// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import static frc.robot.Constants.DrivetrainConstants.kAutoDriveTime;
import static frc.robot.Constants.DrivetrainConstants.kAutoSpeed;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Constants.LauncherConstants;
import frc.robot.subsystems.CANDrivetrain;
import frc.robot.subsystems.PWMDrivetrain;
import frc.robot.subsystems.PWMLauncher;

// import frc.robot.subsystems.CANDrivetrain;

public final class Autos {

  /** Example static factory for an autonomous command. */
  public static Command exampleAuto(CANDrivetrain drivetrain, PWMLauncher m_launcher) {
    /**
     * RunCommand is a helper class that creates a command from a single method, in this case we
     * pass it the arcadeDrive method to drive straight back at half power. We modify that command
     * with the .withTimeout(1) decorator to timeout after 1 second, and use the .andThen decorator
     * to stop the drivetrain after the first command times out
     */
    //  return new PrintCommand("starting aut")
    //  //.andThen( Commands.run (() -> drivetrain.drive(0, 0, 0), drivetrain))
    //  .andThen( new PrintCommand("..start prepare..."))
    //  .andThen( new PrepareLaunch(m_launcher).withTimeout(LauncherConstants.kLauncherDelay))
    //  .andThen( new PrintCommand("..start launch..."))
    //  .andThen( new LaunchNote(m_launcher).withTimeout(.5))
    //  .andThen( new PrintCommand("..ended!..."))
    //  ;

     return Commands.runOnce(() -> drivetrain.drive(0,0,0), drivetrain)
            .andThen(new PrepareLaunch(m_launcher)
                .withTimeout(LauncherConstants.kLauncherDelay))
        .andThen(new LaunchNote(m_launcher)
                .withTimeout(0.5))
        .andThen(Commands.run(() -> drivetrain.drive(kAutoSpeed, 0, 0), drivetrain)
                .withTimeout(kAutoDriveTime))
        .andThen(Commands.run(() -> drivetrain.drive(0, 0, 0), drivetrain));
       
  }

  private Autos() {
    throw new UnsupportedOperationException("This is a utility class!");
  }
}
