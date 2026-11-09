package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.DcMotor;
public class Transfer {
    DcMotor TransferLeft;
    DcMotor TransferRight;
    public Transfer(DcMotor TransferLeft, DcMotor TransferRight){
        this.TransferLeft=TransferLeft;
        this.TransferRight=TransferRight;
    }
    public void transfer(){
        TransferLeft.setPower(1);
        TransferRight.setPower(-1);
    }
    public void reverse(){
        TransferLeft.setPower(-1);
        TransferRight.setPower(1);
    }
    public void stop(){
        TransferLeft.setPower(0.0);
        TransferRight.setPower(0.0);
    }
}