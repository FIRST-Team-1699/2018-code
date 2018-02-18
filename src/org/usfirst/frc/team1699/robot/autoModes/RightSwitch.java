package org.usfirst.frc.team1699.robot.autoModes;

import org.usfirst.frc.team1699.robot.Constants;
import org.usfirst.frc.team1699.robot.commands.CubeGrabber;
import org.usfirst.frc.team1699.robot.commands.Drive;
import org.usfirst.frc.team1699.robot.commands.Elevator;
import org.usfirst.frc.team1699.utils.autonomous.AutoMode;

import edu.wpi.first.wpilibj.DriverStation;

public class RightSwitch implements AutoMode{

	@Override
	public void runAuto() {
		
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		if(gameData.charAt(0) == 'R') //checks if switch is on right side
		{
			//if switch on right: drives forward and puts crate into switch
			Drive.getInstance().runAuto(Constants.DISTANCE_TO_SWITCH/2, .7, true);
			Elevator.getInstance().runAuto(Constants.AUTO_SWITCH_UPPER_LIMIT, 0.7, true);
			CubeGrabber.getInstance().runAuto(0, 0.7, true);
			Drive.getInstance().runAuto(Constants.DISTANCE_TO_SWITCH/2, 0.7, true);
			CubeGrabber.getInstance().dropAuto();
			
		}else {
			//if switch on left: drives forward and holds crate
			Drive.getInstance().runAuto(Constants.DISTANCE_TO_SWITCH/2, 0.7, true);
			Elevator.getInstance().runAuto(Constants.AUTO_SWITCH_UPPER_LIMIT, 0.7, true);
			Drive.getInstance().runAuto(Constants.DISTANCE_TO_SWITCH/2, 0.7, true);
		}	
	}

}
