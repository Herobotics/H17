// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static frc.robot.Constants.DrivetrainConstants.kDriveScale;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.DrivetrainConstants;
import frc.robot.Constants.LauncherConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.LaunchNote;
import frc.robot.commands.PrepareLaunch;
import frc.robot.subsystems.CANDrivetrain;
import frc.robot.subsystems.PWMDrivetrain;
import frc.robot.subsystems.PWMLauncher;


// import frc.robot.subsystems.CANDrivetrain;
// import frc.robot.subsystems.CANLauncher;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems are defined here.
  // private final PWMDrivetrain m_drivetrain = new PWMDrivetrain();
  public final CANDrivetrain m_drivetrain = new CANDrivetrain();
  private final PWMLauncher m_launcher = new PWMLauncher();
  // private final CANLauncher m_launcher = new CANLauncher();

  /*The gamepad provided in the KOP shows up like an XBox controller if the mode switch is set to X mode using the
   * switch on the top.*/
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);
  private final CommandXboxController m_operatorController =
      new CommandXboxController(OperatorConstants.kOperatorControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be accessed via the
   * named factory methods in the Command* classes in edu.wpi.first.wpilibj2.command.button (shown
   * below) or via the Trigger constructor for arbitary conditions
   */
  private void configureBindings() {
    // Set the default command for the drivetrain to drive using the joysticks
    m_drivetrain.setDefaultCommand(
        new RunCommand(
            () ->
               driveWithControllerAndInversion(m_driverController, false),
            m_drivetrain));

    // Set a "reset gyro" button
    m_driverController.b().whileTrue(new RunCommand(
      () -> m_drivetrain.GyroReset(), m_drivetrain
      ));

    // Attempt at a "inverse drive" button.
    m_driverController.x().toggleOnTrue(new RunCommand(
      () -> driveWithControllerAndInversion(m_driverController, true),
            m_drivetrain));

    /*Create an inline sequence to run when the operator presses and olds the A (green) button. Run the PrepareLaunch
     * command for 1 seconds and then run the LaunchNote command */
    m_driverController
        .a()
        .whileTrue(
            new PrepareLaunch(m_launcher)
                .withTimeout(LauncherConstants.kLauncherDelay)
                .andThen(new LaunchNote(m_launcher))
                .handleInterrupt(() -> m_launcher.stop()));
    
    m_operatorController
        .a()
        .whileTrue(
            new PrepareLaunch(m_launcher)
                .withTimeout(LauncherConstants.kLauncherDelay)
                .andThen(new LaunchNote(m_launcher))
                .handleInterrupt(() -> m_launcher.stop()));

    // Set up a binding to run the intake command while the operator is pressing and holding the
    // left Bumper
    m_driverController.leftTrigger().whileTrue(m_launcher.getIntakeCommand());
    m_operatorController.leftTrigger().whileTrue(m_launcher.getIntakeCommand());

    m_driverController.rightTrigger().whileTrue(m_launcher.getGroundIntakeCommand());
    m_driverController.rightBumper().whileTrue(m_launcher.getGroundIntakeCommand());
    m_operatorController.rightTrigger().whileTrue(m_launcher.getGroundIntakeCommand());
    m_operatorController.rightBumper().whileTrue(m_launcher.getGroundIntakeCommand());

    m_launcher.setDefaultCommand(
        new RunCommand(
            () ->
                m_launcher.setLaunchWheel(applyDeadband(-m_operatorController.getLeftY())),
            m_launcher));
    
    m_operatorController
        .x()
        .whileTrue(m_launcher.getFeedWheelOutCommand());
        

    m_operatorController
        .rightStick()
        .whileTrue(m_launcher.getNoteOut());
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Autos.exampleAuto(m_drivetrain, m_launcher);
  }

  public void calibrateGyro() {
    m_drivetrain.GyroCalibrate();
  }

  public double applyDeadband(double input) {
    if (Math.abs(input) < OperatorConstants.kJoystickDeadband) {
      input = 0.0;
    } else if (input > 0) {
      input -= OperatorConstants.kJoystickDeadband;
    } else {
      input += OperatorConstants.kJoystickDeadband;
    }

    // Finally, scale up to [-1,1]
    return input / (1.0 - OperatorConstants.kJoystickDeadband);
  }

  public void driveWithControllerAndInversion(CommandXboxController controller, boolean invert) {
    double direction = 1.0;
    if (invert) {
      direction = -1.0;
    }

    m_drivetrain.drive(applyDeadband(controller.getLeftY()) * DrivetrainConstants.kDriveScale * direction,
                    applyDeadband(-controller.getLeftX()) * DrivetrainConstants.kDriveScale * direction,
                    applyDeadband(-controller.getRightX()) * DrivetrainConstants.kTurnScale * direction);
  }
}