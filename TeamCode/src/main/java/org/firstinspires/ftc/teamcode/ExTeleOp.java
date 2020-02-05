package org.firstinspires.ftc.robotcontroller;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "ExTeleOp")
public class ExTeleOp extends OpMode {
    public DcMotor frontRight;
    public DcMotor backRight;
    public DcMotor frontLeft;
    public DcMotor backLeft;

    public DcMotor lift;

    public Servo leftClaw;
    public Servo rightClaw;
    public Servo leftLatch;
    public Servo rightLatch;

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
    }

    @Override
    public void loop() {

        telemetry.addData("leftClaw: ",leftClaw.getPosition());
        telemetry.addData("rightClaw: ", rightClaw.getPosition());
        telemetry.addData("stick: ", gamepad2.left_stick_y);
        telemetry.update();

        //for moving the left motors
        if (Math.abs(gamepad1.left_stick_y) > .1) {
            frontLeft.setPower(-gamepad1.left_stick_y);
            backLeft.setPower(-gamepad1.left_stick_y);
        }
        else {
            frontLeft.setPower(0);
            backLeft.setPower(0);
        }

        //for moving the right motors
        if (Math.abs(gamepad1.right_stick_y) > .1) {
            frontRight.setPower(gamepad1.right_stick_y);
            backRight.setPower(gamepad1.right_stick_y);
        }
        else {
            frontRight.setPower(0);
            backRight.setPower(0);
        }

        if ((gamepad2.left_trigger) > .1) {
            lift.setPower(gamepad2.left_trigger);
        }
        else if (gamepad2.right_trigger > .1) {
            lift.setPower(-gamepad2.right_trigger);
        }
        else {
            lift.setPower(0);
        }

        if (gamepad2.dpad_right) {
            leftClaw.setPosition(226/280.0);
             //close
        }
        else if (gamepad2.dpad_left){
            leftClaw.setPosition(181/280.0);
        }
        else if (gamepad2.dpad_up){
            leftClaw.setPosition(136/280.0);
        }
        else if (gamepad2.dpad_down){
            leftClaw.setPosition(271/280.0);
        }
        else if (gamepad2.left_bumper){
            leftClaw.setPosition(0/280.0);
        }
        /*int count = 0;
        while (gamepad2.right_bumper){
            leftClaw.setPosition((count++)/280.0);
        }*/

        if (gamepad2.a) {
            rightClaw.setPosition(280.0/280.0);
            //close
        }
        else if (gamepad2.y) {
            rightClaw.setPosition(188.0/280.0);
        }

        if (gamepad1.a) {
            rightLatch.setPosition(280.0/280.0);
            leftLatch.setPosition(0.0/280.0);
            //close
        }
        else if (gamepad1.y) {
            rightLatch.setPosition(220.0/280.0);
            leftLatch.setPosition(55.0/280.0);
        }

         /*
         * 45 degrees is 45/280
         * 90 degrees is 90/280
         * 135 degrees is 135/280
         */
    }
}
