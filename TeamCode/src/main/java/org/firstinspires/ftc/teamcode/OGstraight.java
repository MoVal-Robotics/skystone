package org.firstinspires.ftc.robotcontroller;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;

@Autonomous (name = "OGstraight")
public class OGstraight extends LinearOpMode{
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
    void drive_vertical (double spd, double dist) {
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
                backLeft.getCurrentPosition() < left_tick_goal && backRight.getCurrentPosition() < right_tick_goal) {
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
    void drive_horizontal (double spd, double dist) {
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
                backLeft.getCurrentPosition() < left_tick_goal && backRight.getCurrentPosition() < right_tick_goal) {
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

    void setup_for_moving_backwards() {
        frontRight.setDirection(REVERSE);
        backRight.setDirection(REVERSE);
        frontLeft.setDirection(FORWARD);
        backLeft.setDirection(FORWARD);
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

        leftClaw.setPosition(1);
        rightClaw.setPosition(1);
        leftLatch.setPosition(0);
        rightLatch.setPosition(1);

        //frontRight.setDirection(REVERSE);
        //backRight.setDirection(REVERSE);
        //frontLeft.setDirection(FORWARD);
        //backLeft.setDirection(FORWARD);

        waitForStart();
        //start of autonomous

        //move backwards
        drive_vertical(0.3, -45);
        sleep(1000);

        //end of autonomous
    }
}