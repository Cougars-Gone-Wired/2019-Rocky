package org.usfirst.frc.team2996.robot;

public class Constants {
	
	static final int MANIPULATOR_CONTROLLER_PORT = 0;
	static final int LIFT_AXIS = 1;
	static final int LIFT_HIGH_GEAR_BUMPER = 6;
	static final int LIFT_LOW_GEAR_TRIGGER = 3;
	static final int INTAKE_TRIGGER = 2;
	static final int OUTTAKE_TRIGGER = 3;
	static final int ARM_BUTTON = 1;
	
	static final int MOBILITY_CONTROLLER_PORT = 1;
	static final int DRIVE_FORWARD_AXIS = 1;
	static final int DRIVE_TURN_AXIS = 4;
	static final int DRIVE_HIGH_GEAR_BUMPER = 6;
	static final int DRIVE_LOW_GEAR_TRIGGER = 3;
	
	static final int LIFT_MASTER_MOTOR_ID = 0;
	static final int LIFT_SLAVE_MOTOR_ID = 1;
	static final int LEFT_INTAKE_MOTOR_ID = 2;
	static final int RIGHT_INTAKE_MOTOR_ID = 3;
	static final int FRONT_LEFT_MOTOR_ID = 4;
	static final int REAR_LEFT_MOTOR_ID = 5;
	static final int FRONT_RIGHT_MOTOR_ID = 6;
	static final int REAR_RIGHT_MOTOR_ID = 7;
	
	static final int ARM_SOLENOID_UP_PORT = 0;
	static final int ARM_SOLENOID_DOWN_PORT = 1;
	static final int LIFT_GEAR_SOLENOID_PORT = 2;
	static final int DRIVE_GEAR_SOLENOID_PORT = 3;
	
	static final double DRIVE_SPEED = 1;
	static final double DRIVE_FORWARD_CONSTANT = .95;
	static final double DRIVE_TURN_CONSTANT = .75;
}
