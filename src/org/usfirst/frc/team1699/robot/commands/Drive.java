package org.usfirst.frc.team1699.robot.commands;

import org.usfirst.frc.team1699.robot.Constants;
import org.usfirst.frc.team1699.robot.Joysticks;
import org.usfirst.frc.team1699.robot.pid.PIDLoop;
import org.usfirst.frc.team1699.utils.autonomous.AutoCommand;
import org.usfirst.frc.team1699.utils.command.Command;
import org.usfirst.frc.team1699.utils.sensors.BetterEncoder;
import org.usfirst.frc.team1699.utils.sensors.BetterGryo;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

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
		AUTONOMOUS, //Mode used during autonomous
	}
	
	//Hardware control
	private final WPI_TalonSRX portMaster, portSlave, starboardMaster, starboardSlave;
	private final Gyro driveGyro;
	private final Encoder portEncoder, starboardEncoder;
	
	//Motor Group
	SpeedControllerGroup lGroup;
	SpeedControllerGroup rGroup;
	
	//Hardware state
	private boolean isHighGear;
	private boolean isLowGear;
	private DriveState driveState;
	
	//Drive train (might change)
	private final DifferentialDrive driveTrain;
	
	//Software controllers
	private final PIDLoop rotatePID;
	private final PIDLoop portVelocityPID;
	private final PIDLoop starboardVelocityPID;

	//Setpoints
	private double velocitySetpoint;
	
	private Drive(){
		super("Drive", 0);
		
		//Initialize hardware
		portMaster = new WPI_TalonSRX(Constants.PORT_MASTER_PORT);
		portSlave = new WPI_TalonSRX(Constants.PORT_SLAVE_PORT);
		starboardMaster = new WPI_TalonSRX(Constants.STARBOARD_MASTER_PORT);
		starboardSlave = new WPI_TalonSRX(Constants.STARBOARD_SLAVE_PORT);
		driveGyro = new BetterGryo(Constants.GRYO_PORT);
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
		rotatePID = new PIDLoop("rotatePID", 1, 0.0, 0.0, 0.0, null);
		portVelocityPID = new PIDLoop("portVelocityPID", 4, 0.0, 0.0, 0.0, null);
		starboardVelocityPID = new PIDLoop("starboardVelocityPID", 5, 0.0, 0.0, 0.0, null);
		
		//TBD may change if we end up with our own method of control
		driveTrain = new DifferentialDrive(lGroup, rGroup);
		
		//Sets drive state
		isHighGear = false;
		isLowGear = false;
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
			case AUTONOMOUS: autonomous();
				break;
			default: openLoop(); //Might change to invalid state exception
				break;
		}
	}
	
	private void openLoop() {
		//Standard open loop driving
		//TODO make sure other states do not interfere
		//TODO test is correct axis for joystick
		driveTrain.arcadeDrive(Joysticks.getInstance().getDriveStick().getY(), Joysticks.getInstance().getDriveStick().getX());
	}
	
	private void straightLine() {
		//Used to make the robot track in a straight line
		//TODO Test
		rotatePID.setGoal(0);
		driveTrain.arcadeDrive(Joysticks.getInstance().getDriveStick().getThrottle(), rotatePID.output());
	}
	
	private void closedLoop(){
		//Closed loop - need to determine what this should entail
		//Speed is proportional to joystick up/down
		//Rotation is proportional to joystick left/right
		//Look at up/down value, if within deadband, drive straight, else, turn at a rate proportional to left/right
		//Need verification
		
		if(withinJoystickDeadBand(Joysticks.getInstance().getDriveStick().getX())){ //Need to make sure using right axis
			//Drive straight
			//Needs to add speed control
			driveTrain.arcadeDrive(Joysticks.getInstance().getDriveStick().getThrottle(), rotatePID.output());
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
			portValue = portVelocityPID.output();
		}
		
		//Starboard Side Set
		if(getStarboardSurfaceSpeed() > velocitySetpoint) {
			starboardValue = velocitySetpoint - 0.05;
		}else {
			starboardValue = starboardVelocityPID.output();
		}
		
		driveTrain.tankDrive(portValue, starboardValue);
	}
	
	private void autonomous() {
		//Used for autonomous
		//TODO figure out how integrate this with scripting language, or how to change scripting language
	}
	
	//Sets correct drive state
	//TODO make sure this is thread save if we end up going to that direction
	public void setState(DriveState state){
		this.driveState = state;
	}
	
	//Gets correct drive state
	//TODO make sure this is thread safe if we end up going to that direction
	public DriveState getState(){
		return this.driveState;
	}
	
	//Returns port shaft rpm
	private double getPortShaftRPM(){
		return portEncoder.getRate();
	}
	
	//Returns starboard shaft rpm
	private double getStarboardShaftRPM(){
		return starboardEncoder.getRate();
	}
	
	//Uses port encoder to calculate speed over the floor
	private double getPortSurfaceSpeed(){
		return getPortShaftRPM() * Constants.WHEEL_DIAMETER; //Needs verification
	}
	
	//Uses starboard encoder to calculate speed over the floor
	private double getStarboardSurfaceSpeed(){
		return getStarboardShaftRPM() * Constants.WHEEL_DIAMETER; //Needs verification
	}
	
	//Returns the current percentage of the max surface speed for the port side
	private double portPercentMaxSpeed(){
		return (getPortSurfaceSpeed() / Constants.MAX_SURFACE_SPEED);
	}
	
	//Returns the current percentage of the max surface speed for the starboard side
	private double starboardPercentMaxSpeed() throws Exception{
		return (getStarboardSurfaceSpeed() / Constants.MAX_SURFACE_SPEED);
	}

	//Returns true if the joystick value is within the dead band
	private boolean withinJoystickDeadBand(double inp){
		return inp < JOYSTICK_DEADBAND && inp > (JOYSTICK_DEADBAND * -1);
	}
	
	private void setVelocitySetpoint(double setpoint){
		this.velocitySetpoint = setpoint;
		portVelocityPID.setGoal(setpoint);
		starboardVelocityPID.setGoal(setpoint);
	}
	
	private double getVelocitySetpoint(){
		return this.velocitySetpoint;
	}
	
	@Override
	public void outputToDashboard() {
		//Puts gear state on dashboard
		SmartDashboard.putBoolean("High Gear:", isHighGear);
		SmartDashboard.putBoolean("Low Gear:", isLowGear);
	}

	@Override
	public void zeroAllSensors() {
		driveGyro.calibrate();
		portEncoder.reset();
		starboardEncoder.reset();
	}

	@Override
	public void runAuto(double distance, double speed, boolean useSensor) {
		
	}

	@Override
	public boolean autoCommandDone() {
		return false;
	}
}
