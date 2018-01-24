package org.usfirst.frc.team1699.robot.commands;

import org.usfirst.frc.team1699.robot.Constants;
import org.usfirst.frc.team1699.utils.autonomous.AutoCommand;
import org.usfirst.frc.team1699.utils.command.Command;

public class CubeGrabber extends Command implements AutoCommand{

	private static CubeGrabber instance;
	
	public static CubeGrabber getInstance() {
		if(instance == null) {
			instance = new CubeGrabber("CubeGrabber", Constants.CUBE_GRABBER_ID);
		}
		return instance;
	}
	
	private CubeGrabber(String name, int id) {
		super(name, id);
	}

	@Override
	public void runAuto(double distance, double speed, boolean useSensor) {
		
	}

	@Override
	public boolean autoCommandDone() {
		return false;
	}

	@Override
	public void run() {
		
	}

	@Override
	public void outputToDashboard() {
		
	}

	@Override
	public void zeroAllSensors() {
		
	}

}
