package org.usfirst.frc.team1699.robot.commands;

import org.usfirst.frc.team1699.robot.Constants;
import org.usfirst.frc.team1699.robot.Joysticks;
import org.usfirst.frc.team1699.utils.autonomous.AutoCommand;
import org.usfirst.frc.team1699.utils.command.Command;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
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
		
		antiReverse.set(Value.kReverse);
	}

	@Override
	public void run() {
		//TODO create button to move lift to predetermined height
		if(Joysticks.getInstance().getOperatorStick().getRawButton(Constants.LIFT_BUTTON)) {
			//Move elevator
			if(withinLimits(liftEncoder.getDistance())){
				//Allow movement
				elevator1.set(Joysticks.getInstance().getOperatorStick().getThrottle());
				elevator2.set(Joysticks.getInstance().getOperatorStick().getThrottle());
			}else{
				//Allow movement opposite of limit
			}
		}else {
			elevator1.set(0);
			elevator2.set(0);
		}
		
		if(Joysticks.getInstance().getOperatorStick().getRawButton(Constants.ENGAGE_ANTIREVERSE_BUTTON) && released){
			//Engage Anti-Reverse
			System.out.println("Fire Anti-Reverse");
			engageAntiReverse();
			released = false;
		}
		
		if(!Joysticks.getInstance().getOperatorStick().getRawButton(Constants.ENGAGE_ANTIREVERSE_BUTTON)) {
			released = true;
		}
	}
	
	private boolean withinLimits(double encValue){
		//TODO populate
		return true;
	}

	private void engageAntiReverse() {
		if(antiReverse.get() == Value.kReverse){
			antiReverse.set(Value.kForward);
		}else if(antiReverse.get() == Value.kForward){
			antiReverse.set(Value.kReverse);
		}else{
			antiReverse.set(Value.kOff);
		}
	}

	@Override
	public void outputToDashboard() {
		SmartDashboard.putNumber("Elevator Position", liftEncoder.getDistance());
	}

	@Override
	public void zeroAllSensors() {
		liftEncoder.reset();
	}

	@Override
	public void runAuto(double distance, double speed, boolean useSensor) {
		
	}

	@Override
	public boolean autoCommandDone() {
		return false;
	}

}
