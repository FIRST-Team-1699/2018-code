package org.usfirst.frc.team1699.robot.autoModes;

import org.usfirst.frc.team1699.robot.commands.CubeGrabber;
import org.usfirst.frc.team1699.robot.commands.Drive;
import org.usfirst.frc.team1699.robot.commands.Elevator;
import org.usfirst.frc.team1699.utils.autonomous.AutoMode;

import edu.wpi.first.wpilibj.DriverStation;

public class LeftEitherScale implements AutoMode{

	/*
	 * Places the cube on the left side of the scale, if that is the correct side
	 * If not, places it on the left side of the scale, if that is the correct side
	 * If all that doesn't work, runs the baseline auto
	 */
	@Override
	public void runAuto() {
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		if(gameData.charAt(1) == 'L') {
			LeftSideScale leftScale = new LeftSideScale();
			leftScale.runAuto();
		} else { //Cross to right side of field
			//TODO replace with an EitherScale once implemented
			//Most of these numbers are guesses, and will need to be tested and verified. 
			//They are based on math using numbers from official field drawings
			//Test during 6 hours
			//Drive forward
			Drive.getInstance().runAuto(229, .7, true);
			//Turn right 90 degrees, 80 to account for gyro inaccuracy
			Drive.getInstance().autoTurn(.5, 90);
			//Zero gyro
			Drive.getInstance().starboardEncoderZero();
			//Drive across field to right side
			Drive.getInstance().runAuto(227, .7, true);
			//Turn left 90 degrees, 80 to account for gyro inaccuracy
			Drive.getInstance().autoTurn(.5, -90);
			//Zero gyro
			Drive.getInstance().starboardEncoderZero();
			//Drive forward to center of scale
			Drive.getInstance().runAuto(95, .7, true);
			//Turn left 90 degrees, 80 to account for gyro inaccuracy
			Drive.getInstance().autoTurn(0, -90);
			//Zero gyro
			Drive.getInstance().starboardEncoderZero();
			//Lower claw
			CubeGrabber.getInstance().runAuto(2, .5, false);
			//Raise elevator
			Elevator.getInstance().runAuto(10, .7, false);
			//Drive forward to scale
			Drive.getInstance().runAuto(10, .4, true);
			//Drop cube
			CubeGrabber.getInstance().dropAuto();
			//Back away
			Drive.getInstance().runAuto(15, -.7, true);
			//Lower elevator
			Elevator.getInstance().runAuto(5, -.5, false);
		}
	}

}
