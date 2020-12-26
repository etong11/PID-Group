package frc.pid;

//a generic pid class
//pretty sure itll work with everything because it makes the user do a lot
//tuning's probably harder
public class GenPID {
    //private double kp = 2e-2, ki = 2e-10, kd = 1e-2;
    private double kp,ki,kd;
    private double setpt = 10;
	private double oldVal = 0;
	private double time = 0;
	private double error=0;
	private double out;
    private double oldErr;
    private double curPos;

    public GenPID(double initPos,double setpoint,double kP,double kI,double kD,double interval)//set values 
    {
        curPos = initPos;
        setpt = setpoint;
        kp = kP;
        ki = kI;
        kd = kD;
        time = interval;
    }
    //update position for speed modulation (important)
    public void setPos(double pos){curPos = pos;}
    //set more values
    public void setSetpoint(double setpoint){setpt = setpoint;}    
    
    public void setkP(double kP){kp = kP;}

    public void setkI(double kI){ki = kI;}

    public void setkD(double kD){kd = kD;}

    public void setInterval(double interval){time = interval;}

    //updates the speed value based on position
    //returns 0 upon reaching setpoint
    //magic pid math voodoo follows
	public double getVal() {
		if(error > 1 || error == 0)
		{
			error = setpt-curPos;
            out = (kp*error)+((oldVal+error)*ki)+((error-oldErr)*kd);
			oldErr = error;
            oldVal += error*time;
            return out;
			/*  oldVal += fieldPosition*20;
			time+=20;
			out = setpt-((kp*fieldPosition)+(ki*oldVal)+(kd*(oldErr/20)));
			fieldPosition = (ROBO_SPEED * out);
			oldErr+=out;*/
        }
        return 0;
	}
}