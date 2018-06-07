package org.usfirst.frc.team2996.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Arm {

	private DoubleSolenoid armSolenoid;
	
	public Arm() {
		armSolenoid = new DoubleSolenoid(Constants.ARM_SOLENOID_UP_PORT, Constants.ARM_SOLENOID_DOWN_PORT);
	}
	
	public enum ArmStates {
		UP, DOWN
	}
	
	ArmStates currentArmState = ArmStates.UP;
	
	public void armFunctions(boolean armButtonOutput) {
		switch (currentArmState) {
		case UP:
			if (!armButtonOutput) {
				armSolenoid.set(DoubleSolenoid.Value.kReverse);
				currentArmState = ArmStates.DOWN;
			}
			break;
		case DOWN:
			if (armButtonOutput) {
				armSolenoid.set(DoubleSolenoid.Value.kForward);
				currentArmState = ArmStates.UP;
			}
			break;
		}
	}
}
