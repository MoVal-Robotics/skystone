package org.firstinspires.ftc.robotcontroller.internal;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ColorSensor;

@TeleOp(name = "THISTeleOp")
public class THISTeleOp extends OpMode {
    public DcMotor frontRight;
    public DcMotor backRight;
    public DcMotor frontLeft;
    public DcMotor backLeft;

    public DcMotor lift;

    public Servo rotateClaw;
    public Servo closeClaw;
    public Servo leftLatch;
    public Servo rightLatch;
    public Servo capstoneDrop;

    //public ColorSensor ICYOU;
    public NormalizedColorSensor ICYOU2;
    //public double red_ = Color.red(ISEEYOU.toColor());
    //public double blue_ = Color.blue(ISEEYOU.toColor());
    //public double green_ = Color.green(ISEEYOU.toColor());
    public double red_;
    public double green_;
    public double blue_;

    double speed_ = 1.0;

    @Override
    public void init() {
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backLeft = hardwareMap.dcMotor.get("backLeft");

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rotateClaw = hardwareMap.servo.get("left");
        closeClaw = hardwareMap.servo.get("right");
        leftLatch = hardwareMap.servo.get("left hand");
        rightLatch = hardwareMap.servo.get("right hand");
        capstoneDrop = hardwareMap.servo.get("Capstone Drop");

        lift = hardwareMap.dcMotor.get("lift");

        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //ICYOU = hardwareMap.colorSensor.get("I SENSE COLOR");
        ICYOU2 = hardwareMap.get(NormalizedColorSensor.class, "I SENSE COLOR");
        //NormalizedRGBA ISEEYOU = ICYOU2.getNormalizedColors();
        //red_ = Color.red(ISEEYOU.toColor());
        //blue_ = Color.blue(ISEEYOU.toColor());
        //green_ = Color.green(ISEEYOU.toColor());

        rotateClaw.setPosition(1);
        closeClaw.setPosition(1);
        leftLatch.setPosition(0);
        rightLatch.setPosition(1);
        capstoneDrop.setPosition(0);
    }

    @Override
    public void loop() {

        //telemetry.addData("rotateClaw: ", rotateClaw.getPosition());
        //telemetry.addData("closeClaw: ", closeClaw.getPosition());
        //telemetry.addData("stick: ", gamepad2.left_stick_y);
        //telemetry.addData("sensor:", ICYOU.red() + ", " + ICYOU.blue() + ", " + ICYOU.green());

        NormalizedRGBA ISEEYOU = ICYOU2.getNormalizedColors();
        red_ = Color.red(ISEEYOU.toColor());
        blue_ = Color.blue(ISEEYOU.toColor());
        green_ = Color.green(ISEEYOU.toColor());

        telemetry.addData("red: ", red_);
        telemetry.addData("green: ", green_);
        telemetry.addData("blue: ", blue_);
        telemetry.update();

        if (gamepad1.dpad_left) {
            speed_ = 0.5;
        } else if (gamepad1.dpad_right) {
            speed_ = 1.0;
        }

        if (Math.abs(gamepad1.left_stick_y) > .1 || Math.abs(gamepad1.right_stick_y) > .1) {
            //for moving robot forward and backward and turning
            frontLeft.setPower(gamepad1.left_stick_y * 0.9 * speed_); //0.85
            backLeft.setPower(gamepad1.left_stick_y * 0.9 * speed_); //0.85
            frontRight.setPower(-gamepad1.right_stick_y * 0.9 * speed_); //0.85
            backRight.setPower(-gamepad1.right_stick_y * 0.9 * speed_); //0.85
        } else if (gamepad1.left_trigger > .1) {
            //for moving robot left
            frontLeft.setPower(gamepad1.left_trigger * speed_);
            backLeft.setPower(-gamepad1.left_trigger * speed_);
            frontRight.setPower(gamepad1.left_trigger * speed_);
            backRight.setPower(-gamepad1.left_trigger * speed_);
        } else if (gamepad1.right_trigger > .1) {
            //for moving robot right
            frontLeft.setPower(-gamepad1.right_trigger * speed_);
            backLeft.setPower(gamepad1.right_trigger * speed_);
            frontRight.setPower(-gamepad1.right_trigger * speed_);
            backRight.setPower(gamepad1.right_trigger * speed_);
            //for moving robot straight forward and backward
        } else if (gamepad1.dpad_up) {
            //for moving robot straight
            frontLeft.setPower(-0.7); //-.5
            backLeft.setPower(-0.7); //-.5
            frontRight.setPower(0.7); //.5
            backRight.setPower(0.7);//.5
        } else if (gamepad1.dpad_down) {
            //for moving robot backwards
            frontLeft.setPower(.5);
            backLeft.setPower(.5);
            frontRight.setPower(-.5);
            backRight.setPower(-.5);


        } else {
            //stop moving robot
            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);
        }

        //for moving lift up and down
        if ((gamepad2.left_trigger) > .1) {
            //down
            lift.setPower(gamepad2.left_trigger);
        } else if (gamepad2.right_trigger > .1) {
            //up
            lift.setPower(-gamepad2.right_trigger);
        } else {
            //stop
            lift.setPower(0);
        }

        //for positioning claw
        if (gamepad2.dpad_right) {
            rotateClaw.setPosition(226/280.0);
        } else if (gamepad2.dpad_left){
            rotateClaw.setPosition(181/280.0);
        } else if (gamepad2.dpad_up){
            rotateClaw.setPosition(136/280.0);
        } else if (gamepad2.dpad_down){
            rotateClaw.setPosition(271/280.0);
        }
        /*else if (gamepad2.left_bumper){
            rotateClaw.setPosition(0/280.0);
        }
        int count = 0;
        while (gamepad2.right_bumper){
            rotateClaw.setPosition((count++)/280.0);
        }*/

        //for opening and closing claw
        if (gamepad2.a) {
            //close
            closeClaw.setPosition(275.0/280.0); //280
        } else if (gamepad2.y) {
            //open
            closeClaw.setPosition(160.0/280.0); //188
        }

        if (gamepad2.x) {
            //drop capstone
            capstoneDrop.setPosition(75.0/280.0);
        } else if (gamepad2.b) {
            //pull back capstone
            capstoneDrop.setPosition(0);
        }

        //for opening and closing latches
        if (gamepad1.a) {
            //close
            rightLatch.setPosition(232.0/280.0);
            leftLatch.setPosition(48.0/280.0);
            //rightLatch.setPosition(280.0/280.0);
            //leftLatch.setPosition(0.0/280.0);
        }
        else if (gamepad1.y) {
            //open
            rightLatch.setPosition(280.0/280.0);
            leftLatch.setPosition(0.0/280.0);
            //rightLatch.setPosition(230.0/280.0);
            //leftLatch.setPosition(50.0/280.0);
        }

         /*
         * 45 degrees is 45/280
         * 90 degrees is 90/280
         * 135 degrees is 135/280
         */
    }
}
