package org.usfirst.frc.team2996.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Drive {

	private WPI_TalonSRX frontLeftMotor;
	private WPI_TalonSRX rearLeftMotor;
	private SpeedControllerGroup leftMotors;
	
	private WPI_TalonSRX frontRightMotor;
	private WPI_TalonSRX rearRightMotor;
	private SpeedControllerGroup rightMotors;
	
	private DifferentialDrive robotDrive;
	
	public Drive() {
		frontLeftMotor = new WPI_TalonSRX(Constants.FRONT_LEFT_MOTOR_ID);
		rearLeftMotor = new WPI_TalonSRX(Constants.REAR_LEFT_MOTOR_ID);
		leftMotors = new SpeedControllerGroup(frontLeftMotor, rearLeftMotor);
		
		frontRightMotor = new WPI_TalonSRX(Constants.FRONT_RIGHT_MOTOR_ID);
		rearRightMotor = new WPI_TalonSRX(Constants.REAR_RIGHT_MOTOR_ID);
		rightMotors = new SpeedControllerGroup(frontRightMotor, rearRightMotor);
		
		robotDrive = new DifferentialDrive(leftMotors, rightMotors);
	}
	
	public void tankDrive(double driveRightAxis, double driveLeftAxis) {
		driveRightAxis = Utility.deadZone(driveRightAxis) * Constants.DRIVE_SPEED;
		driveLeftAxis = Utility.deadZone(driveLeftAxis) * Constants.DRIVE_SPEED;
		
		robotDrive.tankDrive(driveRightAxis, driveLeftAxis);
	}
}
