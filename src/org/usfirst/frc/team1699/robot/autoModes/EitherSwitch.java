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
			Drive.getInstance().runAuto(Constants.DISTANCE_TO_SWITCH/2 - 12, 0.7, true);
			Drive.getInstance().autoTurn(0, 80);
			Drive.getInstance().starboardEncoderZero();
			Drive.getInstance().runAuto(Constants.SWITCH_LENGTH / 2 + 12, 0.7, false);
			Drive.getInstance().autoTurn(0, -80);
			Elevator.getInstance().runAuto(Constants.AUTO_SWITCH_UPPER_LIMIT, 0.7, false);
			Drive.getInstance().starboardEncoderZero();
			Drive.getInstance().runAuto(Constants.DISTANCE_TO_SWITCH / 2 + 16, 0.7, false);
			CubeGrabber.getInstance().runAuto(0, 0.7, true);
			CubeGrabber.getInstance().dropAuto();
			Drive.getInstance().runAuto(Constants.DISTANCE_TO_SWITCH / 2, -0.9, false);
		}else {
			//if switch on left: drive to left switch from center of wall and drop crate in switch
			Drive.getInstance().runAuto(Constants.DISTANCE_TO_SWITCH/2 - 12, 0.7, true);
			Drive.getInstance().autoTurn(0, -80);
			Drive.getInstance().starboardEncoderZero();
			Drive.getInstance().runAuto(Constants.SWITCH_LENGTH / 2 + 20, 0.7, false);
			Drive.getInstance().autoTurn(0, 80);
			Elevator.getInstance().runAuto(Constants.AUTO_SWITCH_UPPER_LIMIT, 0.7, false);
			//CubeGrabber.getInstance().runAuto(0, 0.7, true);
			Drive.getInstance().starboardEncoderZero();
			Drive.getInstance().runAuto(Constants.DISTANCE_TO_SWITCH / 2 + 16, 0.7, false);
			CubeGrabber.getInstance().runAuto(0, 0.7, false);
			CubeGrabber.getInstance().dropAuto();
			Drive.getInstance().runAuto(Constants.DISTANCE_TO_SWITCH / 2, -0.9, false);
		}
	}
}


