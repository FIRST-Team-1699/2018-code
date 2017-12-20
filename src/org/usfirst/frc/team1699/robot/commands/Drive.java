package org.usfirst.frc.team1699.robot.commands;

import org.usfirst.frc.team1699.robot.Constants;
import org.usfirst.frc.team1699.robot.Joysticks;
import org.usfirst.frc.team1699.robot.pid.PIDLoop;
import org.usfirst.frc.team1699.utils.command.Command;
import org.usfirst.frc.team1699.utils.sensors.BetterEncoder;
import org.usfirst.frc.team1699.utils.sensors.BetterGryo;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive extends Command{
	
	//One and only instance of "this"
	private static Drive instance = new Drive();
	
	//Shifter Constants
	private static final boolean HIGH_GEAR = true;
	private static final boolean LOW_GEAR = false;
	
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
		GOAL_TRACKING //Used to track a goal with sensors
	}
	
	//Hardware control
	private final CANTalon portMaster, portSlave, starboardMaster, starboardSlave;
	private final Solenoid shifter; //May need to change to double solenoid. Only one because hardware can be used to split output.
	private final Gyro driveGyro;
	private final Encoder portEncoder, starboardEncoder;
	
	//Hardware state
	private boolean isHighGear;
	private boolean isLowGear;
	private DriveState driveState;
	
	//Drive train (might change)
	private final RobotDrive driveTrain;
	
	//Software controllers
	private final PIDLoop rotatePID;
	private final PIDLoop velocityPID;
	private final PIDLoop visionPID;

	//Vision values
	private double visionXError;
	
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
		
		//Software controllers
		rotatePID = new PIDLoop("rotatePID", 1, 0.0, 0.0, 0.0, null);
		velocityPID = new PIDLoop("velocityPID", 2, 0.0, 0.0, 0.0, null);
		visionPID = new PIDLoop("visionPID", 3, 0.0, 0.0, 0.0, null);
		
		//TBD may change if we end up with our own method of control
		driveTrain = new RobotDrive(portMaster, portSlave, starboardMaster, starboardSlave);
		
		//Vision
		this.visionXError = 0;
		
		//Sets drive state
		isHighGear = false;
		isLowGear = false;
		driveState = DriveState.OPEN_LOOP;
		setHighGear();
	}

	//Puts robot in high gear
	private void setHighGear() {
		if(!isHighGear){
			isHighGear = true;
			isLowGear = false;
			shifter.set(HIGH_GEAR);
		}
	}
	
	//Puts robot in low gear
	private void setLowGear() {
		if(!isLowGear){
			isLowGear = true;
			isHighGear = false;
			shifter.set(LOW_GEAR);
		}
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
			case GOAL_TRACKING: goalTracking();
				break;
			default: openLoop();
				break;
		}
		
		//Gearing control
		//Might want to change gear to be reliant on desired speed EX. When driver inputs a slow speed, automatically switch to low gear
		if(Joysticks.getInstance().getDriveStick().getRawButton(1)){
			if(isHighGear){
				setLowGear();
			}else if(isLowGear){
				setHighGear();
			}
		}
	}
	
	private void openLoop() {
		//Standard open loop driving
		//TODO make sure other states do not interfere
		driveTrain.arcadeDrive(Joysticks.getInstance().getDriveStick());
	}
	
	private void straightLine() {
		//Used to make the robot track in a straight line
		//TODO Test
		rotatePID.setGoal(0);
		driveTrain.arcadeDrive(Joysticks.getInstance().getDriveStick().getAxis(AxisType.kThrottle), rotatePID.output());
	}
	
	private void closedLoop() {
		//Closed loop - need to determine what this should entail
		//Speed is proportional to joystick up/down
		//Rotation is proportional to joystick left/right
		//Look at up/down value, if within deadband, drive straight, else, turn at a rate proportional to left/right
		//Need verification
		
		if(withinJoystickDeadBand(Joysticks.getInstance().getDriveStick().getAxis(AxisType.kX))){ //Need to make sure using right axis
			//Drive straight
		}else{
			
		}
	}
	
	private void setVelocity() {
		//Drives at a predetermined velocity
		//May transform into closed loop7
	}
	
	private void autonomous() {
		//Used for autonomous
		//TODO figure out how integrate this with scripting language, or how to change scripting language
	}
	
	private void goalTracking() {
		//Used to track a vision target
		//TODO determine what type of vision system we are using and feed values into a function to determine angle
		//*continued* then feed into gyro and turn based on that (Will likely change)
		
		//Sets vision X error
		this.visionXError = Vision.getInstance().xError(); //TODO Move to pid sensor
		
		//Turns robot to goal
		driveTrain.arcadeDrive(Joysticks.getInstance().getDriveStick().getAxis(AxisType.kThrottle), visionPID.output());
	}
	
	//Sets correct drive state
	//TODO make sure this is thread save if we end up going to that direction
	public void setState(DriveState state){
		this.driveState = state;
	}
	
	//Gets correct drive state
	//TODO make sure this is thread save if we end up going to that direction
	public DriveState getState(){
		return this.driveState;
	}
	
	//Returns shaft rpm
	private double getShaftRPM(){
		portEncoder.getRate(); //Use this maybe
		return 0.0;
	}
	
	//Uses encoders to calculate speed over the floor
	private double getSurfaceSpeed(){
		return 0.0;
	}
	
	//Returns the current percentage of the max surface speed
	private double percentMaxSpeed() throws Exception{
		if(isHighGear){
			//Calculates for high gear
		}else if(isLowGear){
			//Calculates for low gear
		}else{
			throw new Exception(); //TODO create custom exception
		}
		return 0;
	}

	//Returns true if the joystick value is within the dead band
	private boolean withinJoystickDeadBand(double inp){
		return inp < JOYSTICK_DEADBAND && inp > (JOYSTICK_DEADBAND * -1);
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
}
