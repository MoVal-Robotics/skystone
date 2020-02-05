package org.firstinspires.ftc.robotcontroller;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous (name = "red")
public class ExRedAutonomous extends LinearOpMode {
    public DcMotor frontRight;
    public DcMotor backRight;
    public DcMotor frontLeft;
    public DcMotor backLeft;

    void move() {
        frontRight.setPower(1);
        frontLeft.setPower(1);
        backRight.setPower(1);
        backLeft.setPower(1);
    }

    void stopMoving() {
        frontRight.setPower(0);
        frontLeft.setPower(0);
        backRight.setPower(0);
        backLeft.setPower(0);
    }

    void rightDirection(DcMotorSimple.Direction dir) {
        frontRight.setDirection(dir);
        backRight.setDirection(dir);
    }

    void leftDirection(DcMotorSimple.Direction dir) {
        frontLeft.setDirection(dir);
        backLeft.setDirection(dir);
    }

    void reverseDirection() {
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    void moveLeft() {
        frontRight.setPower(-1);
        frontLeft.setPower(-1);
        backRight.setPower(1);
        backLeft.setPower(1);
    }

    public int random() {
        return 5;
    }

    @Override

    public void runOpMode() throws InterruptedException {
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backLeft = hardwareMap.dcMotor.get("backLeft");

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        //forward 2 secs
        move();
        sleep(2000);

        //backward 2 secs
        reverseDirection();
        move();
        sleep(2000);

        //left 2 secs
        reverseDirection();
        moveLeft();
        sleep(2000);

        /*
        move();
        sleep(2000);

        //turn right
        rightDirection(DcMotorSimple.Direction.FORWARD);
        move();
        sleep(1000);
        //straighten
        rightDirection(DcMotorSimple.Direction.REVERSE);
        move();
        //forward 1 sec
        sleep(1000);
        */
    }
}