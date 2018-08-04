package org.usfirst.frc.team2996.robot;

public class State {

	private double liftAxisState;
	private boolean liftHighGearBumperState;
	private double liftLowGearTriggerState;
//	private boolean intakeBumperState;
//	private double outtakeTriggerState;
	private double intakeAxis;
//	private boolean armButtonOutputState;
	private boolean armUpBumperState;
	private double armDownTriggerState;

	private double driveForwardAxisState;
	private double driveTurnAxisState;
//	private boolean driveHighGearBumperState;
//	private double driveLowGearTriggerState;

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

//	public boolean isIntakeBumperState() {
//		return intakeBumperState;
//	}
//
//	public void setIntakeBumperState(boolean intakeBumperState) {
//		this.intakeBumperState = intakeBumperState;
//	}
//
//	public double getOuttakeTriggerState() {
//		return outtakeTriggerState;
//	}
//
//	public void setOuttakeTriggerState(double outtakeTriggerState) {
//		this.outtakeTriggerState = outtakeTriggerState;
//	}
	
	public double getIntakeAxis() {
		return intakeAxis;
	}

	public void setIntakeAxis(double intakeAxis) {
		this.intakeAxis = intakeAxis;
	}

//	public boolean isArmButtonOutputState() {
//		return armButtonOutputState;
//	}
//
//	public void setArmButtonOutputState(boolean armButtonOutputState) {
//		this.armButtonOutputState = armButtonOutputState;
//	}
	
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

//	public boolean isDriveHighGearBumperState() {
//		return driveHighGearBumperState;
//	}
//
//	public void setDriveHighGearBumperState(boolean driveHighGearBumperState) {
//		this.driveHighGearBumperState = driveHighGearBumperState;
//	}
//
//	public double getDriveLowGearTriggerState() {
//		return driveLowGearTriggerState;
//	}
//
//	public void setDriveLowGearTriggerState(double driveLowGearTriggerState) {
//		this.driveLowGearTriggerState = driveLowGearTriggerState;
//	}
}
