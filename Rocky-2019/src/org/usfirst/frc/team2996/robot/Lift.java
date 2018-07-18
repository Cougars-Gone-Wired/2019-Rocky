package org.usfirst.frc.team2996.robot;

import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;

public class Lift {

	private WPI_TalonSRX liftMasterMotor;
	private WPI_TalonSRX liftSlaveMotor;
	private SensorCollection liftSensors;
	
	private Solenoid liftGearSolenoid;
	private ChangeGear liftChangeGear;
	
	public Lift() {
		liftMasterMotor = new WPI_TalonSRX(Constants.LIFT_MASTER_MOTOR_ID);
		liftSlaveMotor = new WPI_TalonSRX(Constants.LIFT_SLAVE_MOTOR_ID);
		liftSlaveMotor.follow(liftMasterMotor);
		
		liftSensors = new SensorCollection(liftMasterMotor);
		
		liftGearSolenoid = new Solenoid(Constants.LIFT_GEAR_SOLENOID_PORT);
		liftChangeGear = new ChangeGear(liftGearSolenoid);
	}
	
	public enum LiftStates {
		NOT_MOVING, GOING_UP, GOING_DOWN
	}
	
	LiftStates currentLiftState = LiftStates.NOT_MOVING;
	
	public void liftFunctions(double liftAxis) {
		liftAxis = -Utility.deadZone(liftAxis);
		switch (currentLiftState) {
		case NOT_MOVING:
			if (liftAxis > 0 && !liftSensors.isFwdLimitSwitchClosed()) {
				liftMasterMotor.set(liftAxis);
				currentLiftState = LiftStates.GOING_UP;
			} else if (liftAxis < 0 && !liftSensors.isRevLimitSwitchClosed()) {
				liftMasterMotor.set(liftAxis);
				currentLiftState = LiftStates.GOING_DOWN;
			}
			break;
		case GOING_UP:
			if (liftSensors.isFwdLimitSwitchClosed() || (liftAxis == 0)) {
				liftMasterMotor.set(0);
				currentLiftState = LiftStates.NOT_MOVING;
			} else if (liftAxis > 0) {
				liftMasterMotor.set(liftAxis);
			}
			break;
		case GOING_DOWN:
			if (liftSensors.isRevLimitSwitchClosed() || (liftAxis == 0)) {
				liftMasterMotor.set(0);
				currentLiftState = LiftStates.NOT_MOVING;
			} else if (liftAxis < 0) {
				liftMasterMotor.set(liftAxis * .5); // to slow on the way down
			}
			break;
		}
	}

	public ChangeGear getLiftChangeGear() {
		return liftChangeGear;
	}
}
