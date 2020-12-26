/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.controller.PIDController;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
//import frc.lib.Field2d;

/**
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
 */
public class Robot extends TimedRobot {
  private DifferentialDrive m_myRobot;
  private Joystick m_leftStick;
  private Joystick m_rightStick;
  private TalonSRX masterTalon = new TalonSRX(0);
  private TalonSRX slaveTalon = new TalonSRX(1);
  private int kF = 1023;

  //private Field2d field = new Field2d();

  @Override
  public void robotInit() {
    // m_myRobot = new DifferentialDrive(new PWMVictorSPX(0), new PWMVictorSPX(1));
    // m_leftStick = new Joystick(0);
    // m_rightStick = new Joystick(1);
    masterTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
    masterTalon.config_kP(0, 0.250);
    masterTalon.changeControlMode(TalonControlMode.MotionMagic);
  }

  @Override
  public void teleopPeriodic() {
    // m_myRobot.tankDrive(m_leftStick.getY(), m_rightStick.getY());
    masterTalon.setMotionMagicCruiseVelocity();
    masterTalon.setMotionMagicAcceleration();
    
  }
  public void setMotorSpeed(double motorSpeed) {
    masterTalon.set(ControlMode.PercentOutput, 1);
  }
}
