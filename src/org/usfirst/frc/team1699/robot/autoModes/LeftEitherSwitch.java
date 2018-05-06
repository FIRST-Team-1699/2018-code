package org.usfirst.frc.team1699.robot.autoModes;

import org.usfirst.frc.team1699.robot.Constants;
import org.usfirst.frc.team1699.robot.commands.CubeGrabber;
import org.usfirst.frc.team1699.robot.commands.Drive;
import org.usfirst.frc.team1699.robot.commands.Elevator;
import org.usfirst.frc.team1699.utils.autonomous.AutoMode;

import edu.wpi.first.wpilibj.DriverStation;

public class LeftEitherSwitch implements AutoMode{

	/*
	 * Places cube on the proper side of the switch, starting on the left side of the field
	 */
	@Override
	public void runAuto() {
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		System.out.println(gameData);
		if(gameData.charAt(0) == 'L') {
			LeftSideSwitch l = new LeftSideSwitch();
			l.runAuto();
		}
		else {
			Drive.getInstance().runAuto(229, .7, true);
			//Turn right 90 degrees, 80 to account for gyro inaccuracy
			Drive.getInstance().autoTurn(.7, 90);
			//Zero gyro
			Drive.getInstance().starboardEncoderZero();
			//Drive across field to right side
			Drive.getInstance().runAuto(227, .7, false);
			//Turn right 90 degrees, 80 to account for gyro inaccuracy
			Drive.getInstance().autoTurn(.7, 90);
			//Zero gyro
			Drive.getInstance().starboardEncoderZero();
			//Drive forward to line up with middle of switch
			Drive.getInstance().runAuto(61, .7, false);
			//Turn right 90 degrees, 80 to account for gyro inaccuracy
			Drive.getInstance().autoTurn(.7, 90);
			//Now facing switch
			//Zero gyro
			Drive.getInstance().starboardEncoderZero();
			//Raise elevator
			Elevator.getInstance().runAuto(Constants.AUTO_SWITCH_UPPER_LIMIT, .5, false);
			//Claw down
			CubeGrabber.getInstance().runAuto(1, .5, false);
			//Drive forward to switch
			Drive.getInstance().runAuto(22, .7, false);
			//Drop cube
			CubeGrabber.getInstance().dropAuto();
			//Back away
			Drive.getInstance().runAuto(24, -.9, false);
		}
	}

}
