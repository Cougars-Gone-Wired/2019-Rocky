package org.usfirst.frc.team2996.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Intake {

	private WPI_TalonSRX leftIntakeMotor;
	private WPI_TalonSRX rightIntakeMotor;
	
	public Intake() {
		leftIntakeMotor = new WPI_TalonSRX(Constants.LEFT_INTAKE_MOTOR_ID);
		rightIntakeMotor = new WPI_TalonSRX(Constants.RIGHT_INTAKE_MOTOR_ID);
	}
	
	public enum IntakeStates {
		NOT_MOVING, INTAKING, OUTTAKING
	}
	
	IntakeStates currentIntakeState = IntakeStates.NOT_MOVING;
	
	public void intakeFunctions(boolean intakeTrigger, double outtakeTrigger) {
		switch (currentIntakeState) {
		case NOT_MOVING:
			if (intakeTrigger && outtakeTrigger < 0.15) {
				intake();
				currentIntakeState = IntakeStates.INTAKING;
			} else if (!intakeTrigger && outtakeTrigger >= 0.15) {
				outtake();
				currentIntakeState = IntakeStates.OUTTAKING;
			}
			break;
		case INTAKING:
		case OUTTAKING:
			if (!intakeTrigger && outtakeTrigger < 0.15) {
				stop();
				currentIntakeState = IntakeStates.NOT_MOVING;
			}
			break;
		}
	}
	
	public void intake() {
		leftIntakeMotor.set(-1);
		rightIntakeMotor.set(1);
	}
	
	public void outtake() {
		leftIntakeMotor.set(1);
		rightIntakeMotor.set(-1);
	}
	
	public void stop() {
		leftIntakeMotor.set(0);
		rightIntakeMotor.set(0);
	}
}
