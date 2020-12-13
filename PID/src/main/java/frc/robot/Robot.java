/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2020 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.controller.PIDController;

import frc.lib.Field2d; // Copied from the source code into the project



public class Robot extends TimedRobot {

  // Simulation variables
  private final double LOOP_TIME = 0.01;
  private final double ROBO_SPEED = 1;
  private double fieldPosition = 0;
  private Field2d field = new Field2d();
  private double kp = 3e-2, ki = 1e-6, kd = 2e-1;
  //private double kp = 0.1, ki = 1e-3, kd = 1e-1;
  private double setpt = 10;
  private double oldVal = 0;
  private double time = 0;
  private double error=0;
  private double out;
  private double oldErr;

  @Override
  public void autonomousPeriodic() {
    if(error > 1 || error == 0)
    {
      error = setpt-fieldPosition;
      out = (kp*error)+((oldVal+error)*ki)+((error-oldErr)*kd);
      System.out.println("error: "+error+"\nField position: "+fieldPosition+"\nOut: "+out);
      fieldPosition += (ROBO_SPEED * out);
      oldErr = error;
      oldVal += error*20;
    /*  oldVal += fieldPosition*20;
      time+=20;
      out = setpt-((kp*fieldPosition)+(ki*oldVal)+(kd*(oldErr/20)));
      fieldPosition = (ROBO_SPEED * out);
      oldErr+=out;*/
    }

  }

  @Override
  public void simulationPeriodic() {
    field.setRobotPose(fieldPosition, 0, new Rotation2d(0));
  }

  @Override
  public void autonomousInit(){
      fieldPosition=0;
      error=0;
  }
}
