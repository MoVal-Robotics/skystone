package org.firstinspires.ftc.robotcontroller.internal;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "NewRobotTeleOp")
public class NewRobotTeleOp extends OpMode {
    public DcMotor frontRight;
    public DcMotor backRight;
    public DcMotor frontLeft;
    public DcMotor backLeft;

    double speed_ = 0.5;

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
    }

    @Override
    public void loop() {

        if (gamepad1.dpad_left) {
            //speed_ = 0.5;
        } else if (gamepad1.dpad_right) {
            //speed_ = 1.0;
        }

        if (Math.abs(gamepad1.left_stick_y) > .1 || Math.abs(gamepad1.right_stick_y) > .1) {
            //for moving robot forward and backward and turning
            frontLeft.setPower(gamepad1.left_stick_y * speed_); //0.85
            backLeft.setPower(gamepad1.left_stick_y * speed_); //0.85
            frontRight.setPower(-gamepad1.right_stick_y * speed_); //0.85
            backRight.setPower(-gamepad1.right_stick_y * speed_); //0.85
        } else if (gamepad1.dpad_up) {
            //for moving robot straight
            frontLeft.setPower(-0.5); //-.5
            backLeft.setPower(-0.5); //-.5
            frontRight.setPower(0.5); //.5
            backRight.setPower(0.5);//.5
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
    }
}
