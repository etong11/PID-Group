import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class SparkPID extends SubsystemBase {
  private CANSparkMax motor;
  private CANPIDController pid;

/*

    // PID coefficients
    kP = 0.1; 
    kI = 1e-4;
    kD = 1; 
    kIz = 0; 
    kFF = 0; 
    kMaxOutput = 1; 
    kMinOutput = -1;
    setpoint (user defined)
*/

  public SparkPID(double kp, double ki, double kd, double kiz, double kff, double setpoint,CANSensor fb) {
    motor = new CANSparkMax(0, MotorType.kBrushless);
    pid = motor.getPIDController();

    pid.setP(kp);
    pid.setI(ki);
    pid.setD(kd);
    pid.setIZone(kiz);
    pid.setFF(kff);
    pid.setOutputRange(-1, 1);
    pid.setReference(setpoint, ControlType.kPosition);
    pid.setFeedbackDevice(fb);
  }
  public void setkP(double kp){pid.setP(kp);}
  public void setkI(double ki){pid.setI(ki);}
  public void setkD(double kd){pid.setD(kd);}
  public void setIZone(double iz){pid.setIZone(iz);}
  public void setFF(double ff){pid.setFF(ff);}
  public void setSetpoint(double setpount){pid.setReference(setpoint,ControlType.kPosition);}
  public void setFeedbackDevice(CANSensor fb){pid.setFeedbackDevice(fb);}
  
}