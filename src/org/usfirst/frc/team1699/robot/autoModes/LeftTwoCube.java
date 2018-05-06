package org.usfirst.frc.team1699.robot.autoModes;

import org.usfirst.frc.team1699.robot.commands.CubeGrabber;
import org.usfirst.frc.team1699.robot.commands.Drive;
import org.usfirst.frc.team1699.robot.commands.Elevator;
import org.usfirst.frc.team1699.utils.autonomous.AutoMode;

import edu.wpi.first.wpilibj.DriverStation;

public class LeftTwoCube implements AutoMode{

	@Override
	public void runAuto() {
		String gamedata;
		gamedata = DriverStation.getInstance().getGameSpecificMessage();
		if(gamedata.charAt(0) == 'L' && gamedata.charAt(1) == 'L') {
			//Drive forward to scale, 230 inches at full speed
			Drive.getInstance().runAuto(230, 1, true);
			//Turn left 35 degrees at .5 speed
			Drive.getInstance().autoTurn(.5, 35);
			//Lower claw
			CubeGrabber.getInstance().runAuto(1, .5, false);
			//Raise elevator to full height at max speed
			Elevator.getInstance().runAuto(13, 1, false);
			//Zero encoder
			Drive.getInstance().starboardEncoderZero();
			//Drive forward to scale, 14 inches at .7 speed
			Drive.getInstance().runAuto(14, .7, false);
			//Drop cube
			CubeGrabber.getInstance().dropAuto();
			//Zero encoder
			Drive.getInstance().starboardEncoderZero();
			//Back up from scale, 24 inches at .7 speed
			Drive.getInstance().runAuto(-24, -.7, false);
			//Lower elevator at near max speed
			Elevator.getInstance().runAuto(9, -.9, false);
			//Turn left 95 degrees at .5 speed, hopefully to line up with cube
			Drive.getInstance().autoTurn(.5, 98);
			//Zero encoder
			Drive.getInstance().starboardEncoderZero();
			//Drive forward 25 inches at .7 speed to hopefully line up to grab cube
			Drive.getInstance().runAuto(25, .7, false);
		}
		
	}

}
