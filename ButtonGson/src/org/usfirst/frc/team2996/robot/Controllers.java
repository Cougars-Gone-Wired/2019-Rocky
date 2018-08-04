package org.usfirst.frc.team2996.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Controllers {

	private Joystick manipulatorController;
	private double liftAxis;
	private boolean liftHighGearBumper;
	private double liftLowGearTrigger;
//	private boolean intakeBumper;
//	private double outtakeTrigger;
	private double intakeAxis;
//	private Toggle armButton;
//	private boolean armButtonOutput;
	private boolean armUpBumper;
	private double armDownTrigger;
	
	private Joystick mobilityController;
	private double driveForwardAxis;
	private double driveTurnAxis;
	private boolean driveHighGearBumper;
	private double driveLowGearTrigger;
	
	public Controllers() {
		manipulatorController = new Joystick(Constants.MANIPULATOR_CONTROLLER_PORT);
//		armButton = new Toggle(manipulatorController, Constants.ARM_BUTTON);
		
		mobilityController = new Joystick(Constants.MOBILITY_CONTROLLER_PORT);
	}
	
	public void setControllerInputValues() {
		liftAxis = manipulatorController.getRawAxis(Constants.LIFT_AXIS);
		liftHighGearBumper = manipulatorController.getRawButton(Constants.LIFT_HIGH_GEAR_BUMPER);
		liftLowGearTrigger = manipulatorController.getRawAxis(Constants.LIFT_LOW_GEAR_TRIGGER);
//		intakeBumper = manipulatorController.getRawButton(Constants.INTAKE_BUMPER);
//		outtakeTrigger = manipulatorController.getRawAxis(Constants.OUTTAKE_TRIGGER);
		intakeAxis = manipulatorController.getRawAxis(Constants.INTAKE_AXIS);
//		armButtonOutput = armButton.toggle();
		armUpBumper = manipulatorController.getRawButton(Constants.ARM_UP_BUMPER);
		armDownTrigger = manipulatorController.getRawAxis(Constants.ARM_DOWN_TRIGGER);
		
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

//	public boolean isIntakeBumper() {
//		return intakeBumper;
//	}
//
//	public double getOuttakeTrigger() {
//		return outtakeTrigger;
//	}
	
	public double getIntakeAxis() {
		return intakeAxis;
	}

//	public boolean isArmButtonOutput() {
//		return armButtonOutput;
//	}

	public boolean isArmUpBumper() {
		return armUpBumper;
	}

	public double getArmDownTrigger() {
		return armDownTrigger;
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
