
package org.usfirst.frc.team1699.robot;

import org.usfirst.frc.team1699.robot.commands.CubeGrabber;
import org.usfirst.frc.team1699.robot.commands.Drive;
import org.usfirst.frc.team1699.robot.commands.Elevator;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {
	
	private Compressor c;
	
	@Override
	public void robotInit() {
		//Compressor init
		c = new Compressor(0);
		c.setClosedLoopControl(true);
		
		//Init for commands
		Drive.getInstance();
		CubeGrabber.getInstance();
		Elevator.getInstance();
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
		Drive.getInstance().run();
		CubeGrabber.getInstance().run();
		Elevator.getInstance().run();
	}
	
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		
	}

	
	@Override
	public void testPeriodic() {
		
	}
}
