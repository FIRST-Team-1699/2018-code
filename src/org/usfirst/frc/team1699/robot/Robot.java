package org.usfirst.frc.team1699.robot;

import org.usfirst.frc.team1699.robot.autoModes.BaseLine;
import org.usfirst.frc.team1699.robot.autoModes.DoNothing;
import org.usfirst.frc.team1699.robot.autoModes.EitherSwitch;
import org.usfirst.frc.team1699.robot.autoModes.LeftEitherScale;
import org.usfirst.frc.team1699.robot.autoModes.LeftEitherSwitch;
import org.usfirst.frc.team1699.robot.autoModes.LeftSideScale;
import org.usfirst.frc.team1699.robot.autoModes.LeftSideSwitch;
import org.usfirst.frc.team1699.robot.autoModes.LeftSwitch;
import org.usfirst.frc.team1699.robot.autoModes.RightEitherScale;
import org.usfirst.frc.team1699.robot.autoModes.RightEitherSwitch;
import org.usfirst.frc.team1699.robot.autoModes.RightSideScale;
import org.usfirst.frc.team1699.robot.autoModes.RightSideSwitch;
import org.usfirst.frc.team1699.robot.autoModes.RightSwitch;
import org.usfirst.frc.team1699.robot.autoModes.Turn90;
import org.usfirst.frc.team1699.robot.autoModes.Turn90TheOtherWay;
import org.usfirst.frc.team1699.robot.autoModes.TwoCube;
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
		
		//Auto Chooser
		//TODO Add new autos
		autoChooser = new SendableChooser<AutoMode>();
		autoChooser.addDefault("Base Line", new BaseLine());
		autoChooser.addObject("Left Switch", new LeftSwitch());
		autoChooser.addObject("Right Switch", new RightSwitch());
		autoChooser.addObject("Side Chooser", new EitherSwitch());
		autoChooser.addObject("Left Either Scale", new LeftEitherScale());
		autoChooser.addObject("Left Either Switch", new LeftEitherSwitch());
		autoChooser.addObject("Left Side Scale", new LeftSideScale());
		autoChooser.addObject("Left Side Switch", new LeftSideSwitch());
		autoChooser.addObject("Right Either Scale", new RightEitherScale());
		autoChooser.addObject("Right Either Switch", new RightEitherSwitch());
		autoChooser.addObject("Right Side Switch", new RightSideSwitch());
		autoChooser.addObject("Right Side Scale", new RightSideScale());
		autoChooser.addObject("Two Cube", new TwoCube());
		autoChooser.addObject("Do Nothing", new DoNothing());
		autoChooser.addObject("Turn 90", new Turn90());
		autoChooser.addObject("Turn 90 the other way", new Turn90TheOtherWay());
		SmartDashboard.putData("Auto mode chooser", autoChooser);
		
		//Output to Dashboard
		outputAllToDashboard();
	}

	
	@Override
	public void autonomousInit() {
		//Run Auto
		System.out.println("Running Auto");
		((AutoMode) autoChooser.getSelected()).runAuto();
		
		//Output to Dashboard
		outputAllToDashboard();	
	}

	@Override
	public void teleopPeriodic() {
		Drive.getInstance().run();
		CubeGrabber.getInstance().run();
		Elevator.getInstance().run();
		
		//Output to Dashboard
		outputAllToDashboard();
	}

	@Override
	public void disabledPeriodic() {
		//Output to Dashboard
		outputAllToDashboard();
	}
	
	//TODO Should work, but test
	//Outputs commands to dashboard
	private void outputAllToDashboard(){
		Drive.getInstance().outputToDashboard();
		CubeGrabber.getInstance().outputToDashboard();
		Elevator.getInstance().outputToDashboard();
	}
}
