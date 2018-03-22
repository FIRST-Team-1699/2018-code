package org.usfirst.frc.team1699.robot.autoModes;

import org.usfirst.frc.team1699.utils.autonomous.AutoMode;

import edu.wpi.first.wpilibj.DriverStation;

import org.usfirst.frc.team1699.robot.Constants;
import org.usfirst.frc.team1699.robot.commands.Drive;
import org.usfirst.frc.team1699.robot.commands.Elevator;
import org.usfirst.frc.team1699.robot.commands.CubeGrabber;
import org.usfirst.frc.team1699.utils.sensors.BetterGryo;



public class EitherSwitch implements AutoMode{

	@Override
	public void runAuto() {
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		//Checks for switch on right
		if(gameData.charAt(0) == 'R') {
			//if switch on right: drive to right switch from center of wall and drop crate in switch
			//Call sequence for driving to right side of switch
			rightDrive();
			//Drops cube
			dropCube();
		}else {
			//if switch on left: drive to left switch from center of wall and drop crate in switch
			//Call sequence for driving to left side of switch
			leftDrive();
			//Drops cube
			dropCube();
		}
		
		//Back away from switch
		Drive.getInstance().runAuto(Constants.DISTANCE_TO_SWITCH / 2, -0.9, false);
	}
	
	//Used to drive to the right side of the switch
	private void rightDrive(){
		//Drive halfway to switch
		Drive.getInstance().runAuto(Constants.DISTANCE_TO_SWITCH/2 - 12, 0.7, true);
		//Turn right 90 degrees, 80 to account for gyro error
		Drive.getInstance().autoTurn(0, 80);
		//Zero encoder
		Drive.getInstance().starboardEncoderZero();
		//Drive to the middle of the right side of the switch
		Drive.getInstance().runAuto(Constants.SWITCH_LENGTH / 2 + 12, 0.7, false);
		//Turn left 90 degrees, -80 to account for gyro error
		Drive.getInstance().autoTurn(0, -80);
		//Raise elevator
		Elevator.getInstance().runAuto(Constants.AUTO_SWITCH_UPPER_LIMIT, 0.7, false);
		//Zero encoder again
		Drive.getInstance().starboardEncoderZero();
		//Drive rest of distanc to switch
		Drive.getInstance().runAuto(Constants.DISTANCE_TO_SWITCH / 2 + 16, 0.7, false);
	}
	
	//Used to drive to the left side of the switch
	private void leftDrive(){
		//Drive half the distance to the switch
		Drive.getInstance().runAuto(Constants.DISTANCE_TO_SWITCH/2 - 12, 0.7, true);
		//Turn left 90 degrees, -80 to account for gyro inaccuracy
		Drive.getInstance().autoTurn(0, -80);
		//Zero encoder
		Drive.getInstance().starboardEncoderZero();
		//Drive to middle of left side of switch
		Drive.getInstance().runAuto(Constants.SWITCH_LENGTH / 2 + 20, 0.7, false);
		//Turn right 90 degrees, 80 to account for gyro inaccuracy
		Drive.getInstance().autoTurn(0, 80);
		//Raise elevator
		Elevator.getInstance().runAuto(Constants.AUTO_SWITCH_UPPER_LIMIT, 0.7, false);
		//CubeGrabber.getInstance().runAuto(0, 0.7, true);
		//Zero encoder
		Drive.getInstance().starboardEncoderZero();
		//Drive rest of distance to switch
		Drive.getInstance().runAuto(Constants.DISTANCE_TO_SWITCH / 2 + 16, 0.7, false);
	}
	
	//Used to drop the cube
	private void dropCube(){
		//Lower arm
		CubeGrabber.getInstance().runAuto(0, 0.7, true);
		//Drop cube
		CubeGrabber.getInstance().dropAuto();
	}
}


