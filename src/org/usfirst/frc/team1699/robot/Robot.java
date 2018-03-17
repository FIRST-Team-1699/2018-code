package org.usfirst.frc.team1699.robot;

import org.usfirst.frc.team1699.robot.autoModes.BaseLine;
import org.usfirst.frc.team1699.robot.autoModes.EitherSwitch;
import org.usfirst.frc.team1699.robot.autoModes.LeftSwitch;
import org.usfirst.frc.team1699.robot.autoModes.RightSwitch;
import org.usfirst.frc.team1699.robot.commands.CubeGrabber;
import org.usfirst.frc.team1699.robot.commands.Drive;
import org.usfirst.frc.team1699.robot.commands.Elevator;
import org.usfirst.frc.team1699.utils.autonomous.AutoMode;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	
	//Compressor
	private Compressor compressor;
	
	//Auto Chooser
	private SendableChooser<AutoMode> autoChooser;
	
	@Override
	public void robotInit() {
		//Compressor init
		//TODO Uncomment
		compressor = new Compressor(Constants.PCM_ID);
		compressor.setClosedLoopControl(true);
		compressor.start();
		//compressor.stop();
		
		//start camera
		CameraServer.getInstance().startAutomaticCapture();
		
		//Init for commands
		Drive.getInstance();
		CubeGrabber.getInstance();
		Elevator.getInstance();
		
		//Output to Dashboard
		Drive.getInstance().outputToDashboard();
		CubeGrabber.getInstance().outputToDashboard();
		Elevator.getInstance().outputToDashboard();
		
	}

	
	@Override
	public void autonomousInit() {
		//Auto Chooser
		autoChooser = new SendableChooser<AutoMode>();
		autoChooser.addDefault("Default", new BaseLine());
		autoChooser.addObject("Left Switch", new LeftSwitch());
		autoChooser.addObject("Right Switch", new RightSwitch());
		autoChooser.addObject("Side Chooser", new EitherSwitch());
		autoChooser.addDefault("Base Line", new BaseLine());
		SmartDashboard.putData("Auto mode chooser", autoChooser);
		
		//Run Auto
		System.out.println("Running Auto");
		((AutoMode) autoChooser.getSelected()).runAuto();
		
		//Output to Dashboard
		Drive.getInstance().outputToDashboard();
		CubeGrabber.getInstance().outputToDashboard();
		Elevator.getInstance().outputToDashboard();
		
//		Drive.getInstance().runAuto(130, .6, true);
		
		
	}

	@Override
	public void teleopPeriodic() {
		Drive.getInstance().run();
		CubeGrabber.getInstance().run();
		Elevator.getInstance().run();
		
		//Output to Dashboard
		Drive.getInstance().outputToDashboard();
		CubeGrabber.getInstance().outputToDashboard();
		Elevator.getInstance().outputToDashboard();
	}

	@Override
	public void disabledPeriodic() {
		//Output to Dashboard
		Drive.getInstance().outputToDashboard();
		CubeGrabber.getInstance().outputToDashboard();
		Elevator.getInstance().outputToDashboard();
	}
}
