package org.usfirst.frc.team1699.robot.autoModes;

import org.usfirst.frc.team1699.robot.commands.CubeGrabber;
import org.usfirst.frc.team1699.utils.autonomous.AutoMode;

//Code is complete

/*
 * Does nothing
 */

public class DoNothing implements AutoMode {

	@Override
	public void runAuto() {
		System.out.println("Running Do Nothing autonomous");
		//Use for reference for claw distances
		//CubeGrabber.getInstance().runAuto(5, .5, false);
	}
}
