package org.usfirst.frc.team1699.robot;

public class Constants {

	//Drive motor constants
	public static final int PORT_MASTER_PORT = 10;
	public static final int PORT_SLAVE_PORT = 11;
	public static final int STARBOARD_MASTER_PORT = 12;
	public static final int STARBOARD_SLAVE_PORT = 13;
	
	//Elevator Motor Constants
	public static final int ELEVATOR1 = 8;
	public static final int ELEVATOR2 = 7;
	
	//Sensor constants
	public static final int GRYO_PORT = 0;
	public static final int PORT_ENCODER_A = 0;
	public static final int PORT_ENCODER_B = 1;
	public static final int STARBOARD_ENCODER_A = 2;
	public static final int STARBOARD_ENCODER_B = 3;
	public static final int LOWER_LIMIT = 0;
	public static final int UPPER_LIMIT = 0;
	
	//Joystick constants
	public static final int DRIVE_STICK_PORT = 1;
	public static final int OPERATOR_STICK_PORT = 0;
	
	//Theoretical limits
	public static final double MAX_SURFACE_SPEED = 7437.2; //Inches per rotation
	public static final double MAX_SHAFT_SPEED = 1239.5;
	
	//Hardware Dimensions
	public static final double WHEEL_DIAMETER = 6.0;
	
	//Command ID constants
	public static final int ELEVATOR_ID = 78;
	public static final int CUBE_GRABBER_ID = 79;
	
	//Joystick button constants
	public static final int LIFT_BUTTON = 1;
	public static final int ENGAGE_ANTIREVERSE_BUTTON = 7;
	
	//Elevator encoder ports
	public static final int LIFT_ENCODER_ID_1 = 19;
	public static final int LIFT_ENCODER_ID_2 = 20;
	public static final int LIFT_ENCODER_ID_3 = 21;
	public static final int LIFT_ENCODER_ID_4 = 22;
	
	//Grabber motors
	public static final int GRABBER_LEFT_ROTATE = 9;
	//public static final int GRABBER_RIGHT_ROTATE = 7;
	
	//Solenoids
	public static final int ANTI_REVERSE_SOLENOID_1 = 7;
	public static final int ANTI_REVERSE_SOLENOID_2 = 6;
	
	public static final int GRABBER_SOLENOID_1 = 2;
	public static final int GRABBER_SOLENOID_2 = 3;
	
	//Arm encoder constants
	public static final int ARM_ENCODER_1 = 29;
	public static final int ARM_ENCODER_2 = 30;
	
	//Grabber Button
	public static final int GRABBER_BUTTON = 2;
	public static final int ROTATE_UP_BUTTON = 0;
	public static final int ROTATE_DOWN_BUTTON = 0;
	
	//Grabber Encoder Limits
	public static final double TOP_LIMIT = 0.0;
	public static final double BOT_LIMIT = 0.0;
	
	//Elevator Encoder Limit
	public static final double TOP_ELEVATOR_LIMIT = 0.0;
	public static final double BOT_ELEVATOR_LIMIT = 0.0;
	
	//Field Dimensions
	public static final int DISTANCE_TO_BASELINE = 0;
	
	//PCM Constants
	public static final int PCM_ID = 1;
}
