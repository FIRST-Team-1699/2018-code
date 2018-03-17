package org.usfirst.frc.team1699.robot.commands;

import org.usfirst.frc.team1699.robot.Constants;
import org.usfirst.frc.team1699.robot.Joysticks;
import org.usfirst.frc.team1699.utils.autonomous.AutoCommand;
import org.usfirst.frc.team1699.utils.command.Command;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator extends Command implements AutoCommand{
	
	private static Elevator instance;
	
	public static Elevator getInstance() {
		if(instance == null) {
			instance = new Elevator("Elevator", Constants.ELEVATOR_ID);
		}
		return instance;
	}

	//Lift sensors
	private final Encoder liftEncoder;
	
	private boolean ratchetEngaged = false;
	
	//Lift motors
	//TODO FIX NAMES
	private final VictorSP elevator1;
	private final VictorSP elevator2;
	
	//Anti-Reverse Solenoid
	private final DoubleSolenoid antiReverse;
	
	//Joystick Utils
	private boolean released = true;
	
	private Elevator(String name, int id) {
		super(name, id);
		elevator1 = new VictorSP(Constants.ELEVATOR1);
		elevator2 = new VictorSP(Constants.ELEVATOR2);
		liftEncoder = new Encoder(Constants.LIFT_ENCODER_ID_3, Constants.LIFT_ENCODER_ID_4);
		antiReverse = new DoubleSolenoid(Constants.PCM_ID, Constants.ANTI_REVERSE_SOLENOID_1, Constants.ANTI_REVERSE_SOLENOID_2);
		
		antiReverse.set(Value.kForward);
	}

	@Override
	public void run() {
		//TODO create button to move lift to predetermined height
//		if(Joysticks.getInstance().getOperatorStick().getRawButton(Constants.LIFT_BUTTON)) {
//			checkLimits(Constants.TOP_ELEVATOR_LIMIT, Constants.BOT_ELEVATOR_LIMIT);
//		}else {
//			setElevator(0);
//		}
		
		if(Joysticks.getInstance().getOperatorStick().getRawButton(Constants.ENGAGE_ANTIREVERSE_BUTTON) && released){
			//Engage Anti-Reverse
			System.out.println("Fire Anti-Reverse");
			engageAntiReverse();
			released = false;
		}
		
		if(!Joysticks.getInstance().getOperatorStick().getRawButton(Constants.ENGAGE_ANTIREVERSE_BUTTON)) {
			released = true;
		}
		
		if(Joysticks.getInstance().getOperatorStick().getRawButton(1)) {
			elevator1.set(Joysticks.getInstance().getOperatorStick().getThrottle());
			elevator2.set(Joysticks.getInstance().getOperatorStick().getThrottle());
		}else{
			elevator1.set(0);
			elevator2.set(0);
		}
	}
	
	
	/**
	 * Determines if the elevator is within set software limits
	 * 
	 * @param encValue The encoder value that reads the position
	 * @param upperLimit The upper limit of the elevator
	 * @param lowerLimit The lower limit of the elevator
	 * @return True if within limits, false if otherwise
	 */
	private boolean withinLimits(double encValue, double upperLimit, double lowerLimit){
		if(encValue < upperLimit && encValue > lowerLimit) {
			return true;
		}else {
			return false;
		}
	}
	
	//compare value of encValue to TOP_ELEVATOR_LIMIT
	/**
	 * Compares value from encoder to the upper elevator limit
	 * 
	 * @param encValue The encoder value
	 * @return True if under upper limit, false if equal to or above
	 */
	private boolean checkUpperLimit(double encValue) {
		if(encValue > Constants.TOP_ELEVATOR_LIMIT) {
			return true;
		}else {
			return false;
		}
	}
	
	//compare value of encValue to BOT_ELEVATOR_LIMIT
	/**
	 * Compares encoder value to the lower elevator limit
	 * 
	 * @param encValue Encoder value
	 * @return True if above lower limit, false if equal to or below
	 */
	private boolean checkLowerLimit(double encValue) {
		if(encValue < Constants.BOT_ELEVATOR_LIMIT) {
			return true;
		}else {
			return false;
		}
	}
	
	//set speed of both elevator motors to double speed
	/**
	 * Sets speed of elevator
	 * 
	 * @param speed Value between -1 and 1, controls speed of elevator
	 */
	private void setElevator(double speed) {
		elevator1.set(speed);
		elevator2.set(speed);
	}
	
	//ensure limits aren't broken
	/**
	 * Checks to make sure limits aren't broken
	 * 
	 * @param upperLimit The upper limit value
	 * @param lowerLimit The lower limit value
	 */
	private void checkLimits(double upperLimit, double lowerLimit) {
		//Move elevator
		if(withinLimits(liftEncoder.getDistance(), upperLimit, lowerLimit)){
			//Allow movement
			setElevator(Joysticks.getInstance().getOperatorStick().getThrottle());
			
		}else{
			//allow movement down if upper limit broken
			if(checkUpperLimit(liftEncoder.getDistance())) {
				if(Joysticks.getInstance().getOperatorStick().getThrottle() < 0) {
					setElevator(Joysticks.getInstance().getOperatorStick().getThrottle());
				}else {
					setElevator(0);
				}
			}
			//allow movement up if lower limit broken
			if(checkLowerLimit(liftEncoder.getDistance())) {
				if(Joysticks.getInstance().getOperatorStick().getThrottle() > 0) {
					setElevator(Joysticks.getInstance().getOperatorStick().getThrottle());
				}else {
					setElevator(0);
				}
			}
		}
	}

	/*
	 * Engages the anti-falling solenoid that prevents the robot from falling down in a catastrophic manner.
	 */
	private void engageAntiReverse() {
		if(antiReverse.get() == Value.kReverse){
			antiReverse.set(Value.kForward);
			ratchetEngaged = true;
		}else if(antiReverse.get() == Value.kForward){
			antiReverse.set(Value.kReverse);
			ratchetEngaged = false;
		}else{
			antiReverse.set(Value.kOff);
		}
	}

	@Override
	public void outputToDashboard() {
		SmartDashboard.putNumber("Elevator Position", liftEncoder.getDistance());
		SmartDashboard.putBoolean("Ratchet Engaged", ratchetEngaged);
	}

	@Override
	public void zeroAllSensors() {
		liftEncoder.reset();
	}

	@Override
	//raises elevator for auto
	public void runAuto(double distance, double speed, boolean useSensor) {
//		if(withinLimits(liftEncoder.getDistance(), Constants.AUTO_SWITCH_UPPER_LIMIT, Constants.AUTO_SWITCH_LOWER_LIMIT)) {
//			setElevator(speed);
//		}else {
//			setElevator(0);
//		}
		for(int i = 0; i < distance; i++) {
			if(DriverStation.getInstance().isAutonomous()) {
				setElevator(speed * -1);
			}else {
				System.out.println("Breaking");
				break;
			}
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		setElevator(0);
	}

	@Override
	public boolean autoCommandDone() {
		return false;
	}

}
