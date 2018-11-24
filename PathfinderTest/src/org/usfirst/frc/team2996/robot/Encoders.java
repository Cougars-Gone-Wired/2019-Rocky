package org.usfirst.frc.team2996.robot;

import com.ctre.phoenix.motorcontrol.SensorCollection;

public class Encoders {

	public SensorCollection leftSensors, rightSensors;
	
	double WHEEL_RADIUS = 3;
	double CIRCUMFRENCE = 2 * Math.PI * WHEEL_RADIUS;
	double PULSES_PER_REVOLUTION = 360;
	double distancePerPulse = CIRCUMFRENCE / PULSES_PER_REVOLUTION;
	
	public Encoders(Robot robot) {
		leftSensors = robot.getLeftSensors();
		rightSensors = robot.getRightSensors();
	}
	
	public double getLeftDistanceFeet() {
		return (leftSensors.getQuadraturePosition() * distancePerPulse) / 12;
	}
	
	public double getRightDistanceFeet() {
		return (rightSensors.getQuadraturePosition() * distancePerPulse) / 12; 
	}
	
	public void reset() {
		leftSensors.setQuadraturePosition(0, 10);
		rightSensors.setQuadraturePosition(0, 10);
	}
}
