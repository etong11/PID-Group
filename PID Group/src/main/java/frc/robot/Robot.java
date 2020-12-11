/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2020 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.MedianFilter;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  // distance in inches the robot wants to stay from an object
  public static final double kHoldDistance = 12.0;

  // factor to convert sensor values to a distance in inches
  public static final double kValueToInches = 0.125;

  // proportional speed constant
  public static final double kP = 7.0;

  // integral speed constant
  public static final double kI = 0.018;

  // derivative speed constant
  public static final double kD = 1.5;

  //position and velocity tolerance range of pid controller
  public static final double posTolerance = 5;
  public static final double veloTolerance = 10;

  public static final int kLeftMotorPort = 0;
  public static final int kRightMotorPort = 1;
  public static final int kUltrasonicPort = 0;
  public static final int kUltrasonicPort2 = 1;
  public static final int kDriverControllerPort = 2;

  // median filter to discard outliers; filters over 5 samples
  public static MedianFilter m_filter = new MedianFilter(5);

  public static AnalogInput m_ultrasonic = new AnalogInput(kUltrasonicPort);
  public static AnalogInput m_ultrasonic2 = new AnalogInput(kUltrasonicPort2);
  public static CANSparkMax leftSparkMax = new CANSparkMax(kLeftMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
  public static CANSparkMax rightSparkMax = new CANSparkMax(kRightMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
  public static DifferentialDrive m_diffDrive = new DifferentialDrive (leftSparkMax, rightSparkMax); 
  public static PIDController m_pidController = new PIDController(kP, kI, kD);
  public static Joystick driverController = new Joystick(kDriverControllerPort);


  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /**
   * This function is called once when teleop is enabled.
   */
  @Override
  public void teleopInit() {
    // Set setpoint of the pid controller
    m_pidController.setSetpoint(kHoldDistance * kValueToInches);

    //set tolerance range of pid controller
    m_pidController.setTolerance(posTolerance, veloTolerance);
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    //returned value is filtered with a rolling median filter
    //Option 1: pidOutput based off of ultrasonic feedback
    double pidOutput1 = m_pidController.calculate(m_filter.calculate(m_ultrasonic.getVoltage()));
    double pidOutput2 = m_pidController.calculate(m_filter.calculate(m_ultrasonic2.getVoltage()));
    m_diffDrive.tankDrive(pidOutput1, pidOutput2);

    //Option 2: take speed generated by axis and put through pidController to adjust measurements
    double leftSpeed = driverController.getRawAxis(0);
    double rightSpeed = driverController.getRawAxis(1);
    m_diffDrive.tankDrive(m_pidController.calculate(leftSpeed), m_pidController.calculate(rightSpeed));

    //to do: set raw axis values to setpoint value
  }

  /**
   * This function is called once when the robot is disabled.
   */
  @Override
  public void disabledInit() {
  }

  /**
   * This function is called periodically when disabled.
   */
  @Override
  public void disabledPeriodic() {
  }

  /**
   * This function is called once when test mode is enabled.
   */
  @Override
  public void testInit() {
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
