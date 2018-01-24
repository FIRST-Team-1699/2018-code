package org.usfirst.frc.team1699.robot.commands;

import org.usfirst.frc.team1699.robot.Constants;
import org.usfirst.frc.team1699.robot.Joysticks;
import org.usfirst.frc.team1699.utils.autonomous.AutoCommand;
import org.usfirst.frc.team1699.utils.command.Command;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;

public class Elevator extends Command implements AutoCommand{
	
	private static Elevator instance;
	
	public static Elevator getInstance() {
		if(instance == null) {
			instance = new Elevator("Elevator", Constants.ELEVATOR_ID);
		}
		return instance;
	}

	//Lift sensors
	private final DigitalInput lowerLimit;
	private final DigitalInput upperLimit;
	private final Encoder liftEncoderChain;
	private final Encoder liftEncoderCable;
	
	//Lift motors
	//TODO FIX NAMES
	private final VictorSP elevator1;
	private final VictorSP elevator2;
	private final VictorSP elevator3;
	
	private Elevator(String name, int id) {
		super(name, id);
		lowerLimit = new DigitalInput(Constants.LOWER_LIMIT);
		upperLimit = new DigitalInput(Constants.UPPER_LIMIT);
		elevator1 = new VictorSP(Constants.ELEVATOR1);
		elevator2 = new VictorSP(Constants.ELEVATOR2);
		elevator3 = new VictorSP(Constants.ELEVATOR3);
		liftEncoderChain = new Encoder(Constants.LIFT_ENCODER_ID_1, Constants.LIFT_ENCODER_ID_2);
		liftEncoderCable = new Encoder(Constants.LIFT_ENCODER_ID_3, Constants.LIFT_ENCODER_ID_4);
	}

	@Override
	public void run() {
		//TODO create button to move lift to predetermined height
		if(Joysticks.getInstance().getOperatorStick().getRawButton(Constants.LIFT_BUTTON)) {
			//Move lift up
		}else if(Joysticks.getInstance().getOperatorStick().getRawButton(Constants.LOWER_BUTTON)) {
			//Lower lift
		}
	}

	@Override
	public void outputToDashboard() {
		
	}

	@Override
	public void zeroAllSensors() {
		liftEncoderChain.reset();
		liftEncoderCable.reset();
	}

	@Override
	public void runAuto(double distance, double speed, boolean useSensor) {
		
	}

	@Override
	public boolean autoCommandDone() {
		return false;
	}

}
