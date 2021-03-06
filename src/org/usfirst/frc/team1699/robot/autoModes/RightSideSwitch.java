package org.usfirst.frc.team1699.robot.autoModes;

import org.usfirst.frc.team1699.robot.Constants;
import org.usfirst.frc.team1699.robot.commands.CubeGrabber;
import org.usfirst.frc.team1699.robot.commands.Drive;
import org.usfirst.frc.team1699.robot.commands.Elevator;
import org.usfirst.frc.team1699.utils.autonomous.AutoMode;

import edu.wpi.first.wpilibj.DriverStation;

public class RightSideSwitch implements AutoMode{

	/*
	 * Starts at right side, and places cube on the side of the switch, but only if the correct side of the switch is on the right
	 * If it is not on the right, it will run the baseline auto
	 */
	@Override
	public void runAuto() {
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		//checks for switch on right
		if(gameData.charAt(0) == 'R') {
			//Drive forward to end of side of switch
			Drive.getInstance().runAuto(159, .7, true);
			//Turn left 90 degrees, -80 to compensate for gyro inaccuracy
			Drive.getInstance().autoTurn(.5, -90);
			//Zero encoder
			Drive.getInstance().starboardEncoderZero();
			//Raise elevator
			Elevator.getInstance().runAuto(Constants.AUTO_SWITCH_UPPER_LIMIT, .7, false);
			//Drive forward to switch
			Drive.getInstance().runAuto(24, .7, true);
			//Raise arm
			CubeGrabber.getInstance().runAuto(0, .7, false);
			//Drop cube
			CubeGrabber.getInstance().dropAuto();
			//Back away
			Drive.getInstance().runAuto(24, .9, true);
			//Drop elevator
			Elevator.getInstance().runAuto(1, .5, false);
		} else {
			BaseLine b = new BaseLine();
			b.runAuto();
		}
	}
	

}
