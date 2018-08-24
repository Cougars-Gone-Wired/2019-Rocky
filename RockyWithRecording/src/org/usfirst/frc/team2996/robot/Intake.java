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
	
	public void intakeFunctions(double intakeAxis) {
		switch (currentIntakeState) {
		case NOT_MOVING:
			if (intakeAxis < -0.2) {
				outtake();
				currentIntakeState = IntakeStates.OUTTAKING;
			} else if (intakeAxis > 0.2) {
				intake();
				currentIntakeState = IntakeStates.INTAKING;
			}
			break;
		case INTAKING:
			if (intakeAxis > -0.2 && intakeAxis < 0.2) {
				stop();
				currentIntakeState = IntakeStates.NOT_MOVING;
			} else if (intakeAxis > 0.2) {
				intake();
			}
		case OUTTAKING:
			if (intakeAxis > -0.2 && intakeAxis < 0.2) {
				stop();
				currentIntakeState = IntakeStates.NOT_MOVING;
			} else if (intakeAxis < -0.2) {
				outtake();
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
