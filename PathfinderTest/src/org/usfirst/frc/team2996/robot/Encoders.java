package org.usfirst.frc.team2996.robot;

import com.ctre.phoenix.motorcontrol.SensorCollection;

public class Encoders {

	double WHEEL_RADIUS = 3;
	double CIRCUMFRENCE = 2 * Math.PI * WHEEL_RADIUS;
	double PULSES_PER_REVOLUTION = 1440;
	double distancePerPulse = CIRCUMFRENCE / PULSES_PER_REVOLUTION;
	
	private SensorCollection leftSensors;
	private SensorCollection rightSensors;
	
	public Encoders(Robot robot) {
		leftSensors = robot.getDrive().getLeftSensors();
		rightSensors = robot.getDrive().getRightSensors();
	}
	
	/**
	 * Get the count of the encoders
	 * 
	 * @return pulses of encoders
	 */
	public int getLeftCount() {
		return leftSensors.getQuadraturePosition();
	}

	public int getRightCount() {
		return rightSensors.getQuadraturePosition();
	}
	
	/**
	 * Convert the count of the encoders to inches
	 * 
	 * @return inches traveled
	 */
	public double getLeftDistanceInches() {
		return leftSensors.getQuadraturePosition() * distancePerPulse;
	}

	public double getRightDistanceInches() {
		return rightSensors.getQuadraturePosition() * distancePerPulse;
	}
	
	/**
	 * Convert the count of the encoders to feet
	 * 
	 * @return feet traveled
	 */
	public double getLeftDistanceFeet() {
		return (leftSensors.getQuadraturePosition() * distancePerPulse) / 12;
	}

	public double getRightDistanceFeet() {
		return (rightSensors.getQuadraturePosition() * distancePerPulse) / 12;
	}
	
	/**
	 * Reset methods
	 * 
	 */
	public void resetLeftEncoder() {
		leftSensors.setQuadraturePosition(0, 10);
	}

	public void resetRightEncoder() {
		rightSensors.setQuadraturePosition(0, 10);
	}

	public void reset() {
		leftSensors.setQuadraturePosition(0, 10);
		rightSensors.setQuadraturePosition(0, 10);
	}
}
