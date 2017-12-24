package org.usfirst.frc.team1699.robot.commands;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Vision {
	
	//Making singleton
	//TODO Give real values
	private static final Vision instance = new Vision(null, null, 0, 0);
	
	//Returns instance
	public static Vision getInstance(){
		return instance;
	}
	
	//Network tables key
	private final String key;
	
	//Network tables instance
	private NetworkTable table;
	
	//Screen center
	private final int screenWidthCenter;
	
	//Center X
	private double centerX;
	
	private Vision(final String key, final String name, final int screenWidth, final int screenHeight){
		this.key = key;
		screenWidthCenter = screenWidth / 2;
		this.table = NetworkTable.getTable(name);
		centerX = table.getNumberArray(key, new double[0])[0];
	}
	
	//Determines the angle the robot needs to turn to be at centerX
	public double xError(){
		centerX = table.getNumberArray(key, new double[0])[0];
		return screenWidthCenter - centerX;
	}
}
