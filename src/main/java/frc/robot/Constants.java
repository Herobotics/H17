// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    // Port numbers for driver and operator gamepads. These correspond with the numbers on the USB
    // tab of the DriverStation
    public static final int kDriverControllerPort = 0;
    public static final int kOperatorControllerPort = 1;

    // Constants for transforming controller input
    public static final double kJoystickDeadband = 0.1;  // values below this amount will map to 0, needs to be in [0, 1)
  }

  public static class DrivetrainConstants {
    // PWM ports/CAN IDs for motor controllers
    public static final int kLeftRearID = 4;
    public static final int kLeftFrontID = 2;
    public static final int kRightRearID = 3;
    public static final int kRightFrontID = 1;

    // Current limit for drivetrain motors
    public static final int kCurrentLimit = 60;

    // Limit top speeds
    public static final double kDriveScale = 1.0;
    public static final double kTurnScale = 0.60;

    // Autonomous speed
    public static final double kAutoSpeed = 0.2;
    public static final double kAutoDriveTime = 10;
    // Autonomous duration
  }

  public static class LauncherConstants {
    // PWM ports/CAN IDs for motor controllers
    public static final int kFeederID = 1;
    public static final int kLauncherID = 0;
    public static final int kLowerRollerID = 2;
    public static final int kUpperRollerID = 3;
    public static final int kmiddleWheelID = 4;

    // Current limit for launcher and feed wheels
    public static final int kLauncherCurrentLimit = 80;
    public static final int kFeedCurrentLimit = 80;

    // Speeds for wheels when intaking and launching. Intake speeds are negative to run the wheels
    // in reverse
    public static final double kLauncherSpeed = 1;
    public static final double kLaunchFeederSpeed = 1;
    public static final double kIntakeLauncherSpeed = -1;
    public static final double kIntakeFeederSpeed = -.2;
    public static final double kUpperRollerSpeed = -1;
    public static final double kLowerRollerSpeed = 1;
    public static final double kmiddleWheelSpeed = 1;

    public static final double kLauncherDelay = 0.75;
    public static final double kIntakeDelay = 2.0;
  }
}
