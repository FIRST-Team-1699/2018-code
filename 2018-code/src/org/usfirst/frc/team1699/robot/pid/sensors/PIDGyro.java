package org.usfirst.frc.team1699.robot.pid.sensors;

import edu.wpi.first.wpilibj.interfaces.Gyro;

public class PIDGyro implements PIDSensor{
	private Gyro gyro;
	
	public PIDGyro(Gyro gyro){
		this.gyro = gyro;
	}
	
	@Override
	public double get() {
		return gyro.getAngle();
	}

}
