package org.usfirst.frc.team1699.robot.autoModes;

import org.usfirst.frc.team1699.robot.Constants;
import org.usfirst.frc.team1699.robot.commands.Drive;
import org.usfirst.frc.team1699.utils.autonomous.AutoMode;

public class BaseLine implements AutoMode{

	@Override
	public void runAuto() {
		//drives to baseline
		Drive.getInstance().runAuto(Constants.DISTANCE_TO_BASELINE + 10, .7, true);
	}

}
