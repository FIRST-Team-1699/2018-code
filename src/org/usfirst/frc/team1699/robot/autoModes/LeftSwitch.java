package org.usfirst.frc.team1699.robot.autoModes;

import org.usfirst.frc.team1699.utils.autonomous.AutoMode;
import org.usfirst.frc.team1699.robot.Constants;
import org.usfirst.frc.team1699.robot.commands.Drive;
import org.usfirst.frc.team1699.robot.commands.Elevator;
import org.usfirst.frc.team1699.robot.commands.CubeGrabber;

import edu.wpi.first.wpilibj.DriverStation;

/*
 * Drives forward and places cube is switch is on left side, else, drives to baseline and raises elevator
 */

public class LeftSwitch implements AutoMode{

	@Override
	public void runAuto() {
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		System.out.println(gameData);
		if(gameData.charAt(0) == 'L')//checks if switch is on right side
		{
			//if switch on left: drives forward and puts crate into switch
			System.out.println("Start");
			//Drive halfway to switch
			Drive.getInstance().runAuto(Constants.DISTANCE_TO_SWITCH/2, .7, true);
			System.out.println("Drive 1");
			//Raise elevator
			Elevator.getInstance().runAuto(Constants.AUTO_SWITCH_UPPER_LIMIT, 0.5, true);
			System.out.println("Elevator");
			//Drive rest of way to switch
			Drive.getInstance().runAuto(Constants.DISTANCE_TO_SWITCH, 0.7, true);
			System.out.println("Drive 2");
			//Lower claw
			CubeGrabber.getInstance().runAuto(100, 0.7, true);
			System.out.println("CubeGrabber 1");
			//Drop cube
			CubeGrabber.getInstance().dropAuto();
			//Back away from switch
			Drive.getInstance().runAuto(Constants.DISTANCE_TO_SWITCH / 2, -0.6, true);
			System.out.println("End");
		} else {
			//if switch on right: drives forward and holds crate
			//Drive forward
			Drive.getInstance().runAuto(Constants.DISTANCE_TO_SWITCH/2, 0.7, true);
			//Raise elevator
			Elevator.getInstance().runAuto(Constants.AUTO_SWITCH_UPPER_LIMIT, 0.7, true);
			//Drive rest of way to switch
			Drive.getInstance().runAuto(Constants.DISTANCE_TO_SWITCH/2, 0.7, true);
		}	
	}

}
