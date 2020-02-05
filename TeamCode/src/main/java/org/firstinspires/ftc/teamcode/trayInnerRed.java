package org.firstinspires.ftc.robotcontroller;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;

@Autonomous (name = "trayInnerRed")
public class trayInnerRed extends LinearOpMode{
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
        frontRight.setPower(.75 * -val);
        backRight.setPower(.75 * -val);
        frontLeft.setPower(.75 * val);
        backLeft.setPower(.75 * val);
    }

    void stopMoving () {
        frontRight.setPower(0);
        frontLeft.setPower(0);
        backRight.setPower(0);
        backLeft.setPower(0);
    }

    void moveHorizontal (int val) {
        frontRight.setPower(val);
        frontLeft.setPower(-val);
        backRight.setPower(-val);
        backLeft.setPower(val);
    }

    void rightDirection (DcMotorSimple.Direction dir) {
        frontRight.setDirection(dir);
        backRight.setDirection(dir);
    }

    void leftDirection (DcMotorSimple.Direction dir) {
        frontLeft.setDirection(dir);
        backLeft.setDirection(dir);
    }

    double ipr = 3 * Math.PI; //inches per revolution
    double tpr = 383.6; //ticks per revolutions
    double tpi = tpr / ipr; //ticks per inches
    void driveVertical (double spd, double dist) {
        int left_tick_goal = (int) Math.round(tpi * dist + frontLeft.getCurrentPosition());
        int right_tick_goal = (int) Math.round(tpi * dist + frontRight.getCurrentPosition());

        frontLeft.setTargetPosition(left_tick_goal);
        frontRight.setTargetPosition(right_tick_goal);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        if (dist < 0 ) {
            setup_for_moving_left();
            setup_for_moving_right();
        }
        while (frontLeft.getCurrentPosition() < left_tick_goal && frontRight.getCurrentPosition() < right_tick_goal) {
            frontLeft.setPower(spd);
            frontRight.setPower(spd);
            backLeft.setPower(spd);
            backRight.setPower(spd);
        }
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setDirection(FORWARD);
        frontLeft.setDirection(FORWARD);
        backLeft.setDirection(FORWARD);
        backRight.setDirection(FORWARD);
    }

    void driveHorizontal (double spd, double dist) {
        int top_tick_goal = (int) Math.round(tpi * dist + frontLeft.getCurrentPosition());
        int bottom_tick_goal = (int) Math.round(tpi * dist + backLeft.getCurrentPosition());

        frontLeft.setTargetPosition(top_tick_goal);
        backLeft.setTargetPosition(bottom_tick_goal);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        if (dist < 0) {
            setup_for_moving_left();
        } else if (dist > 0) {
            setup_for_moving_right();
        }
        while (frontLeft.getCurrentPosition() < top_tick_goal && backLeft.getCurrentPosition() < bottom_tick_goal) {
            frontLeft.setPower(spd); //Math.abs
            frontRight.setPower(spd);
            backLeft.setPower(spd);
            backRight.setPower(spd);
        }
        //reset things
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setDirection(FORWARD);
        frontLeft.setDirection(FORWARD);
        backLeft.setDirection(FORWARD);
        backRight.setDirection(FORWARD);
    }

    void setup_for_moving_right() {
        frontLeft.setDirection(REVERSE);
        backRight.setDirection(REVERSE);
    }

    void setup_for_moving_left() {
        backLeft.setDirection(REVERSE);
        frontRight.setDirection(REVERSE);
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

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        //frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        //backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        //start of autonomous
        //move forwards .825 secs
        //move(1);
        //sleep(825);


        //move right
        driveHorizontal(0.5, 12);
        sleep(1000);


        stopMoving();
        /* PSEUDO CODE
        - move right
        - move forward
        - close clamps
        - move backwards
        - open clamps
        - move left under bridge
        - move forward
         */

        /*
        //close latches
        rightLatch.setPosition(230.0/280.0);
        leftLatch.setPosition(50.0/280.0);
        sleep(1000);

        //move backwards 1 sec
        move(-1);
        sleep(1500);
        stopMoving();
        sleep(1000);

        //open latches
        rightLatch.setPosition(1);
        leftLatch.setPosition(0);
        sleep(1000);

        //move left 1 sec
        moveHorizontal(-1);
        sleep(1500);
        stopMoving();
        sleep(1000);
        //end of autonomous
        */
    }
}
