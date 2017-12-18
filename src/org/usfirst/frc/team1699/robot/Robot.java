
package org.usfirst.frc.team1699.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.TalonSRX;

public class Robot extends IterativeRobot {
	
	//Drive motor controllers
	private static TalonSRX portMaster;
	private static TalonSRX portSlave;
	private static TalonSRX starboardMaster;
	private static TalonSRX starboardSlave;
	
	//Drive train
	private static RobotDrive drive;
	
	@Override
	public void robotInit() {
		Robot.portMaster = new TalonSRX(Constants.PORT_MASTER_PORT);
		Robot.portSlave = new TalonSRX(Constants.PORT_SLAVE_PORT);
		Robot.starboardMaster = new TalonSRX(Constants.STARBOARD_MASTER_PORT);
		Robot.starboardSlave = new TalonSRX(Constants.STARBOARD_SLAVE_PORT);
		Robot.drive = new RobotDrive(portMaster, portSlave, starboardMaster, starboardSlave);
	}

	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		
	}

	
	@Override
	public void autonomousInit() {
		
	}
	
	@Override
	public void autonomousPeriodic() {
		
	}

	@Override
	public void teleopInit() {
		
	}

	@Override
	public void teleopPeriodic() {
		
	}

	
	@Override
	public void testPeriodic() {
		
	}
}
