package org.firstinspires.ftc.robotcontroller;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;

@Autonomous (name = "BluePlateTowardsCenter")
public class BluePlateTowardsCenter extends LinearOpMode{
    public DcMotor frontRight;
    public DcMotor backRight;
    public DcMotor frontLeft;
    public DcMotor backLeft;

    public DcMotor lift;

    public Servo leftClaw;
    public Servo rightClaw;
    public Servo leftLatch;
    public Servo rightLatch;




    void move (int val){
        frontRight.setPower(.75 * val);
        backRight.setPower(.75 * val);
        frontLeft.setPower(.75 * val);
        backLeft.setPower(.75 * val);
    }

    void stop_moving () {
        frontRight.setPower(0);
        frontLeft.setPower(0);
        backRight.setPower(0);
        backLeft.setPower(0);
    }
    void reset_direction () {
        frontLeft.setDirection(FORWARD);
        backRight.setDirection(FORWARD);
        frontRight.setDirection(FORWARD);
        backLeft.setDirection(FORWARD);
    }

    void moveHorizontal (int val) {
        frontRight.setPower(val);
        frontLeft.setPower(-val);
        backRight.setPower(-val);
        backLeft.setPower(val);
    }

    void right_direction (DcMotorSimple.Direction dir) {
        frontRight.setDirection(dir);
        backRight.setDirection(dir);
    }

    void left_direction (DcMotorSimple.Direction dir) {
        frontLeft.setDirection(dir);
        backLeft.setDirection(dir);
    }

