package org.usfirst.frc.team2996.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Controllers {
	
	private Joystick mobilityController;
	private double driveRightAxis;
	private double driveLeftAxis;
	
	public Controllers() {
		mobilityController = new Joystick(Constants.MOBILITY_CONTROLLER_PORT);
	}
	
	public void setControllerInputValues() {
		driveRightAxis = mobilityController.getRawAxis(Constants.DRIVE_RIGHT_AXIS);
		driveLeftAxis = mobilityController.getRawAxis(Constants.DRIVE_LEFT_AXIS);
	}
	
	public double getDriveForwardAxis() {
		return driveRightAxis;
	}

	public double getDriveTurnAxis() {
		return driveLeftAxis;
	}
	
}
