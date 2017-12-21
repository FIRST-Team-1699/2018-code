package org.usfirst.frc.team1699.test.efficiency;

import java.util.Random;

import org.junit.Test;

public class TimeTest {
	
	Random rand = new Random();
	final int loops = 100000;
	
	@Test
	public void outsideLoop(){
		double d;
		for(int i = 0; i < loops; i++){
			d = rand.nextDouble();
			System.out.println(d);
		}
	}
	
	@Test
	public void insideLoop(){
		for(int i = 0; i < loops; i++){
			double d = rand.nextDouble();
			System.out.println(d);
		}
	}
}
