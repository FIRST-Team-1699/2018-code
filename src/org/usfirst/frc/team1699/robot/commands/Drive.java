package org.usfirst.frc.team1699.robot.commands;

import org.usfirst.frc.team1699.robot.Constants;
import org.usfirst.frc.team1699.utils.command.Command;
import org.usfirst.frc.team1699.utils.sensors.BetterEncoder;
import org.usfirst.frc.team1699.utils.sensors.BetterGryo;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class Drive extends Command{
	
	private static Drive instance = new Drive();
	
	public static Drive getInstance(){
		return instance;
	}
	
	public enum DriveState{
		OPEN_LOOP, //Drives with no sensor feedback
		STRAIGHT_LINE, //Drives at a set angle
		CLOSED_LOOP, //Closed loop driving scheme
		SET_VELOCITY, //Drives at a set velocity
		AUTONOMOUS, //Mode used during autonomous
		GOAL_TRACKING //Used to track a goal with sensors
	}
	
	//Hardware control
	private final CANTalon portMaster, portSlave, starboardMaster, starboardSlave;
	private final Solenoid shifter;
	private final Gyro driveGyro;
	private final Encoder portEncoder, starboardEncoder;
	
	//Hardware state
	private boolean isHighGear;
	private boolean isLowGear;
	private DriveState driveState;
	
	private Drive(){
		super("Drive", 0);
		
		//Initialize hardware
		portMaster = new CANTalon(Constants.PORT_MASTER_PORT);
		portSlave = new CANTalon(Constants.PORT_SLAVE_PORT);
		starboardMaster = new CANTalon(Constants.STARBOARD_MASTER_PORT);
		starboardSlave = new CANTalon(Constants.STARBOARD_SLAVE_PORT);
		shifter = new Solenoid(Constants.SHIFT_SOLENOID);
		driveGyro = new BetterGryo(Constants.GRYO_PORT);
		portEncoder = new BetterEncoder(Constants.PORT_ENCODER_A, Constants.PORT_ENCODER_B);
		starboardEncoder = new BetterEncoder(Constants.STARBOARD_ENCODER_A, Constants.STARBOARD_ENCODER_B);
		
		//Sets drive state
		isHighGear = false;
		isLowGear = false;
		driveState = DriveState.OPEN_LOOP;
		setHighGear();
	}

	private void setHighGear() {
		if(!isHighGear){
			//TODO fill in
		}
	}
	
	private void setLowGear() {
		if(!isLowGear){
			//TODO fill in
		}
	}

	@Override
	public void init() {
		
	}

	@Override
	public void run() {
		
	}

	@Override
	public void outputToDashboard() {
		
	}

	@Override
	public void zeroAllSensors() {
		driveGyro.calibrate();
		portEncoder.reset();
		starboardEncoder.reset();
	}
}
