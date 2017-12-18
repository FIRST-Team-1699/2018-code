package org.usfirst.frc.team1699.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Joysticks {
	
	private final Joystick driveInstance;
	private final Joystick operatorInstance;
	
	private static Joysticks instance;
	
	public static Joysticks getInstance(){
		if(instance == null){
			instance = new Joysticks();
		}
		return instance;
	}
	
	private Joysticks(){
		driveInstance = new Joystick(Constants.DRIVE_STICK_PORT);
		operatorInstance = new Joystick(Constants.OPERATOR_STICK_PORT);
	}
	
	public Joystick getDriveStick(){
		return this.driveInstance;
	}
	
	public Joystick getOperatorStick(){
		return this.operatorInstance;
	}
	
}
