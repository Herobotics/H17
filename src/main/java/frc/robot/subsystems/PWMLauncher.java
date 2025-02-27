// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.LauncherConstants.*;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PWMLauncher extends SubsystemBase {
  VictorSP m_launchWheel;
  VictorSP m_feedWheel;
  Spark m_lowerRoller;
  Spark m_upperRoller;
  Spark m_middleWheel;

  /** Creates a new Launcher. */
  public PWMLauncher() {
    m_launchWheel = new VictorSP(kLauncherID);
    m_feedWheel = new VictorSP(kFeederID);
    m_lowerRoller = new Spark(kLowerRollerID);
    m_upperRoller = new Spark(kUpperRollerID);
    m_middleWheel = new Spark(kmiddleWheelID);
  }

  /**
   * This method is an example of the 'subsystem factory' style of command creation. A method inside
   * the subsytem is created to return an instance of a command. This works for commands that
   * operate on only that subsystem, a similar approach can be done in RobotContainer for commands
   * that need to span subsystems. The Subsystem class has helper methods, such as the startEnd
   * method used here, to create these commands.
   */
  public Command getIntakeCommand() {
    // The startEnd helper method takes a method to call when the command is initialized and one to
    // call when it ends
    return this.startEnd(
        // When the command is initialized, set the wheels to the intake speed values
        () -> {
          setFeedWheel(kIntakeFeederSpeed);
          setLaunchWheel(kIntakeLauncherSpeed);
        },
        // When the command stops, stop the wheels
        () -> {
          stop();
        });
  }

  public Command getGroundSpinupCommand() {
    // The startEnd helper method takes a method to call when the command is initialized and one to
    // call when it ends
    return this.startEnd(
        // When the command is initialized, set the wheels to the intake speed values
        () -> {
              m_middleWheel.set(kmiddleWheelSpeed);
        },
        // When the command stops, stop the wheels
        () -> {
        });
  }

  public Command getFullGroundIntakeCommand() {
    // The startEnd helper method takes a method to call when the command is initialized and one to
    // call when it ends
    return this.startEnd(
        // When the command is initialized, set the wheels to the intake speed values
        () -> {
              m_lowerRoller.set(kLowerRollerSpeed);
              m_upperRoller.set(kUpperRollerSpeed);
              m_middleWheel.set(kmiddleWheelSpeed);
        },
        // When the command stops, stop the wheels
        () -> {
          stop();
        });
  }

  public Command getGroundIntakeCommand() {
    return this.getGroundSpinupCommand()
      .withTimeout(kIntakeDelay)
      .andThen(this.getFullGroundIntakeCommand())
      .handleInterrupt(() -> this.stop());
  }

  public Command getNoteOut() {
    // The startEnd helper method takes a method to call when the command is initialized and one to
    // call when it ends
    return this.startEnd(
        () -> {
              m_lowerRoller.set(-kLowerRollerSpeed);
              m_upperRoller.set(-kUpperRollerSpeed);
              setFeedWheel(-kIntakeFeederSpeed);
              setLaunchWheel(-kIntakeLauncherSpeed);
              m_middleWheel.set(-kmiddleWheelSpeed);
        },
        // When the command stops, stop the wheels
        () -> {
          stop();
        });
  }
   /**
   * Feed out the note. Launch wheel speed sold separately.
   */
  public Command getFeedWheelOutCommand() {
    // The startEnd helper method takes a method to call when the command is initialized and one to
    // call when it ends
    return this.startEnd(
        // When the command is initialized, set the wheels to the intake speed values
        () -> {
          setFeedWheel(kLaunchFeederSpeed);
          m_middleWheel.set(kmiddleWheelSpeed);
        },
        // When the command stops, stop the wheels
        () -> {
          stop();
        });
  }

  // An accessor method to set the speed (technically the output percentage) of the launch wheel
  public void setLaunchWheel(double speed) {
    m_launchWheel.set(speed);
  }

  // An accessor method to set the speed (technically the output percentage) of the feed wheel
  public void setFeedWheel(double speed) {
    m_feedWheel.set(speed);
  }

  // A helper method to stop both wheels. You could skip having a method like this and call the
  // individual accessors with speed = 0 instead
  public void stop() {
    m_launchWheel.set(0);
    m_feedWheel.set(0);
    m_lowerRoller.set(0);
    m_upperRoller.set(0);
    m_middleWheel.set(0);
  }
}
