package org.usfirst.frc.team1699.robot.autoModes;

import org.usfirst.frc.team1699.utils.autonomous.AutoMode;

import edu.wpi.first.wpilibj.DriverStation;

public class LeftSwitch implements AutoMode{

	@Override
	public void runAuto() {
		//Basic code for switch selection. Will change
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		if(gameData.charAt(0) == 'L')
		{
			//Put left auto code here
		} else {
			//Put right auto code here
		}	
	}

}
