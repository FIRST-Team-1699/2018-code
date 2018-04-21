package org.usfirst.frc.team1699.robot.autoModes;

import org.usfirst.frc.team1699.robot.commands.CubeGrabber;
import org.usfirst.frc.team1699.robot.commands.Drive;
import org.usfirst.frc.team1699.robot.commands.Elevator;
import org.usfirst.frc.team1699.utils.autonomous.AutoMode;

import edu.wpi.first.wpilibj.DriverStation;

public class RightSideScale implements AutoMode{

	/*
	 * Places cube in the scale, but only if the correct side of the scale is on the right
	 * If it is not on the right, it will place the cube on the right side of the switch, but only if the correct side is on the right
	 * If neither of those are true, it will run the BaseLine auto
	 */
	@Override
	public void runAuto() {
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		if(gameData.charAt(1) == 'R') {
			//Most of these numbers are guesses, and will need to be tested and verified. 
			//They are based on math using numbers from official field drawings
			//Test during 6 hours
			//Drive to center of the end of the scale
			//287 is just a guess!!!
			//Zero encoder just in case
			Drive.getInstance().starboardEncoderZero();
			Drive.getInstance().runAuto(287, .7, true);
			CubeGrabber.getInstance().runAuto(1, .7, true);
			//Raise elevator
			//10 is just a guess!!!
			Elevator.getInstance().runAuto(17, .7, false);
			//Turn left 90 degrees, 80 to account for gyro inaccuracy
			Drive.getInstance().autoTurn(.5, -80);
			//Claw up
			//This is also just a guess!!!
			
			//Drive forward
			//Acutally zero the encoder first
			Drive.getInstance().starboardEncoderZero();
			//tHIs iS aLsO jUsT a GuESs !!!! xDDDDD
			//Drop cube
			CubeGrabber.getInstance().dropAuto();
			//Back away
			//Acutally zero encoder first
			Drive.getInstance().starboardEncoderZero();
			Drive.getInstance().runAuto(10, -.5, true);
			//Lower elevator
			Elevator.getInstance().runAuto(-7, -.5, false);
		}
		else if(gameData.charAt(0) == 'R') {
			RightSideSwitch r = new RightSideSwitch();
			r.runAuto();
		} else {
			BaseLine b = new BaseLine();
			b.runAuto();
			
		}
	}

}
