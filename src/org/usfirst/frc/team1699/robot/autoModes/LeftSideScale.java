package org.usfirst.frc.team1699.robot.autoModes;

import org.usfirst.frc.team1699.robot.commands.CubeGrabber;
import org.usfirst.frc.team1699.robot.commands.Drive;
import org.usfirst.frc.team1699.robot.commands.Elevator;
import org.usfirst.frc.team1699.utils.autonomous.AutoMode;

import edu.wpi.first.wpilibj.DriverStation;

public class LeftSideScale implements AutoMode{
	
	@Override
	public void runAuto() {
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		if(gameData.charAt(1) == 'L') {
			//Drive to center of the end of the scale
			//286.65 is just a guess!!!
			Drive.getInstance().runAuto(286.65, .7, true);
			//Turn left 90 degrees, 80 to account for gyro inaccuracy
			Drive.getInstance().autoTurn(.5, 80);
			//Raise elevator
			//10 is just a guess!!!
			Elevator.getInstance().runAuto(10, .7, false);
			//Claw up
			//This is also just a guess!!!
			CubeGrabber.getInstance().runAuto(0, .5, true);
			//Drive forward
			//tHIs iS aLsO jUsT a GuESs !!!! xDDDDD
			Drive.getInstance().runAuto(10, .7, true);
			//Drop cube
			CubeGrabber.getInstance().dropAuto();
			//Back away
			Drive.getInstance().runAuto(10, -.9, true);
		}
		else if(gameData.charAt(0) == 'L') {
			LeftSideSwitch l = new LeftSideSwitch();
			l.runAuto();
		}
		else {
			BaseLine b = new BaseLine();
			b.runAuto();
			
		}
	}
}
