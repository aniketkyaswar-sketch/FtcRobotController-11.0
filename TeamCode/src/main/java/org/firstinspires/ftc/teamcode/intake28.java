package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

@TeleOp
public class intake28 extends OpMode {
    ElapsedTime runTime = new ElapsedTime();
    double i = 0;
    // Drive motors
    DcMotor FL, FR, BL, BR;

    // Intake + turret
    DcMotor intake, intake2;
    DcMotor turret;
    DcMotor turret2;
    boolean pressed = false;


    // Servo
    Servo tounge;

    // Touch sensor
    TouchSensor touch;

    @Override
    public void init() {

        // Motors
        FL = hardwareMap.dcMotor.get("FL");
        FR = hardwareMap.dcMotor.get("FR");
        BL = hardwareMap.dcMotor.get("BL");
        BR = hardwareMap.dcMotor.get("BR");

        turret = hardwareMap.dcMotor.get("turret");
        turret2 = hardwareMap.dcMotor.get("test");

        intake = hardwareMap.dcMotor.get("intake");
        intake2 = hardwareMap.dcMotor.get("intake2");

        tounge = hardwareMap.servo.get("tounge");

        // Touch sensor
        touch = hardwareMap.get(TouchSensor.class, "touch");

        // Reverse right side
        FR.setDirection(DcMotor.Direction.REVERSE);
        BR.setDirection(DcMotor.Direction.REVERSE);
        turret2.setDirection(DcMotor.Direction.REVERSE);

        tounge.setPosition(0);
    }



    @Override
    public void loop() {

        if (pressed == false){
            pressed = touch.isPressed();
            telemetry.addData("Touch Sensor", pressed ? "PRESSED" : "NOT PRESSED");
            telemetry.update();
        }
        if ((pressed == true)){
            intake.setPower(0);
            //intake2.setPower(0);
            pressed = false;
        } else {
            intake.setPower(-1);
            intake2.setPower(-1);
        }
        /* =====================
           TOUCH SENSOR
           ===================== */

        /* =====================
           INTAKE / TURRET
           ===================== */
        if (gamepad1.b) {
            turret.setPower(1);
            turret2.setPower(1);
        } else {
            turret.setPower(0);
            turret2.setPower(0);
        }

        if (gamepad1.a) {
            intake.setPower(-1);
            intake2.setPower(1);
            tounge.setPosition(1);
        }

        if (gamepad1.x) {
            tounge.setPosition(0);

        }

        /* =====================
           MECANUM DRIVE
           ===================== */
        double y = -gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x;
        double rx = -gamepad1.right_stick_x;

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);

        FL.setPower((y + x + rx) / denominator * 0.8);
        FR.setPower((y - x - rx) / denominator * 0.8);
        BL.setPower((y - x + rx) / denominator * 0.8);
        BR.setPower((y + x - rx) / denominator * 0.8);

        /* =====================
           TELEMETRY
           ===================== */

    }
}
