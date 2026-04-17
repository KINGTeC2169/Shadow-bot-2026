// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.HootAutoReplay;
import com.ctre.phoenix6.SignalLogger;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.generated.TunerConstants;

public class Robot extends TimedRobot {
    private Command m_autonomousCommand;

    private final RobotContainer m_robotContainer;

    /* log and replay timestamp and joystick data */
    private final HootAutoReplay m_timeAndJoystickReplay = new HootAutoReplay()
        .withTimestampReplay()
        .withJoystickReplay();

    public Robot() {
        m_robotContainer = new RobotContainer();
        SignalLogger.setPath("/u/logs");
        SignalLogger.start();
        
    }

    @Override
    public void robotPeriodic() {
        m_timeAndJoystickReplay.update();
        CommandScheduler.getInstance().run(); 

        m_robotContainer.xSpeed = -m_robotContainer.joystick.getLeftY() * m_robotContainer.MaxSpeed * m_robotContainer.speed;
        m_robotContainer.ySpeed= -m_robotContainer.joystick.getLeftX() * m_robotContainer.MaxSpeed * m_robotContainer.speed;
        SmartDashboard.putNumber("xSetSpeed", m_robotContainer.xSpeed);
        SmartDashboard.putNumber("xSpeed", m_robotContainer.drivetrain.getState().Speeds.vxMetersPerSecond);
        SmartDashboard.putNumber("ySetSpeed", m_robotContainer.ySpeed);
        SmartDashboard.putNumber("ySpeed", m_robotContainer.drivetrain.getState().Speeds.vyMetersPerSecond);
        SmartDashboard.putNumber("KP", TunerConstants.KP);
        SmartDashboard.putNumber("KD", TunerConstants.KD);
        SmartDashboard.putNumber("KI", TunerConstants.KI);
       
    }
    //PUSH TEST
    //another test

    @Override
    public void disabledInit() {}

    @Override
    public void disabledPeriodic() {}

    @Override
    public void disabledExit() {}

    @Override
    public void autonomousInit() {
        m_autonomousCommand = m_robotContainer.getAutonomousCommand();

        if (m_autonomousCommand != null) {
            CommandScheduler.getInstance().schedule(m_autonomousCommand);
        }
    }

    @Override
    public void autonomousPeriodic() {}

    @Override
    public void autonomousExit() {}

    @Override
    public void teleopInit() {
        if (m_autonomousCommand != null) {
            CommandScheduler.getInstance().cancel(m_autonomousCommand);
        }
    }

    @Override
    public void teleopPeriodic() {
        m_robotContainer.setSpeed();
        TunerConstants.KP = SmartDashboard.getNumber("KP",8);
        TunerConstants.KI = SmartDashboard.getNumber("KI", 0);
        TunerConstants.KD = SmartDashboard.getNumber("KD", 0);
    }

    @Override

    public void teleopExit() {}

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic() {}

    @Override
    public void testExit() {}

    @Override
    public void simulationPeriodic() {}
}
