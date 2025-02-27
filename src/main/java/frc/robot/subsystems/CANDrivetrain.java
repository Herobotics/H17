// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.DrivetrainConstants.*;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.ADIS16448_IMU;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/* This class declares the subsystem for the robot drivetrain if controllers are connected via CAN. Make sure to go to
 * RobotContainer and uncomment the line declaring this subsystem and comment the line for PWMDrivetrain.
 *
 * The subsystem contains the objects for the hardware contained in the mechanism and handles low level logic
 * for control. Subsystems are a mechanism that, when used in conjuction with command "Requirements", ensure
 * that hardware is only being used by 1 command at a time.
 */
public class CANDrivetrain extends SubsystemBase {
  /*Class member variables. These variables represent things the class needs to keep track of and use between
  different method calls. */
  MecanumDrive m_drivetrain;
  ADXRS450_Gyro gyro;
  public static final ADIS16448_IMU imu = new ADIS16448_IMU();
  /*Constructor. This method is called when an instance of the class is created. This should generally be used to set up
   * member variables and perform any configuration or set up necessary on hardware.
   */
  public CANDrivetrain() {
    CANSparkMax leftFront = new CANSparkMax(kLeftFrontID, MotorType.kBrushed);
    CANSparkMax leftRear = new CANSparkMax(kLeftRearID, MotorType.kBrushed);
    CANSparkMax rightFront = new CANSparkMax(kRightFrontID, MotorType.kBrushed);
    CANSparkMax rightRear = new CANSparkMax(kRightRearID, MotorType.kBrushed);
    // gyro = new ADXRS450_Gyro();
    /*Sets current limits for the drivetrain motors. This helps reduce the likelihood of wheel spin, reduces motor heating
     *at stall (Drivetrain pushing against something) and helps maintain battery voltage under heavy demand */
    leftFront.setSmartCurrentLimit(kCurrentLimit);
    leftRear.setSmartCurrentLimit(kCurrentLimit);
    rightFront.setSmartCurrentLimit(kCurrentLimit);
    rightRear.setSmartCurrentLimit(kCurrentLimit);

    // Set the rear motors to follow the front motors.
    // leftRear.follow(leftFront);
    // rightRear.follow(rightFront);

    // Invert the left side so both side drive forward with positive motor outputs
    leftFront.setInverted(false);
    rightFront.setInverted(true);
    rightRear.setInverted(true);

    // Put the front motors into the differential drive object. This will control all 4 motors with
    // the rears set to follow the fronts
    // m_drivetrain = new DifferentialDrive(leftFront, rightFront);
m_drivetrain = new MecanumDrive(leftFront, leftRear, rightFront,rightRear);
  }

  /*Method to control the drivetrain using arcade drive. Arcade drive takes a speed in the X (forward/back) direction
   * and a rotation about the Z (turning the robot about it's center) and uses these to control the drivetrain motors */
  public void drive(double speed_x, double speed_y, double rotation) {
    //m_drivetrain.driveCartesian(speed_x, speed_y, rotation, gyro.getRotation2d());
    SmartDashboard.putNumber("AngleX", CANDrivetrain.imu.getGyroAngleX());
    SmartDashboard.putNumber("AngleY", CANDrivetrain.imu.getGyroAngleY());
    SmartDashboard.putNumber("AngleZ", CANDrivetrain.imu.getGyroAngleZ());
    m_drivetrain.driveCartesian(speed_x, speed_y, rotation);
  }

  public void GyroCalibrate() {
    // gyro.calibrate();
    // imu.calibrate();
  }
  public void GyroReset() {
    // gyro.reset();
    // imu.reset();
  } 

  @Override
  public void periodic() {
    /*This method will be called once per scheduler run. It can be used for running tasks we know we want to update each
     * loop such as processing sensor data. Our drivetrain is simple so we don't have anything to put here */
  }
}
