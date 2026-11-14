package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
@TeleOp(name = "Scrimmage TeleOp")
public class scrimmage extends LinearOpMode {
    private boolean autoTargetEnabled = false;
    private boolean lastBumper2State = false;
    boolean shooterEnabled = false;
    boolean lastRightBumperState = false;
    private double shooterSpeed;
    private double drive;
    private double strafe;
    private double turn;
    private final double SPEED = 1;
    private final double SLOW_SPEED = 0.5;
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontLeft");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backLeft");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontRight");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backRight");
        DcMotor intake = hardwareMap.dcMotor.get("intake");
        DcMotor transfer = hardwareMap.dcMotor.get("transfer");
        DcMotor shooter = hardwareMap.dcMotor.get("shooter");
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
// Optional: Keeps robot from drifting when sticks are released
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            if (!gamepad1.left_bumper) {
                drive = -SPEED * gamepad1.left_stick_y; //y inputs are reversed
                strafe = SPEED * gamepad1.left_stick_x;
                turn = SPEED * gamepad1.right_stick_x;
            } else {
                drive = -SLOW_SPEED * gamepad1.left_stick_y; //y inputs are reversed
                strafe = SLOW_SPEED * gamepad1.left_stick_x;
                turn = SLOW_SPEED * gamepad1.right_stick_x;
            }
            frontLeftMotor.setPower(drive + turn + strafe);
            frontRightMotor.setPower(drive - turn - strafe); //
            backLeftMotor.setPower(drive + turn - strafe); //
            backRightMotor.setPower(drive - turn + strafe); //

// Intake control (with reverse option on right bumper if needed)
            if (gamepad2.left_trigger > 0.2) {
                intake.setPower(1.0);
            } else if (gamepad2.right_bumper) {
                intake.setPower(-1.0);
            } else {
                intake.setPower(0);
            }

// Transfer control
            if (gamepad2.left_bumper) {
                transfer.setPower(-1.0);
            } else {
                transfer.setPower(0);
            }

// Shooter control
            if (gamepad2.right_trigger > 0.2) {
                shooter.setPower(-1.0);
            } else {
                shooter.setPower(0);
            }
        }
    }
}