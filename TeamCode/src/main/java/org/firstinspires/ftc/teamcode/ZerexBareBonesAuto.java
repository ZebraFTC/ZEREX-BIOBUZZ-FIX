package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous
public class ZerexBareBonesAuto extends LinearOpMode {
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backRight;
    private DcMotor backLeft;
    MecanumDrive drive;
    Outtake shooter;
    @Override
    public void runOpMode() throws InterruptedException {
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        DcMotor Shooter = hardwareMap.dcMotor.get("shooter");
        drive = new MecanumDrive(frontLeft, frontRight, backLeft, backRight);
        shooter = new Outtake(Shooter);

        waitForStart();
        if (opModeIsActive()) {
            shooter.shoot(-1);
            sleep(2000);
            shooter.stop();
            drive.targetDrive(700, 700, 700, 700, 0.6);
            double startTime = getRuntime();
            while (opModeIsActive() && drive.isBusy() && (getRuntime() - startTime < 5.0)) {
                idle();
            }
            drive.stopMotors();
            drive.targetDrive(700, -700, -700, 700, 0.6);
            startTime = getRuntime();
            while (opModeIsActive() && drive.isBusy() && (getRuntime() - startTime < 5.0)) {
                idle();
            }
            drive.stopMotors();
        }

    }

}