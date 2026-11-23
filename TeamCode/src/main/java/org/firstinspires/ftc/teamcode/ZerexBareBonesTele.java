package org.firstinspires.ftc.teamcode;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.AdrcController;
import org.firstinspires.ftc.teamcode.Intake;
import org.firstinspires.ftc.teamcode.Outtake;
import org.firstinspires.ftc.teamcode.Turret;
import org.firstinspires.ftc.teamcode.Transfer;
import com.qualcomm.robotcore.hardware.CRServo;
@TeleOp

public class  ZerexBareBonesTele extends OpMode {
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backRight;
    private DcMotor backLeft;
    Turret turret;
    Outtake shooter;
    Intake intake;
    Transfer transfer;
    private GoBildaPinpointDriver pinpoint;
    private Limelight3A limelight;
    private boolean autoTargetEnabled = false;
    private boolean lastBumper2State = false;
    boolean shooterEnabled = false;
    boolean lastRightBumperState = false;
    private double shooterSpeed;
    private double drive;
    private double strafe;
    private double turn;
    private final double SPEED = 0.7;
    private final double SLOW_SPEED = 0.2;
    @Override
    public void init() {
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        DcMotor turnTableMotor = hardwareMap.dcMotor.get("turnTable");
        DcMotor Transfer = hardwareMap.dcMotor.get("transfer");
        DcMotor Intake = hardwareMap.dcMotor.get("intake");
        DcMotor Shooter = hardwareMap.dcMotor.get("shooter");
        Servo TurretServo = hardwareMap.servo.get("turretServo");
        turret = new Turret(turnTableMotor,limelight,TurretServo);
        transfer = new Transfer(Transfer);
        intake= new Intake(Intake);
        shooter = new Outtake(Shooter);
        turnTableMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turnTableMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void loop() {
        if (!gamepad1.left_bumper) {
            drive = -SPEED * gamepad1.left_stick_y; //y inputs are reversed
            strafe = SPEED * gamepad1.left_stick_x;
            turn = SPEED * gamepad1.right_stick_x;
        } else {
            drive = -SLOW_SPEED * gamepad1.left_stick_y; //y inputs are reversed
            strafe = SLOW_SPEED * gamepad1.left_stick_x;
            turn = SLOW_SPEED * gamepad1.right_stick_x;
        }

        frontLeft.setPower(drive + turn + strafe);
        frontRight.setPower(drive - turn - strafe); //
        backLeft.setPower(drive + turn - strafe); //
        backRight.setPower(drive - turn + strafe); //
        if (gamepad2.dpad_up){
            turret.top();
        }
        else if (gamepad2.dpad_left || gamepad2.dpad_right){
            turret.middle();
        }
        else if (gamepad2.dpad_down){
            turret.bottom();
        }

        boolean currentRightBumperState = gamepad2.right_bumper;
        if (currentRightBumperState && !lastRightBumperState) {
            shooterEnabled = !shooterEnabled;
        }
        lastRightBumperState = currentRightBumperState;
        if (shooterEnabled) {
            shooter.shoot(-1);
        } else {
            shooter.stop();
        }
        // --- Intake ---
        if (gamepad2.right_trigger > 0.3) {
            intake.intake();
            transfer.transfer();
        } else {
            intake.stop();
            transfer.stop();
        }
        //intake reverse(a)
        if (gamepad2.a){
            intake.reverse();
        }
        else{
            intake.stop();
        }
        //intake + transfer reverse(x)
        if (gamepad2.x){
            intake.reverse();
            transfer.reverse();
        }
        else{
            intake.stop();
            transfer.stop();
        }
        //transfer reverse(y)
        if(gamepad2.y){
            transfer.reverse();
        }
        else{
            transfer.stop();
        }

    }
}