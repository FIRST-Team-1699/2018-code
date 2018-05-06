package org.usfirst.frc.team1699.robot.autoModes;

import org.usfirst.frc.team1699.robot.commands.Drive;
import org.usfirst.frc.team1699.utils.autonomous.AutoMode;

public class Turn90 implements AutoMode{

	@Override
	public void runAuto() {
		Drive.getInstance().autoTurn(.3, 90);
	}

}