    double ipr = 3 * Math.PI; //inches per revolution
    double tpr = 383.6; //ticks per revolutions
    double tpi = tpr / ipr; //ticks per inches
    void move_vertical (double spd, double dist) {
        int left_tick_goal = (int) Math.round(tpi * Math.abs(dist) + frontLeft.getCurrentPosition());
        int right_tick_goal = (int) Math.round(tpi * Math.abs(dist) + frontRight.getCurrentPosition());
        if (dist < 0) {
            setup_for_moving_backwards();
        } else {
            setup_for_moving_forwards();
        }

        frontLeft.setTargetPosition(left_tick_goal);
        frontRight.setTargetPosition(right_tick_goal);
        backLeft.setTargetPosition(left_tick_goal);
        backRight.setTargetPosition(right_tick_goal);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (frontLeft.getCurrentPosition() < left_tick_goal && frontRight.getCurrentPosition() < right_tick_goal &&
                backLeft.getCurrentPosition() < left_tick_goal && backRight.getCurrentPosition() < right_tick_goal && !isStopRequested()) {
            frontLeft.setPower(Math.abs(spd));
            frontRight.setPower(Math.abs(spd));
            backLeft.setPower(Math.abs(spd));
            backRight.setPower(Math.abs(spd));
        }
        stop_moving();
        reset_direction();
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    void move_horizontal (double spd, double dist) {
        int left_tick_goal = (int) Math.round(tpi * Math.abs(dist) + frontLeft.getCurrentPosition());
        int right_tick_goal = (int) Math.round(tpi * Math.abs(dist) + frontRight.getCurrentPosition());

        if (dist < 0) {
            setup_for_moving_left();
        } else {
            setup_for_moving_right();
        }

        frontLeft.setTargetPosition(left_tick_goal);
        frontRight.setTargetPosition(right_tick_goal);
        backLeft.setTargetPosition(left_tick_goal);
        backRight.setTargetPosition(right_tick_goal);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (frontLeft.getCurrentPosition() < left_tick_goal && frontRight.getCurrentPosition() < right_tick_goal &&
                backLeft.getCurrentPosition() < left_tick_goal && backRight.getCurrentPosition() < right_tick_goal && !isStopRequested()) {
            frontLeft.setPower(Math.abs(spd));
            frontRight.setPower(Math.abs(spd));
            backLeft.setPower(Math.abs(spd));
            backRight.setPower(Math.abs(spd));
        }
        stop_moving();
        reset_direction();
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    void move_diagonal (double spd, double dist, int hor, int ver) {
        int left_tick_goal = (int) Math.round(tpi * Math.abs(dist) + frontLeft.getCurrentPosition());
        int right_tick_goal = (int) Math.round(tpi * Math.abs(dist) + frontRight.getCurrentPosition());

        setup_for_moving_diagonally(hor, ver);

        if ((hor > 0 && ver > 0) || (hor < 0 && ver < 0)) {
            frontLeft.setTargetPosition(left_tick_goal);
            backRight.setTargetPosition(right_tick_goal);
            frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            while (frontLeft.getCurrentPosition() < left_tick_goal && backRight.getCurrentPosition() < right_tick_goal && !isStopRequested()) {
                frontLeft.setPower(Math.abs(spd));
                backRight.setPower(Math.abs(spd));
                frontRight.setPower(0);
                backLeft.setPower(0);
            }
        } else if ((hor < 0 && ver > 0) || (hor > 0 && ver < 0)) {
            frontRight.setTargetPosition(left_tick_goal);
            backLeft.setTargetPosition(right_tick_goal);
            frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            while (frontRight.getCurrentPosition() < right_tick_goal && backLeft.getCurrentPosition() < left_tick_goal && !isStopRequested()) {
                frontRight.setPower(Math.abs(spd));
                backLeft.setPower(Math.abs(spd));
                frontLeft.setPower(0);
                backRight.setPower(0);
            }
        }

        stop_moving();
        reset_direction();
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    void setup_for_moving_right () {
        frontRight.setDirection(REVERSE);
        backRight.setDirection(FORWARD);
        frontLeft.setDirection(REVERSE);
        backLeft.setDirection(FORWARD);

    }

    void setup_for_moving_left () {
        frontRight.setDirection(FORWARD);
        backRight.setDirection(REVERSE);
        frontLeft.setDirection(FORWARD);
        backLeft.setDirection(REVERSE);
    }

    void setup_for_moving_forwards () {
        frontRight.setDirection(FORWARD);
        backRight.setDirection(FORWARD);
        frontLeft.setDirection(REVERSE);
        backLeft.setDirection(REVERSE);
    }

    void setup_for_moving_backwards () {
        frontRight.setDirection(REVERSE);
        backRight.setDirection(REVERSE);
        frontLeft.setDirection(FORWARD);
        backLeft.setDirection(FORWARD);
    }

    void setup_for_moving_diagonally (int hor, int ver) {
        if (hor > 0 && ver > 0) {
            frontLeft.setDirection(REVERSE);
            backRight.setDirection(FORWARD);
        } else if (hor < 0 && ver > 0) {
            frontRight.setDirection(FORWARD);
            backLeft.setDirection(REVERSE);
        } else if (hor > 0 && ver < 0) {
            frontRight.setDirection(REVERSE);
            backLeft.setDirection(FORWARD);
        } else if (hor < 0 && ver < 0) {
            frontLeft.setDirection(FORWARD);
            backRight.setDirection(REVERSE);
        }
    }

    public int random(){
        return 5;
    }


    public void runOpMode() throws InterruptedException {
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backLeft = hardwareMap.dcMotor.get("backLeft");

        leftClaw = hardwareMap.servo.get("left");
        rightClaw = hardwareMap.servo.get("right");
        leftLatch = hardwareMap.servo.get("left hand");
        rightLatch = hardwareMap.servo.get("right hand");

        lift = hardwareMap.dcMotor.get("lift");

        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftClaw.setPosition(1);
        rightClaw.setPosition(1);
        leftLatch.setPosition(0);
        rightLatch.setPosition(1);

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        telemetry.addData("LeftBPos:", backLeft.getCurrentPosition());
        telemetry.addData("LeftFPos:", frontLeft.getCurrentPosition());
        telemetry.addData("RightFPos:", frontRight.getCurrentPosition());
        telemetry.addData("RightBPos:", backRight.getCurrentPosition());
        telemetry.update();
        //frontRight.setDirection(REVERSE);
        //backRight.setDirection(REVERSE);
        //frontLeft.setDirection(FORWARD);
        //backLeft.setDirection(FORWARD);

        waitForStart();
        //start of autonomous

        //move lift up
        //lift.setPower(-.25);
        //sleep(325);
        //lift.setPower(0);

        //move forward so it can be the only one skipped
        move_vertical(0.3, 2);
        sleep(100);

        //move left up diagonally
        move_diagonal(0.5, 25, -1, 1);
        sleep(100);

        //move forwards
        move_vertical(0.5, 25); //35
        sleep(100);

        //close latches
        rightLatch.setPosition(232.0/280.0);
        leftLatch.setPosition(48.0/280.0);
        sleep(100);

        //move backwards
        move_vertical(0.5, -42); //0.35, -45.5
        sleep(100);

        //open latches
        rightLatch.setPosition(1);
        leftLatch.setPosition(0);
        sleep(100);

        //move right
        move_horizontal(0.5, 52.5); //57.5
        sleep(100);

        //move forwards
        move_vertical(0.5, 27.5); //25
        sleep(100);

        //end of autonomous
    }
}