/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2996.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
	
	//wheelbase - 1.792ft
	
	double P = 0;//3.3 low gear, 0.6 high gear
	double I = 0;
	double D = 0;//0.5 low gear, 0.3 high gear
	
	double desiredFeet = 0;
	double desiredInches = 0;
	double desiredPulses = 0;
	
	boolean zeroEncoder = false;
	boolean enable = false;
	
	double motorSpeed = 0;
	boolean zeroSpeed = false;
	
	boolean testVelocity = false;
	double counter = 0;
	
	static final int FRONT_LEFT_MOTOR_ID = 4;
	static final int REAR_LEFT_MOTOR_ID = 5;
	static final int FRONT_RIGHT_MOTOR_ID = 6;
	static final int REAR_RIGHT_MOTOR_ID = 7;
	
	Joystick controller;
	
	WPI_TalonSRX masterLeftMotor, slaveLeftMotor, masterRightMotor, slaveRightMotor;
	DifferentialDrive robotDrive;
	
	SensorCollection leftSensors, rightSensors;
	Encoders encoders;
	
	Solenoid driveSolenoid;
	
	@Override
	public void robotInit() {
		controller = new Joystick(1);
		
		masterLeftMotor = new WPI_TalonSRX(FRONT_LEFT_MOTOR_ID);
	    slaveLeftMotor = new WPI_TalonSRX(REAR_LEFT_MOTOR_ID);
	    masterRightMotor = new WPI_TalonSRX(FRONT_RIGHT_MOTOR_ID);
	    slaveRightMotor = new WPI_TalonSRX(REAR_RIGHT_MOTOR_ID);
	    
	    masterLeftMotor.setInverted(true);
	    masterLeftMotor.setSensorPhase(true);
	    masterLeftMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
	    masterLeftMotor.configClosedLoopPeakOutput(0, .95, 10);
	    
	    masterRightMotor.setSensorPhase(true);
	    masterRightMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
	    masterRightMotor.configClosedLoopPeakOutput(0, .95, 10);
	    
	    slaveLeftMotor.setInverted(true);
	    slaveLeftMotor.follow(masterLeftMotor);
	    slaveRightMotor.follow(masterRightMotor);
	    
	    robotDrive = new DifferentialDrive(masterLeftMotor, masterRightMotor);
	    robotDrive.setSafetyEnabled(false);
	    
	    leftSensors = new SensorCollection(masterLeftMotor);
		rightSensors = new SensorCollection(masterRightMotor);
		
		encoders = new Encoders(this);
		
		driveSolenoid = new Solenoid(2);
		
		setPeriod(0.05);
	}

	@Override
	public void autonomousInit() {
	}

	@Override
	public void autonomousPeriodic() {
	}

	@Override
	public void teleopInit() {
		SmartDashboard.putNumber("P", P);
		SmartDashboard.putNumber("I", I);
		SmartDashboard.putNumber("D", D);
		
		SmartDashboard.putNumber("desired feet", desiredFeet);
		
		SmartDashboard.putBoolean("Zero Encoder", zeroEncoder);
		SmartDashboard.putBoolean("Enable", false);
		
		SmartDashboard.putBoolean("Test Velocity", testVelocity);
	}
	
	@Override
	public void teleopPeriodic() {
		//driveSolenoid.set(true);
		
		desiredFeet = SmartDashboard.getNumber("desired feet", 0);
		desiredInches = desiredFeet * 12;
		desiredPulses = (desiredFeet * 12) / Encoders.distancePerPulse;
		
		SmartDashboard.putNumber("desired inches", desiredInches);
		SmartDashboard.putNumber("desired pulses", desiredPulses);
		
		SmartDashboard.putNumber("encoder feet", encoders.getAverageDistanceFeet());
		SmartDashboard.putNumber("encoder inches", encoders.getAverageDistanceInches());
		SmartDashboard.putNumber("encoder pulses", encoders.getAverageCount());
		
		SmartDashboard.putNumber("encoder left pulses", encoders.getLeftCount());
		SmartDashboard.putNumber("encoder right pulses", encoders.getRightCount());
		
		P = SmartDashboard.getNumber("P", 0);
		I = SmartDashboard.getNumber("I", 0);
		D = SmartDashboard.getNumber("D", 0);
		
		zeroEncoder = SmartDashboard.getBoolean("Zero Encoder", false);
		enable = SmartDashboard.getBoolean("Enable", false);
		
		motorSpeed = -Utility.deadZone(controller.getRawAxis(1));
		zeroSpeed = SmartDashboard.getBoolean("Zero Speed", false);
		
		testVelocity = SmartDashboard.getBoolean("Test Velocity", false);
		
		if (zeroEncoder) {
			encoders.reset();
		}
		
		if (enable) {
			masterLeftMotor.config_kP(0, P, 10);
			masterLeftMotor.config_kI(0, I, 10);
			masterLeftMotor.config_kD(0, D, 10);
			masterLeftMotor.set(ControlMode.Position, desiredPulses);
			
			masterRightMotor.config_kP(0, P, 10);
			masterRightMotor.config_kI(0, I, 10);
			masterRightMotor.config_kD(0, D, 10);
			masterRightMotor.set(ControlMode.Position, desiredPulses);
		} else if (testVelocity) {
			counter += 0.05;
			if (counter < 10) {
				masterLeftMotor.set(0.95);
				masterRightMotor.set(0.95);
				SmartDashboard.putNumber("time", counter);
				SmartDashboard.putNumber("Velocity", encoders.getAverageDistanceFeet() / counter);
			} else {
				testVelocity = false;
			}
		} else {
			counter = 0;
			masterLeftMotor.set(motorSpeed);
			masterRightMotor.set(motorSpeed);
		}
	}
	
	@Override
	public void testPeriodic() {	
	}

	@Override 
	public void disabledInit() {
		encoders.reset();
		counter = 0;
	}
	
	public SensorCollection getLeftSensors() {
		return leftSensors;
	}

	public SensorCollection getRightSensors() {
		return rightSensors;
	}
}
