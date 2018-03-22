package org.usfirst.frc.team1699.robot.autoModes;

import org.usfirst.frc.team1699.robot.commands.CubeGrabber;
import org.usfirst.frc.team1699.robot.commands.Drive;
import org.usfirst.frc.team1699.robot.commands.Elevator;
import org.usfirst.frc.team1699.utils.autonomous.AutoMode;

import edu.wpi.first.wpilibj.DriverStation;

public class RightEitherScale implements AutoMode{

	@Override
	public void runAuto() {
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		if(gameData.charAt(1) == 'L') {
			RightSideScale r = new RightSideScale();
			//TODO rename r
			r.runAuto();
		} else { //Cross to right side of field
			//TODO replace with EitherScale once implemented 
			//Most of these numbers are guesses, and will need to be tested and verified. 
			//They are based on math using numbers from official field drawings
			//Test during 6 hours
			//Drive forward
			Drive.getInstance().runAuto(228.7, .7, true);
			//Turn right 90 degrees, 80 to account for gyro inaccuracy
			Drive.getInstance().autoTurn(.5, -80);
			//Zero gyro
			Drive.getInstance().starboardEncoderZero();
			//Drive across field to right side
			Drive.getInstance().runAuto(227, .7, true);
			//Turn left 90 degrees, 80 to account for gyro inaccuracy
			Drive.getInstance().autoTurn(.5, 80);
			//Zero gyro
			Drive.getInstance().starboardEncoderZero();
			//Drive forward to center of scale
			Drive.getInstance().runAuto(95.3, .7, true);
			//Turn left 90 degrees, 80 to account for gyro inaccuracy
			Drive.getInstance().autoTurn(.5, 80);
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
