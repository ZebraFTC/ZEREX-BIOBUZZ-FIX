package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.DcMotor;
public class Transfer {
    DcMotor Transfer;
    public Transfer(DcMotor Transfer){
        this.Transfer=Transfer;
    }
    public void transfer(){
        Transfer.setPower(-1);
    }
    public void reverse(){
        Transfer.setPower(1);
    }
    public void stop(){
        Transfer.setPower(0.0);
    }
}