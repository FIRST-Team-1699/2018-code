package org.usfirst.frc.team1699.robot.commands;

import org.usfirst.frc.team1699.robot.Constants;
import org.usfirst.frc.team1699.robot.Joysticks;
import org.usfirst.frc.team1699.robot.pid.SynchronousPIDF;
import org.usfirst.frc.team1699.utils.autonomous.AutoCommand;
import org.usfirst.frc.team1699.utils.command.Command;
import org.usfirst.frc.team1699.utils.sensors.BetterEncoder;
import org.usfirst.frc.team1699.utils.sensors.BetterGryo;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive extends Command implements AutoCommand{
	
	//One and only instance of "this"
	private static Drive instance = new Drive();
	
	//Dead bands
	private static final double JOYSTICK_DEADBAND = 0.05;
	
	//Returns an instance of the Drive class. Makes it so only one instance can ever exist.
	public static Drive getInstance(){
		return instance;
	}
	
	public enum DriveState{
		OPEN_LOOP, //Drives with no sensor feedback
		STRAIGHT_LINE, //Drives at a set angle
		CLOSED_LOOP, //Closed loop driving scheme
		SET_VELOCITY, //Drives at a set velocity
	}
	
	//Constants
	public static final double LOW_GEAR = 0.7;
	public static final double HIGH_GEAR = 1;
	
	//Hardware control
	private final WPI_TalonSRX portMaster, portSlave, starboardMaster, starboardSlave;
	private final Gyro driveGyro;
	private final Encoder portEncoder, starboardEncoder;
	
	//Motor Group
	SpeedControllerGroup lGroup;
	SpeedControllerGroup rGroup;
	
	//Hardware state
	private boolean isHighGear;
	//private boolean isLowGear;
	private DriveState driveState;
	
	//Is Joystick button released
	private boolean released = true;
	
	//Drive train (might change)
	private final DifferentialDrive driveTrain;
	
	//Software controllers
	private final SynchronousPIDF rotatePID, portVelocityPID, starboardVelocityPID;

	//Setpoints
	private double velocitySetpoint;
	
	private Drive(){
		super("Drive", 0);
		
		//Initialize hardware
		portMaster = new WPI_TalonSRX(Constants.PORT_MASTER_PORT);
		portSlave = new WPI_TalonSRX(Constants.PORT_SLAVE_PORT);
		starboardMaster = new WPI_TalonSRX(Constants.STARBOARD_MASTER_PORT);
		starboardSlave = new WPI_TalonSRX(Constants.STARBOARD_SLAVE_PORT);
		driveGyro = new BetterGryo();
		portEncoder = new BetterEncoder(Constants.PORT_ENCODER_A, Constants.PORT_ENCODER_B);
		starboardEncoder = new BetterEncoder(Constants.STARBOARD_ENCODER_A, Constants.STARBOARD_ENCODER_B);
		
		//Motor Groups
		lGroup = new SpeedControllerGroup(portMaster, portSlave);
		rGroup = new SpeedControllerGroup(starboardMaster, starboardSlave);
		
		//Encoder config
		portEncoder.setDistancePerPulse(0.1);
		starboardEncoder.setDistancePerPulse(0.1);
		
		//Software controllers
		//TODO Give real values
		rotatePID = new SynchronousPIDF(0.15, 0.1, 0.2);
		portVelocityPID = new SynchronousPIDF(0.4, 0.0, 0.0);
		starboardVelocityPID = new SynchronousPIDF(0.0, 0.0, 0.0);
		
		//TBD may change if we end up with our own method of control
		driveTrain = new DifferentialDrive(lGroup, rGroup);
		
		//Sets drive state
		isHighGear = true;
		driveState = DriveState.OPEN_LOOP;
	}

	@Override
	public void run() {
		//Runs drive train based on current control state
		switch(driveState){
			case OPEN_LOOP: openLoop();
				break;
			case STRAIGHT_LINE: straightLine();
				break;
			case CLOSED_LOOP: closedLoop();
				break;
			case SET_VELOCITY: setVelocity();
				break;
			default: openLoop(); //Might change to invalid state exception
				break;
		}
		
		System.out.println("Gyro: " + driveGyro.getAngle());
		System.out.println("Port: " + portEncoder.get());
		System.out.println("Starboard: " + starboardEncoder.get() / 13);
	}
	
	/*
	 * Open loop driving, drives without sensors
	 */
	private void openLoop() {
		//Standard open loop driving
		//TODO make sure other states do not interfere
		//TODO test is correct axis for joystick
		driveTrain.arcadeDrive(Joysticks.getInstance().getDriveStick().getY() * -1, Joysticks.getInstance().getDriveStick().getX());
	}
	
	/*
	 * Makes the robot drive in a straight line
	 */
	private void straightLine() {
		//Used to make the robot track in a straight line
		//TODO Test
		rotatePID.setSetpoint(0);
		driveTrain.arcadeDrive(Joysticks.getInstance().getDriveStick().getThrottle(), rotatePID.get());
	}
	
	/*
	 * Uses sensors to make robit drive smoother?
	 */
	private void closedLoop(){
		//Closed loop - need to determine what this should entail
		//Speed is proportional to joystick up/down
		//Rotation is proportional to joystick left/right
		//Look at up/down value, if within deadband, drive straight, else, turn at a rate proportional to left/right
		//Need verification
		
		if(withinJoystickDeadBand(Joysticks.getInstance().getDriveStick().getX())){ //Need to make sure using right axis
			//Drive straight
			//Needs to add speed control
			driveTrain.arcadeDrive(Joysticks.getInstance().getDriveStick().getThrottle(), rotatePID.get());
		}else{
			//Finds speed drive shaft should spin
			double xProp = 0;
			double yProp = 0;
			
			//Might not be correct, will likely change
			
			//Run motors for forward driving
			//These two /\ \/ might be combined
			//Run motors for turning
			//TODO check values, test
		}
	}
	
	/*
	 * Drives at a predetermined velocity.
	 */
	private void setVelocity() {
		//Drives at a predetermined velocity
		//May transform into closed loop
		
		//Tank drive outputs
		double portValue;
		double starboardValue;
		
		//Port Side Set
		if(getPortSurfaceSpeed() > velocitySetpoint) {
			portValue = velocitySetpoint - 0.05;
		}else {
			portValue = portVelocityPID.get();
		}
		
		//Starboard Side Set
		if(getStarboardSurfaceSpeed() > velocitySetpoint) {
			starboardValue = velocitySetpoint - 0.05;
		}else {
			starboardValue = starboardVelocityPID.get();
		}
		
		driveTrain.tankDrive(portValue, starboardValue);
	}
	
	//auto utils
	@Override
	public void runAuto(double distance, double speed, boolean useSensor) {
		rotatePID.setSetpoint(0);
		starboardVelocityPID.setSetpoint(distance);
		starboardVelocityPID.setOutputRange(-1, 1);
		System.out.println(starboardEncoder.get());
		if(useSensor) {
			if(speed > 0) {
				while(distance > starboardEncoder.get() / 13 && DriverStation.getInstance().isAutonomous()){
					//driveTrain.arcadeDrive(starboardVelocityPID.calculate(starboardEncoder.get() / 13, .01), 0);
					//driveTrain.arcadeDrive(starboardVelocityPID.calculate(starboardEncoder.get() / 13, .01), rotatePID.calculate(driveGyro.getAngle(), .01));
					driveTrain.arcadeDrive(speed, rotatePID.calculate(driveGyro.getAngle(), .01));
					//driveTrain.arcadeDrive(speed, 0);
				}
			}else{
				while(distance < starboardEncoder.get() / 13 && DriverStation.getInstance().isAutonomous()){
					//driveTrain.arcadeDrive(starboardVelocityPID.calculate(starboardEncoder.get() / 13, .01), 0);
					//driveTrain.arcadeDrive(starboardVelocityPID.calculate(starboardEncoder.get() / 13, .01), rotatePID.calculate(driveGyro.getAngle(), .01));
					driveTrain.arcadeDrive(speed, rotatePID.calculate(driveGyro.getAngle(), .01));
					//driveTrain.arcadeDrive(speed, 0);
				}
			}
		}else {
			if(speed > 0) {
				while(distance > starboardEncoder.get() / 13 && DriverStation.getInstance().isAutonomous()){
					//driveTrain.arcadeDrive(starboardVelocityPID.calculate(starboardEncoder.get() / 13, .01), 0);
					//driveTrain.arcadeDrive(starboardVelocityPID.calculate(starboardEncoder.get() / 13, .01), rotatePID.calculate(driveGyro.getAngle(), .01));
					driveTrain.arcadeDrive(speed, 0);
					//driveTrain.arcadeDrive(speed, 0);
				}
			}else{
				while(distance < starboardEncoder.get() / 13 && DriverStation.getInstance().isAutonomous()){
					//driveTrain.arcadeDrive(starboardVelocityPID.calculate(starboardEncoder.get() / 13, .01), 0);
					//driveTrain.arcadeDrive(starboardVelocityPID.calculate(starboardEncoder.get() / 13, .01), rotatePID.calculate(driveGyro.getAngle(), .01));
					driveTrain.arcadeDrive(speed, 0);
					//driveTrain.arcadeDrive(speed, 0);
				}
			}
		}
		driveTrain.arcadeDrive(0, 0);
	}
	
	public void runAuto(double distance, double speed, boolean useSensor, double timeOut) {
		rotatePID.setSetpoint(0);
		starboardVelocityPID.setSetpoint(distance);
		starboardVelocityPID.setOutputRange(-1, 1);
		System.out.println(starboardEncoder.get());
		if(useSensor) {
			if(speed > 0) {
				while(distance > starboardEncoder.get() / 13 && DriverStation.getInstance().isAutonomous() && DriverStation.getInstance().getMatchTime() > timeOut){
					//driveTrain.arcadeDrive(starboardVelocityPID.calculate(starboardEncoder.get() / 13, .01), 0);
					//driveTrain.arcadeDrive(starboardVelocityPID.calculate(starboardEncoder.get() / 13, .01), rotatePID.calculate(driveGyro.getAngle(), .01));
					driveTrain.arcadeDrive(speed, rotatePID.calculate(driveGyro.getAngle(), .01));
					//driveTrain.arcadeDrive(speed, 0);
				}
			}else{
				while(distance < starboardEncoder.get() / 13 && DriverStation.getInstance().isAutonomous() && DriverStation.getInstance().getMatchTime() > timeOut){
					//driveTrain.arcadeDrive(starboardVelocityPID.calculate(starboardEncoder.get() / 13, .01), 0);
					//driveTrain.arcadeDrive(starboardVelocityPID.calculate(starboardEncoder.get() / 13, .01), rotatePID.calculate(driveGyro.getAngle(), .01));
					driveTrain.arcadeDrive(speed, rotatePID.calculate(driveGyro.getAngle(), .01));
					//driveTrain.arcadeDrive(speed, 0);
				}
			}
		}else {
			if(speed > 0) {
				while(distance > starboardEncoder.get() / 13 && DriverStation.getInstance().isAutonomous() && DriverStation.getInstance().getMatchTime() > timeOut){
					//driveTrain.arcadeDrive(starboardVelocityPID.calculate(starboardEncoder.get() / 13, .01), 0);
					//driveTrain.arcadeDrive(starboardVelocityPID.calculate(starboardEncoder.get() / 13, .01), rotatePID.calculate(driveGyro.getAngle(), .01));
					driveTrain.arcadeDrive(speed, 0);
					//driveTrain.arcadeDrive(speed, 0);
				}
			}else{
				while(distance < starboardEncoder.get() / 13 && DriverStation.getInstance().isAutonomous() && DriverStation.getInstance().getMatchTime() > timeOut){
					//driveTrain.arcadeDrive(starboardVelocityPID.calculate(starboardEncoder.get() / 13, .01), 0);
					//driveTrain.arcadeDrive(starboardVelocityPID.calculate(starboardEncoder.get() / 13, .01), rotatePID.calculate(driveGyro.getAngle(), .01));
					driveTrain.arcadeDrive(speed, 0);
					//driveTrain.arcadeDrive(speed, 0);
				}
			}
		}
		driveTrain.arcadeDrive(0, 0);
	}	
	
	/**
	 * Pos turns right, neg turns left
	 * This is for autonomous
	 * 
	 * @param speed The speed at which the rotation should take place. Positive turns right, negative turns left
	 * @param setPoint The point at which the robot should stop rotation
	 */
	public void autoTurn(double speed, double setPoint) {
		int setpointToleranceCount = 0;
		double rotate_speed;
		double angle;
		rotatePID.setSetpoint(setPoint);
		driveGyro.reset();
		if(setPoint < 0.0) {
			while(//driveGyro.getAngle() > setPoint && 
					DriverStation.getInstance().isAutonomous() 
					&& setpointToleranceCount <= 50) {
				angle = driveGyro.getAngle();
				rotate_speed = rotatePID.calculate(angle, .1);
				driveTrain.arcadeDrive(speed, rotate_speed/1);
				if(checkTolerance(driveGyro.getAngle() , 1, setPoint)) setpointToleranceCount++;
				
				//System.out.println("count: " + setpointToleranceCount);
				//System.out.println("gyro angle: " + driveGyro.getAngle());
			}
		}else{
			while(//driveGyro.getAngle() < setPoint && 
					DriverStation.getInstance().isAutonomous()
					&& setpointToleranceCount <= 50) {
				rotate_speed = rotatePID.calculate(driveGyro.getAngle(), .1);
				angle = driveGyro.getAngle();
				driveTrain.arcadeDrive(speed, rotate_speed/1);
				if(checkTolerance(driveGyro.getAngle() , 1, setPoint)) setpointToleranceCount++;
				
				System.out.print("count: " + setpointToleranceCount + "\t\t");
				System.out.print("gyro angle: " + angle + "\t\t");
				System.out.println("Rotate Speed: " + rotate_speed);
			}
		}
	}
	
	private boolean checkTolerance(double gyro, double tolerance, double goal) {
		double low = goal - tolerance;
		double high = goal + tolerance;
		if(gyro <= high && gyro >= low) return true;
		else return false;
	}
	
	@Override
	public boolean autoCommandDone() {
		return false;
	}
	
	//end auto utils
	
	//Sets correct drive state
	//TODO make sure this is thread save if we end up going to that direction
	/**
	 * 
	 * @param state The desired state to be set to
	 */
	public void setState(DriveState state){
		this.driveState = state;
	}
	
	@Override
	public void outputToDashboard() {
		//Puts rotation on dashboard
		SmartDashboard.putNumber("Rotation", driveGyro.getAngle() % 360);
		SmartDashboard.putBoolean("High Gear", isHighGear);
	}

	@Override
	public void zeroAllSensors() {
		driveGyro.reset();
		portEncoder.reset();
		starboardEncoder.reset();
	}
	
	public void starboardEncoderZero() {
		starboardEncoder.reset();
	}
	
	//Gets correct drive state
	//TODO make sure this is thread safe if we end up going to that direction
	/**
	 * 
	 * @return Current drive state
	 */
	public DriveState getState(){
		return this.driveState;
	}
	
	/**
	 * 
	 * @return The RPM of the port shaft
	 */
	private double getPortShaftRPM(){
		return portEncoder.getRate();
	}
	
	//Returns starboard shaft rpm
	/**
	 * 
	 * @return The RPM of the starboard shaft
	 */
	private double getStarboardShaftRPM(){
		return starboardEncoder.getRate();
	}
	
	/**
	 * 
	 * @return The surface speed in ft/s based on the port side
	 */
	private double getPortSurfaceSpeed(){
		return getPortShaftRPM() * Constants.WHEEL_DIAMETER; //Needs verification
	}
	
	/**
	 * 
	 * @return The surface speed in ft/s based on starboard side
	 */
	private double getStarboardSurfaceSpeed(){
		return getStarboardShaftRPM() * Constants.WHEEL_DIAMETER; //Needs verification
	}
		

	/**
	 * 
	 * @return Current percentage of max surface speed for port side
	 */
	private double portPercentMaxSpeed(){
		return (getPortSurfaceSpeed() / Constants.MAX_SURFACE_SPEED);
	}
		
	//Returns the current percentage of the max surface speed for the starboard side
	/**
	 * 
	 * @return Current percentage of max surface speed for starboard side
	 */
	private double starboardPercentMaxSpeed(){
		return (getStarboardSurfaceSpeed() / Constants.MAX_SURFACE_SPEED);
	}

	//Returns true if the joystick value is within the dead band
	/**
	 * 
	 * @param inp Double value representing input from joystick
	 * @return True if joystick value is within deadband, false otherwise
	 */
	private boolean withinJoystickDeadBand(double inp){
		return inp < JOYSTICK_DEADBAND && inp > (JOYSTICK_DEADBAND * -1);
	}
	
	/**
	 * 
	 * @param setpoint Sets goal for velocity PID loops
	 */
	private void setVelocitySetpoint(double setpoint){
		this.velocitySetpoint = setpoint;
		portVelocityPID.setSetpoint(setpoint);
		starboardVelocityPID.setSetpoint(setpoint);
	}
	
	/**
	 * 
	 * @return The goal for velocity PID loops
	 */
	private double getVelocitySetpoint(){
		return this.velocitySetpoint;
	}
}
