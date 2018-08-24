package org.usfirst.frc.team2996.robot;

public class State {

	// variables to hold the states of all buttons and axes
	private double liftAxisState;
	private boolean liftHighGearBumperState;
	private double liftLowGearTriggerState;
	private double intakeAxis;
	private boolean armUpBumperState;
	private double armDownTriggerState;

	private double driveForwardAxisState;
	private double driveTurnAxisState;

	// getters and setters for all variables
	public double getLiftAxisState() {
		return liftAxisState;
	}

	public void setLiftAxisState(double liftAxisState) {
		this.liftAxisState = liftAxisState;
	}

	public boolean isLiftHighGearBumperState() {
		return liftHighGearBumperState;
	}

	public void setLiftHighGearBumperState(boolean liftHighGearBumperState) {
		this.liftHighGearBumperState = liftHighGearBumperState;
	}

	public double getLiftLowGearTriggerState() {
		return liftLowGearTriggerState;
	}

	public void setLiftLowGearTriggerState(double liftLowGearTriggerState) {
		this.liftLowGearTriggerState = liftLowGearTriggerState;
	}

	public double getIntakeAxis() {
		return intakeAxis;
	}

	public void setIntakeAxis(double intakeAxis) {
		this.intakeAxis = intakeAxis;
	}

	public boolean isArmUpBumperState() {
		return armUpBumperState;
	}

	public void setArmUpBumperState(boolean armUpBumperState) {
		this.armUpBumperState = armUpBumperState;
	}

	public double getArmDownTriggerState() {
		return armDownTriggerState;
	}

	public void setArmDownTriggerState(double armDownTriggerState) {
		this.armDownTriggerState = armDownTriggerState;
	}
	
	public double getDriveForwardAxisState() {
		return driveForwardAxisState;
	}
	
	public void setDriveForwardAxisState(double driveForwardAxisState) {
		this.driveForwardAxisState = driveForwardAxisState;
	}

	public double getDriveTurnAxisState() {
		return driveTurnAxisState;
	}

	public void setDriveTurnAxisState(double driveTurnAxisState) {
		this.driveTurnAxisState = driveTurnAxisState;
	}
}
