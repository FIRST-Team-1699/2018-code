package org.usfirst.frc.team1699.robot.autoModes;

import org.usfirst.frc.team1699.utils.autonomous.AutoMode;
import org.usfirst.frc.team1699.robot.Constants;
import org.usfirst.frc.team1699.robot.commands.Drive;
import org.usfirst.frc.team1699.robot.commands.Elevator;
import org.usfirst.frc.team1699.robot.commands.CubeGrabber;

import edu.wpi.first.wpilibj.DriverStation;

public class LeftSwitch implements AutoMode{

	@Override
	public void runAuto() {
		//Basic code for switch selection. Will change
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		if(gameData.charAt(0) == 'L')
		{
			Drive.getInstance().runAuto(Constants.DISTANCE_TO_SWITCH/2, .7, true);
			Elevator.getInstance().runAuto(Constants.AUTO_SWITCH_UPPER_LIMIT, 0.7, true);
			CubeGrabber.getInstance().runAuto(0, 0, false);
			Drive.getInstance().runAuto(Constants.DISTANCE_TO_SWITCH/2, 0.7, true);
			
		} else {
			Drive.getInstance().runAuto(Constants.DISTANCE_TO_SWITCH/2, 0.7, true);
			Elevator.getInstance().runAuto(Constants.AUTO_SWITCH_UPPER_LIMIT, 0.7, true);
			Drive.getInstance().runAuto(Constants.DISTANCE_TO_SWITCH/2, 0.7, true);
		}	
	}

}
