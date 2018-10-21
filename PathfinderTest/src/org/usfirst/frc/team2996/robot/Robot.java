package org.usfirst.frc.team2996.robot;

import java.io.File;

import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Notifier;
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
	
	Encoders encoder;
	IMU gyro;
	
	File leftFile = new File("/home/lvuser/paths/Test_left_Jaci.csv"); // ./Main_left_Jaci.csv
	File rightFile = new File("/home/lvuser/paths/Test_right_Jaci.csv"); // ./Main_right_Jaci.csv
	Trajectory leftTrajectory = Pathfinder.readFromCSV(leftFile);
	Trajectory rightTrajectory = Pathfinder.readFromCSV(rightFile);
	DistanceFollower left;
	DistanceFollower right;
	double wheelbase = 1.9791667; // Feet
	double wheelDiameter = 0.5; // Feet
	double maxVel = 9.256463818; // Feet/second
	double maxAccl = 9.84251969; // Feet/second^2
	double maxJerk = 196.850394; // Feet/second^3

	Notifier autoNotifier;
	
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
		
	    gyro = new IMU();
	    encoder = new Encoders(this);
	    
	    left = new DistanceFollower(leftTrajectory);
	    right = new DistanceFollower(rightTrajectory);
	    left.configurePIDVA(3.0, 0.0, 0.5, 1 / maxVel, 0);
	    right.configurePIDVA(3.0, 0.0, 0.5, 1 / maxVel, 0);

	    autoNotifier = new Notifier(runAuton);
	}

	@Override
	public void autonomousInit() {
		autoNotifier.startPeriodic(0.2);
	}

	@Override
	public void autonomousPeriodic() {

	}

	@Override
	public void teleopInit() {

	}
	
	@Override
	public void teleopPeriodic() {
		
	}

	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {

	}
	
	public SensorCollection getLeftSensors() {
		return leftSensors;
	}

	public SensorCollection getRightSensors() {
		return rightSensors;
	}

	Runnable runAuton = new Runnable() {
		@Override
		public void run() {
			if (!left.isFinished() || !right.isFinished()) {
				double l = left.calculate(encoder.getLeftDistanceFeet());
				double r = right.calculate(encoder.getRightDistanceFeet());
	  
				double gyro_heading = gyro.getAngle();
				double desired_heading = Pathfinder.r2d(left.getHeading());
	  
				double angleDifference = 0.8 * (-1.0 / 80.0) * Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
				double turn = 0.8 * (-1.0 / 80.0) * angleDifference;
	  
				leftMotors.set(0.5 * (l + turn));
				rightMotors.set(0.5 * (r - turn));
			} else {
				System.out.println("Done!!");
			}
		}
	};
}
