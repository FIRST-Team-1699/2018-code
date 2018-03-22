package org.usfirst.frc.team1699.robot.autoModes;

import org.usfirst.frc.team1699.robot.Constants;
import org.usfirst.frc.team1699.robot.commands.CubeGrabber;
import org.usfirst.frc.team1699.robot.commands.Drive;
import org.usfirst.frc.team1699.robot.commands.Elevator;
import org.usfirst.frc.team1699.utils.autonomous.AutoMode;

import edu.wpi.first.wpilibj.DriverStation;

/*
 * Drives forward and places cube is switch is on right side, else, drives to baseline and raises elevator
 */

public class RightSwitch implements AutoMode{

	@Override
	public void runAuto() {
		
		System.out.println("Running Right Switch");
		
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		if(gameData.charAt(0) == 'R') //checks if switch is on right side
		{
			System.out.println("Start");
			Drive.getInstance().runAuto(Constants.DISTANCE_TO_SWITCH/2, .9, true);
			System.out.println("Drive 1");
			Elevator.getInstance().runAuto(Constants.AUTO_SWITCH_UPPER_LIMIT, 0.5, true);
			System.out.println("Elevator");
			Drive.getInstance().runAuto(Constants.DISTANCE_TO_SWITCH, 0.9, true);
			System.out.println("Drive 2");
			CubeGrabber.getInstance().runAuto(100, 0.7, true);
			System.out.println("CubeGrabber 1");
			CubeGrabber.getInstance().dropAuto();
			Drive.getInstance().runAuto(Constants.DISTANCE_TO_SWITCH / 2, -0.9, true);
			System.out.println("End");
		}else {
			//if switch on left: drives forward and holds crate
			Drive.getInstance().runAuto(Constants.DISTANCE_TO_SWITCH/2, 0.7, true);
			Elevator.getInstance().runAuto(Constants.AUTO_SWITCH_UPPER_LIMIT, 0.7, true);
			Drive.getInstance().runAuto(Constants.DISTANCE_TO_SWITCH/2, 0.7, true);
		}	
	}

}
