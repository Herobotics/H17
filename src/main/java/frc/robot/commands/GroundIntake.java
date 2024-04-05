// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import static frc.robot.Constants.LauncherConstants.*;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.CANDrivetrain;
import frc.robot.subsystems.PWMLauncher;
import frc.robot.Constants.DrivetrainConstants;

// import frc.robot.subsystems.CANLauncher;

/*This is an example of creating a command as a class. The base Command class provides a set of methods that your command
 * will override.
 */
public class GroundIntake extends Command {
  /** Creates a new LaunchNote. */
  public GroundIntake(PWMLauncher launcher) {
    return launcher.getMiddleGroundSpinupCommand()
                .withTimeout(kIntakeDelay)
                .andThen(launcher.getFullGroundIntakeCommand());
    }
}
