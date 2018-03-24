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
			Drive.getInstance().runAuto(287, .7, true);
			//Turn left 90 degrees, 80 to account for gyro inaccuracy
			Drive.getInstance().autoTurn(.5, 80);
			//Zero gyro
			Drive.getInstance().starboardEncoderZero();
			//Claw up
			//This is also just a guess!!!
			CubeGrabber.getInstance().runAuto(2, .5, true);
			//Raise elevator
			//10 is just a guess!!!
			Elevator.getInstance().runAuto(10, .7, false);
			//Drive forward
			//tHIs iS aLsO jUsT a GuESs !!!! xDDDDD
			Drive.getInstance().runAuto(10, .4, true);
			//Drop cube
			CubeGrabber.getInstance().dropAuto();
			//Back away
			Drive.getInstance().runAuto(10, -.7, true);
			//Drop elevator
			Elevator.getInstance().runAuto(5, -.7, false);
		} else if(gameData.charAt(0) == 'L') {
			LeftSideSwitch l = new LeftSideSwitch();
			l.runAuto();
		} else {
			BaseLine b = new BaseLine();
			b.runAuto();
			
		}
	}
}
