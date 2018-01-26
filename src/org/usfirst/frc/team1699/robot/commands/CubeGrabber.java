package org.usfirst.frc.team1699.robot.commands;

import org.usfirst.frc.team1699.robot.Constants;
import org.usfirst.frc.team1699.utils.autonomous.AutoCommand;
import org.usfirst.frc.team1699.utils.command.Command;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;

public class CubeGrabber extends Command implements AutoCommand{

	private static CubeGrabber instance;
	
	public static CubeGrabber getInstance() {
		if(instance == null) {
			instance = new CubeGrabber("CubeGrabber", Constants.CUBE_GRABBER_ID);
		}
		return instance;
	}
	
	private final DoubleSolenoid opener;
	private final VictorSP leftRotate;
	private final VictorSP rightRotate;
	
	private CubeGrabber(String name, int id) {
		super(name, id);
		opener = new DoubleSolenoid(Constants.GRABBER_SOLENOID_1, Constants.GRABBER_SOLENOID_2);
		leftRotate = new VictorSP(Constants.GRABBER_LEFT_ROTATE);
		rightRotate = new VictorSP(Constants.GRABBER_RIGHT_ROTATE);
	}

	@Override
	public void run() {
		//TODO Make it move
	}

	@Override
	public void outputToDashboard() {
		
	}

	@Override
	public void zeroAllSensors() {
		
	}
	
	@Override
	public void runAuto(double distance, double speed, boolean useSensor) {
		
	}

	@Override
	public boolean autoCommandDone() {
		return false;
	}

}
