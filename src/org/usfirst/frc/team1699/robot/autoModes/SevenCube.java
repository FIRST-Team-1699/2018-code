package org.usfirst.frc.team1699.robot.autoModes;

import org.usfirst.frc.team1699.robot.Constants;
import org.usfirst.frc.team1699.robot.commands.Drive;
import org.usfirst.frc.team1699.utils.autonomous.AutoMode;

public class SevenCube implements AutoMode {

	@Override
	public void runAuto() {
		Drive.getInstance().runAuto(Constants.DISTANCE_TO_BASELINE + 10, .7, true);
		
	}
	
}
