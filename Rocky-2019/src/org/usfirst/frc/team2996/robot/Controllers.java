package org.usfirst.frc.team2996.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Controllers {

	private Joystick manipulatorController;
	private double liftAxis;
	private boolean liftHighGearBumper;
	private double liftLowGearTrigger;
	private double intakeTrigger;
	private double outtakeTrigger;
	private Toggle armButton;
	private boolean armButtonOutput;
	
	private Joystick mobilityController;
	private double driveForwardAxis;
	private double driveTurnAxis;
	private boolean driveHighGearBumper;
	private double driveLowGearTrigger;
	
	public Controllers() {
		manipulatorController = new Joystick(Constants.MANIPULATOR_CONTROLLER_PORT);
		armButton = new Toggle(manipulatorController, Constants.ARM_BUTTON);
		
		mobilityController = new Joystick(Constants.MOBILITY_CONTROLLER_PORT);
	}
	
	public void setControllerInputValues() {
		liftAxis = manipulatorController.getRawAxis(Constants.LIFT_AXIS);
		liftHighGearBumper = manipulatorController.getRawButton(Constants.LIFT_HIGH_GEAR_BUMPER);
		liftLowGearTrigger = manipulatorController.getRawAxis(Constants.LIFT_LOW_GEAR_TRIGGER);
		intakeTrigger = manipulatorController.getRawAxis(Constants.INTAKE_TRIGGER);
		outtakeTrigger = manipulatorController.getRawAxis(Constants.OUTTAKE_TRIGGER);
		armButtonOutput = armButton.toggle();
		
		driveForwardAxis = mobilityController.getRawAxis(Constants.DRIVE_FORWARD_AXIS);
		driveTurnAxis = mobilityController.getRawAxis(Constants.DRIVE_TURN_AXIS);
		driveHighGearBumper = mobilityController.getRawButton(Constants.DRIVE_HIGH_GEAR_BUMPER);
		driveLowGearTrigger = mobilityController.getRawAxis(Constants.DRIVE_LOW_GEAR_TRIGGER);
	}
	
	public double getLiftAxis() {
		return liftAxis;
	}

	public boolean isLiftHighGearBumper() {
		return liftHighGearBumper;
	}

	public double getLiftLowGearTrigger() {
		return liftLowGearTrigger;
	}

	public double getIntakeTrigger() {
		return intakeTrigger;
	}

	public double getOuttakeTrigger() {
		return outtakeTrigger;
	}

	public boolean isArmButtonOutput() {
		return armButtonOutput;
	}

	public double getDriveForwardAxis() {
		return driveForwardAxis;
	}

	public double getDriveTurnAxis() {
		return driveTurnAxis;
	}

	public boolean isDriveHighGearBumper() {
		return driveHighGearBumper;
	}

	public double getDriveLowGearTrigger() {
		return driveLowGearTrigger;
	}
	
}
