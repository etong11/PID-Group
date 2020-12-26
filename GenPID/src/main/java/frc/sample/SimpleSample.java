package frc.sample;

import frc.pid.GenPID;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

public class SimpleSample {
    private CANSparkMax motor = new CANSparkMax(0, CANSparkMaxLowLevel.MotorType.kBrushless);
    private CANEncoder encoder = new CANEncoder(motor);
    private GenPID controller;
    private double d = 2.5;//diameter of wheel in inches
    public void drive(double dist)//distance in inches
    {
        //initializes pid controller with starting position of 0, setpoint of dist inches, 
        //kP as 2e-2, kI as 2e-10, kD as 1e-2, and the interval between function calls as 20ms
        encoder.setPosition(0);
        controller = new GenPID(0, inchesToEncoders(dist,d),2e-2, 2e-10, 1e-2, 20);//initialize generic pid class
        double val;
        while((val = controller.getVal())!=0)//while setpoint not reached, call pid function
        {
            motor.set(val);//val must be stored because if getVal is called twice in a row the interval is incorrect
            controller.setPos(encoder.getPosition());//update position for new values
        }
    }

    public double inchesToEncoders(double dist, double di)//converts inches to encoder values using some magical math
    {
        return dist/(di*Math.PI)*encoder.getCountsPerRevolution();
    }

}