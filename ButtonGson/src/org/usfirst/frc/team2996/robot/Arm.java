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
	
	public void armFunctions(boolean armUpBumper, double armDownTrigger) {
		switch(currentArmState) {
		case UP:
			if (!armUpBumper && armDownTrigger >= 0.15) {
				armSolenoid.set(DoubleSolenoid.Value.kForward);
				currentArmState = ArmStates.DOWN;
			}
			break;
		case DOWN:
			if (armUpBumper && armDownTrigger < 0.15) {
				armSolenoid.set(DoubleSolenoid.Value.kReverse);
				currentArmState = ArmStates.UP;
			}
			break;
		}
	}
	
	public void setArmState(boolean down) {
		if (down) {
			armSolenoid.set(DoubleSolenoid.Value.kForward);
			currentArmState = ArmStates.DOWN;
		} else if (!down) {
			armSolenoid.set(DoubleSolenoid.Value.kReverse);
			currentArmState = ArmStates.UP;
		}
	}
}
