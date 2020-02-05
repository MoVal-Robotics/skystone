package org.firstinspires.ftc.robotcontroller;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;

@Autonomous (name = "BluePlate")
public class ExBluePlate extends LinearOpMode{
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
        frontLeft.setPower(val);
        backRight.setPower(-val);
        backLeft.setPower(-val);
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

        waitForStart();

        //move forwards .825 secs
        move(1);
        sleep(825);
        stopMoving();
        sleep(1000);

        //close latches
        rightLatch.setPosition(190.0/280.0);
        leftLatch.setPosition(100.0/280.0);
        sleep(1000);

        //move backwards 1 sec
        move(-1);
        sleep(1500);
        stopMoving();
        sleep(1000);

        //open latches
        leftLatch.setPosition(0);
        rightLatch.setPosition(1);
        sleep(1000);

        //move right 1 sec
        moveHorizontal(1);
        sleep(1500);
        stopMoving();
        sleep(1000);
        //end of autonomous
    }
}
