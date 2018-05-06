package org.usfirst.frc.team1699.robot.autoModes;

import org.usfirst.frc.team1699.robot.Constants;
import org.usfirst.frc.team1699.robot.commands.CubeGrabber;
import org.usfirst.frc.team1699.robot.commands.Drive;
import org.usfirst.frc.team1699.robot.commands.Elevator;
import org.usfirst.frc.team1699.utils.autonomous.AutoMode;

import edu.wpi.first.wpilibj.DriverStation;

public class LeftSideSwitch implements AutoMode{

	/*
	 * Places cube on the left side of the switch, not the front
	 * But only if that's the correct side
	 * Runs the baseline if it's on the other side
	 */
	@Override
	public void runAuto() {
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		//checks for switch on right
		if(gameData.charAt(0) == 'L') {
			//Drive forward to end of side of switch
			Drive.getInstance().runAuto(130, .9, true);
			//Turn right 90 degrees, -80 to compensate for gyro inaccuracy
			Drive.getInstance().autoTurn(.5, 90);
			//Zero encoder
			Drive.getInstance().starboardEncoderZero();
			//Raise elevator
			Elevator.getInstance().runAuto(Constants.AUTO_SWITCH_UPPER_LIMIT, .7, false);
			//Drive forward to switch
			Drive.getInstance().runAuto(18, .7, false);
			//Raise arm
			CubeGrabber.getInstance().runAuto(1, .7, false);
			//Drop cube
			CubeGrabber.getInstance().dropAuto();
			//Back away
			Drive.getInstance().runAuto(-24, -.9, false);
			//Drop elevator
			Elevator.getInstance().runAuto(1, -.5, false);
		} else {
			BaseLine baseLine = new BaseLine();
			baseLine.runAuto();
		}
	}

}
