package org.usfirst.frc.team1699.robot.commands;

import org.usfirst.frc.team1699.robot.Constants;
import org.usfirst.frc.team1699.robot.Joysticks;
import org.usfirst.frc.team1699.utils.autonomous.AutoCommand;
import org.usfirst.frc.team1699.utils.command.Command;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	//private final VictorSP rightRotate;
	
	private final Encoder rotateEncoder;
	
	//Joystick Utils
	private boolean released = true;
	
	private CubeGrabber(String name, int id) {
		super(name, id);
		//TODO Uncomment
		opener = new DoubleSolenoid(Constants.PCM_ID, Constants.GRABBER_SOLENOID_1, Constants.GRABBER_SOLENOID_2);
		leftRotate = new VictorSP(Constants.GRABBER_LEFT_ROTATE);
		//rightRotate = new VictorSP(Constants.GRABBER_RIGHT_ROTATE);
		rotateEncoder = new Encoder(Constants.ARM_ENCODER_1, Constants.ARM_ENCODER_2);
		
		opener.set(Value.kReverse);
	}

	@Override
	public void run() {
		//TODO Make it move
		if(Joysticks.getInstance().getOperatorStick().getRawButton(Constants.GRABBER_BUTTON) && released){
			//toggleClawOpen();
			System.out.println("Fire Claw");
			toggleClawOpen();
			released = false;
		}
		
		if(!Joysticks.getInstance().getOperatorStick().getRawButton(Constants.GRABBER_BUTTON)) {
			released = true;
		}
		
		if(Joysticks.getInstance().getOperatorStick().getRawButton(Constants.ROTATE_UP_BUTTON)){
//			if(rotateEncoder.getDistance() > Constants.LOWER_LIMIT){
//				leftRotate.set(0.4);
//				//rightRotate.set(-0.4);
//			}
			leftRotate.set(0.6);
		}else if(Joysticks.getInstance().getOperatorStick().getRawButton(Constants.ROTATE_DOWN_BUTTON)){
//			if(rotateEncoder.getDistance() > Constants.UPPER_LIMIT){
//				leftRotate.set(-0.4);
//				//rightRotate.set(0.4);
//			}
			leftRotate.set(-0.6);
		}else{
			leftRotate.set(0);
		}
	}
	
	private void toggleClawOpen(){
		if(opener.get() == Value.kReverse){
			opener.set(Value.kForward);
		}else if(opener.get() == Value.kForward){
			opener.set(Value.kReverse);
		}else {
			opener.set(Value.kOff);
		}
	}

	@Override
	public void outputToDashboard() {
		SmartDashboard.putNumber("Arm Position", rotateEncoder.getDistance());
	}

	@Override
	public void zeroAllSensors() {
		rotateEncoder.reset();
	}
	
	@Override
	public void runAuto(double distance, double speed, boolean useSensor) {
		
	}

	@Override
	public boolean autoCommandDone() {
		return false;
	}

}
