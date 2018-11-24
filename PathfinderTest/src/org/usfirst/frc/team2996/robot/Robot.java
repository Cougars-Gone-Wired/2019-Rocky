package org.usfirst.frc.team2996.robot;

import java.io.File;

import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.DistanceFollower;

public class Robot extends IterativeRobot {

	static final int FRONT_LEFT_MOTOR_ID = 4;
	static final int REAR_LEFT_MOTOR_ID = 5;
	static final int FRONT_RIGHT_MOTOR_ID = 6;
	static final int REAR_RIGHT_MOTOR_ID = 7;
	
	WPI_TalonSRX frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor;
	SpeedControllerGroup leftMotors, rightMotors;
	SensorCollection leftSensors, rightSensors;
	
	AHRS gyro;
	Encoders encoders;
	
	File leftFile = new File("/home/lvuser/paths/LowTimeStep_left.csv");
	File rightFile = new File("/home/lvuser/paths/LowTimeStep_right.csv");
	File sourceFile = new File("/home/lvuser/paths/LowTimeStep_source.csv");
	Trajectory leftTrajectory = Pathfinder.readFromCSV(leftFile);
	Trajectory rightTrajectory = Pathfinder.readFromCSV(rightFile);
	Trajectory sourceTrajectory = Pathfinder.readFromCSV(sourceFile);
	DistanceFollower left;
	DistanceFollower right;
	DistanceFollower source;
	double P = 1;
	double I = 0;
	double D = 0;
	double maxVelocity = 2.0;
	double accelerationGain = 0.0;
	
	
	@Override
	public void robotInit() {
		frontLeftMotor = new WPI_TalonSRX(FRONT_LEFT_MOTOR_ID);
		rearLeftMotor = new WPI_TalonSRX(REAR_LEFT_MOTOR_ID);
		frontRightMotor = new WPI_TalonSRX(FRONT_RIGHT_MOTOR_ID);
		rearRightMotor = new WPI_TalonSRX(REAR_RIGHT_MOTOR_ID);
		
		leftMotors = new SpeedControllerGroup(frontLeftMotor, rearLeftMotor);
		rightMotors = new SpeedControllerGroup(frontRightMotor, rearRightMotor);
		
		leftSensors = new SensorCollection(frontLeftMotor);
		rightSensors = new SensorCollection(frontRightMotor);
		
		gyro = new AHRS(SPI.Port.kMXP);
		encoders = new Encoders(this);
		
		left = new DistanceFollower(leftTrajectory);
		right = new DistanceFollower(rightTrajectory);
		source = new DistanceFollower(sourceTrajectory);
		left.configurePIDVA(P, I, D, 1 / maxVelocity, accelerationGain);
		right.configurePIDVA(P, I, D, 1 / maxVelocity, accelerationGain);
		source.configurePIDVA(P, I, D, 1 / maxVelocity, accelerationGain);
	}

	@Override
	public void autonomousInit() {
		encoders.reset();
		gyro.reset();
		left.reset();
		right.reset();
		source.reset();
	}

	@Override
	public void autonomousPeriodic() {
		SmartDashboard.putNumber("leftEncoder", encoders.getLeftDistanceFeet());
		SmartDashboard.putNumber("right encoder", encoders.getRightDistanceFeet());
		
		runStraight();
		//runCurvy();
		
		if(left.isFinished()) {
			System.out.println("left is finished");
		} 
		if (right.isFinished()) {
			System.out.println("right is finished");
		}
	}

	@Override
	public void teleopPeriodic() {
		
	}
	
	public void runStraight() {
		double l = left.calculate(encoders.getLeftDistanceFeet());
		double r = right.calculate(encoders.getRightDistanceFeet());
		leftMotors.set(-l);
		rightMotors.set(r);
	}
	
	public void runCurvy() {
		double l = left.calculate(encoders.getLeftDistanceFeet());
		double r = right.calculate(encoders.getRightDistanceFeet());
		
		double gyroHeading = gyro.getAngle();
		double desiredHeading = Pathfinder.r2d(left.getHeading());
		
		double angleDifference = Pathfinder.boundHalfDegrees(desiredHeading - gyroHeading);
		double turn = 0.8 * (-1.0 / 80.0) * angleDifference;
		
		leftMotors.set(-(l + turn));
		rightMotors.set(r - turn);
	}
	
	public SensorCollection getLeftSensors() {
		return leftSensors;
	}

	public SensorCollection getRightSensors() {
		return rightSensors;
	}
}
