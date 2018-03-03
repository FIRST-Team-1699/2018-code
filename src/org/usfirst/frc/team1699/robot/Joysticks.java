package org.usfirst.frc.team1699.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Joysticks {
	
	//TODO Rework how to transfer user input. This is not a clean method.
	
	private final Joystick driveInstance;
	private final Joystick operatorInstance;
	
	private static Joysticks instance;
	
	/*
	 * @return The current instance of the joysticks
	 */
	public static Joysticks getInstance(){
		if(instance == null){
			instance = new Joysticks();
		}
		return instance;
	}
	
	/*
	 * This is a constructor for the Joysticks class, which is responsible for everything to do with joysticks.
	 * Jakob told me not to comment this but I will anyway
	 */
	private Joysticks(){
		driveInstance = new Joystick(Constants.DRIVE_STICK_PORT);
		operatorInstance = new Joystick(Constants.OPERATOR_STICK_PORT);
	}
	
	/*
	 * @return The drive stick
	 */
	public Joystick getDriveStick(){
		return this.driveInstance;
	}
	
	/*
	 * @return The operator stick
	 */
	public Joystick getOperatorStick(){
		return this.operatorInstance;
	}
	
}
