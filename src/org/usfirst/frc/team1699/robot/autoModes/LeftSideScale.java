package org.usfirst.frc.team1699.robot.autoModes;

import org.usfirst.frc.team1699.robot.commands.CubeGrabber;
import org.usfirst.frc.team1699.robot.commands.Drive;
import org.usfirst.frc.team1699.robot.commands.Elevator;
import org.usfirst.frc.team1699.utils.autonomous.AutoMode;

import edu.wpi.first.wpilibj.DriverStation;

public class LeftSideScale implements AutoMode{
	
	/*
	 * Places cube on the left side of the scale, if that's the correct side
	 * If the left is not the proper side of the scale, it places it in the left side of the switch, but only if that's the proper side of the switch
	 * If all that is on the wrong side, it just runs the baseline
	 */
	@Override
	public void runAuto() {
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		if(gameData.charAt(1) == 'L') {
			//Most of these numbers are guesses, and will need to be tested and verified. 
			//They are based on math using numbers from official field drawings
			//Test during 6 hours
			//Drive to center of the end of the scale
			//286.65 is just a guess!!!
			Drive.getInstance().runAuto(292, 1, true);
			CubeGrabber.getInstance().runAuto(1, .7, true);
			Drive.getInstance().autoTurn(.7, 90);
			Drive.getInstance().starboardEncoderZero();
			Drive.getInstance().runAuto(-36, -1, false);
			Elevator.getInstance().runAuto(13, 1, false);
			Drive.getInstance().starboardEncoderZero();
			Drive.getInstance().runAuto(19, .6, false);
			//Drop cube
			CubeGrabber.getInstance().dropAuto();
			//Back away
			Drive.getInstance().starboardEncoderZero();
			Drive.getInstance().runAuto(-10, -1, true);
			//Drop elevator
			Elevator.getInstance().runAuto(9, -.9, false);
		} else if(gameData.charAt(0) == 'L') {
			LeftSideSwitch l = new LeftSideSwitch();
			l.runAuto();
		} else {
			BaseLine b = new BaseLine();
			b.runAuto();
			
		}
	}
}
