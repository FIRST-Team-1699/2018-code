package org.usfirst.frc.team1699.robot.commands;

import org.usfirst.frc.team1699.robot.Constants;
import org.usfirst.frc.team1699.robot.Joysticks;
import org.usfirst.frc.team1699.utils.autonomous.AutoCommand;
import org.usfirst.frc.team1699.utils.command.Command;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CubeGrabber extends Command implements AutoCommand{

	private static CubeGrabber instance;
	
	//creates instance of CubeGrabber
	public static CubeGrabber getInstance() {
		if(instance == null) {
			instance = new CubeGrabber("CubeGrabber", Constants.CUBE_GRABBER_ID);
		}
		return instance;
	}
	
	private final DoubleSolenoid opener;
	
	private final VictorSP leftRotate;
	private final VictorSP rightRotate;
	
	private final Encoder rotateEncoder;
	
	//Joystick Utils
	private boolean released = true;
	
	private boolean clawOpen = false;
	
	//motor limits
	private double upper_motor_limit = 1;
	private double lower_motor_limit = -1;
	
	/**
	 * interprets instance of CubeGrabber
	 * 
	 * @param name Name of CubeGrabber
	 * @param id ID of CubeGrabber
	 */
	private CubeGrabber(String name, int id) {
		super(name, id);
		//TODO Uncomment
		opener = new DoubleSolenoid(Constants.PCM_ID, Constants.GRABBER_SOLENOID_1, Constants.GRABBER_SOLENOID_2);
		leftRotate = new VictorSP(Constants.GRABBER_LEFT_ROTATE);
		rightRotate = new VictorSP(Constants.GRABBER_RIGHT_ROTATE);
		leftRotate.setInverted(true);
		rightRotate.setInverted(true);
		rotateEncoder = new Encoder(Constants.ARM_ENCODER_1, Constants.ARM_ENCODER_2);
		
		opener.set(Value.kReverse);
	}

	@Override
	public void run() {
		//TODO Make it move
		//opens claw, I assume
		if(Joysticks.getInstance().getDriveStick().getRawButton(Constants.GRABBER_BUTTON) && released){
			//toggleClawOpen();
			System.out.println("Fire Claw");
			toggleClawOpen();
			released = false;
		}
		
		//close claw, I assume
		if(!Joysticks.getInstance().getDriveStick().getRawButton(Constants.GRABBER_BUTTON)) {
			released = true;
		}
		
		//check that claw is in limits
		checkLimits(Constants.UPPER_LIMIT, Constants.LOWER_LIMIT);
		
		//rotate claw
//		if(Joysticks.getInstance().getOperatorStick().getRawButton(8)) {
//			leftRotate.set(1);
//		}else if(Joysticks.getInstance().getOperatorStick().getRawButton(9)){
//			leftRotate.set(-1);
//		}else{
//			leftRotate.set(0);
//		}
		
		leftRotate.set(Joysticks.getInstance().getOperatorStick().getY());
		rightRotate.set(-1 * Joysticks.getInstance().getOperatorStick().getY());
	}
	
	/**
	 * compares encoder distance to limits passed in and adjusts motor limits
	 * 
	 * @param upperLimit The upper limit of CubeGrabber rotation
	 * @param lowerLimit The lower limit of CubeGrabber rotation
	 */
	private void checkLimits(double upperLimit, double lowerLimit) {
		//check upper limit
		if(rotateEncoder.getDistance() > upperLimit) {
			upper_motor_limit = 0;
		//check lower limit
		}else if(rotateEncoder.getDistance() < lowerLimit) {
			lower_motor_limit = 0;
		}else {
			upper_motor_limit = 1;
			lower_motor_limit = -1;
		}
		
//		if(Joysticks.getInstance().getOperatorStick().getY() > lower_motor_limit &&
//				Joysticks.getInstance().getOperatorStick().getY() < upper_motor_limit) {
//			leftRotate.set(Joysticks.getInstance().getOperatorStick().getY());
//		}else {
//			leftRotate.set(0);
//		}
		
	}
	
	//opens claw if closed, closes claw if open
	private void toggleClawOpen(){
		if(opener.get() == Value.kReverse){
			opener.set(Value.kForward);
			clawOpen = true;
		}else if(opener.get() == Value.kForward){
			clawOpen = false;
			opener.set(Value.kReverse);
		}else {
			opener.set(Value.kOff);
		}
	}

	@Override
	public void outputToDashboard() {
		SmartDashboard.putNumber("Arm Position", rotateEncoder.getDistance());
		SmartDashboard.putBoolean("Claw Open", clawOpen);
	}

	@Override
	public void zeroAllSensors() {
		rotateEncoder.reset();
	}
	
	@Override
	/**
	 * drops claw from upper position during autonomous
	 * 
	 * @param distance The desired amount of rotation
	 * @param speed The desired amount of speed of the rotation
	 */
	public void runAuto(double distance, double speed, boolean useSensor) {
		//checkLimits(Constants.UPPER_LIMIT, Constants.LOWER_LIMIT);
		for(int i = 0; i < distance; i++) {
			if(DriverStation.getInstance().isAutonomous()) {
				leftRotate.set(speed * -1);
				rightRotate.set(speed);
				System.out.println("Running Motors for Claw");
			}else {
				System.out.println("Breaking");
				break;
			}
			
			try {
				Thread.sleep(70);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		leftRotate.set(0);
		rightRotate.set(0);
	}
	
	/*
	 * Calls toggleClawOpen() during autonomous
	 */
	public void dropAuto() {
		toggleClawOpen();
	}
		

	@Override
	public boolean autoCommandDone() {
		return false;
	}

}
