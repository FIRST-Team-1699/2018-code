package org.usfirst.frc.team1699.utils.sensors;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class BetterGryo implements Gyro {

	private Gyro gyro;
	
	public BetterGryo() {
		gyro = new ADXRS450_Gyro();
		gyro.calibrate();
	}

	@Override
	public void calibrate() {
		gyro.calibrate();
	}

	@Override
	public void reset() {
		gyro.reset();		
	}

	@Override
	public double getAngle() {
		return gyro.getAngle();
	}

	@Override
	public double getRate() {
		return gyro.getRate();
	}

	@Override
	public void free() {
		gyro.free();		
	}

}
