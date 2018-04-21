package org.usfirst.frc.team1699.robot.autoModes;

import org.usfirst.frc.team1699.robot.Constants;
import org.usfirst.frc.team1699.robot.commands.CubeGrabber;
import org.usfirst.frc.team1699.robot.commands.Drive;
import org.usfirst.frc.team1699.robot.commands.Elevator;
import org.usfirst.frc.team1699.utils.autonomous.AutoMode;

import edu.wpi.first.wpilibj.DriverStation;

public class TwoCube implements AutoMode{

	@Override
	public void runAuto() {
		String gamedata;
		gamedata = DriverStation.getInstance().getGameSpecificMessage();
		if(gamedata.charAt(0) == 'R' && gamedata.charAt(1) == 'R') {
			RightSideScale rs = new RightSideScale();
			//Leaves robot in null zone
			rs.runAuto();
			//Zero gyro
			Drive.getInstance().starboardEncoderZero();
			//Turn left 90 degrees, 80 to account for gyro inaccuracy
			Drive.getInstance().autoTurn(.5, -80);
			//Zero gyro
			Drive.getInstance().starboardEncoderZero();
			//Drive forward
			Drive.getInstance().runAuto(95, .7, true);
			//Turn right 90
			Drive.getInstance().autoTurn(.5, 80);
			//Turn left 90
			Drive.getInstance().starboardEncoderZero();
			//Drive forward
			Drive.getInstance().runAuto(24, .7, true);
			//Turn left 90
			Drive.getInstance().autoTurn(.5, -80);
			//Drive to cube
			Drive.getInstance().runAuto(10, .5, true);
			//Grab cube
			CubeGrabber.getInstance().dropAuto();
			//Back up a little
			Drive.getInstance().runAuto(5, .5, true);
			//Raise elevator
			Elevator.getInstance().runAuto(Constants.AUTO_SWITCH_UPPER_LIMIT, .7, false);
			//Drive forward
			Drive.getInstance().runAuto(5, .5, true);
			//Drop cube
			CubeGrabber.getInstance().dropAuto();
		}
	}

}
