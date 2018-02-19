package org.usfirst.frc.team1699.robot.autoModes;

import org.usfirst.frc.team1699.utils.autonomous.AutoMode;

import edu.wpi.first.wpilibj.DriverStation;

import org.usfirst.frc.team1699.robot.Constants;
import org.usfirst.frc.team1699.robot.commands.Drive;
import org.usfirst.frc.team1699.robot.commands.Elevator;
import org.usfirst.frc.team1699.robot.commands.CubeGrabber;
import org.usfirst.frc.team1699.utils.sensors.BetterGryo;



public class EitherSwitch implements AutoMode{

	@Override
	public void runAuto() {
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		//checks for switch on right
		if(gameData.charAt(0) == 'R') {
			//if switch on right: drive to right switch from center of wall and drop crate in switch
			Drive.getInstance().runAuto(Constants.DISTANCE_TO_SWITCH/2, 0.7, true);
			Drive.getInstance().auto2Right(0, 90);
			Drive.getInstance().runAuto(Constants.SWITCH_LENGTH/2, 0.7, true);
			Drive.getInstance().auto2Left(0, 90);
			Elevator.getInstance().runAuto(Constants.AUTO_SWITCH_UPPER_LIMIT, 0.7, true);
			CubeGrabber.getInstance().runAuto(0, 0.7, true);
			Drive.getInstance().runAuto(Constants.DISTANCE_TO_SWITCH/2, 0.7, true);
			CubeGrabber.getInstance().dropAuto();
		}else {
			//if switch on left: drive to left switch from center of wall and drop crate in switch
			Drive.getInstance().runAuto(Constants.DISTANCE_TO_SWITCH/2, 0.7, true);
			Drive.getInstance().auto2Left(0, 90);
			Drive.getInstance().runAuto(Constants.SWITCH_LENGTH/2, 0.7, true);
			Drive.getInstance().auto2Right(0, 90);
			Elevator.getInstance().runAuto(Constants.AUTO_SWITCH_UPPER_LIMIT, 0.7, true);
			CubeGrabber.getInstance().runAuto(0, 0.7, true);
			Drive.getInstance().runAuto(Constants.DISTANCE_TO_SWITCH/2, 0.7, true);
			CubeGrabber.getInstance().dropAuto();
		}
	}
}


