package org.usfirst.frc.team2996.robot;

import edu.wpi.first.wpilibj.Solenoid;

public class ChangeGear {

	private Solenoid gearSolenoid;
	
	public ChangeGear(Solenoid gearSolenoid) {
		this.gearSolenoid = gearSolenoid;
	}
	
	public enum GearStates {
		HIGH_GEAR, LOW_GEAR //low gear is false high gear is true
	}
	
	GearStates currentGearState = GearStates.LOW_GEAR;
	
	public void changeGear(boolean highGearBumper, double lowGearTrigger) {
		switch(currentGearState) {
		case HIGH_GEAR:
			if (!highGearBumper && lowGearTrigger >= 0.15) {
				gearSolenoid.set(false);
				currentGearState = GearStates.LOW_GEAR;
			}
			break;
		case LOW_GEAR:
			if (highGearBumper && lowGearTrigger < 0.15) {
				gearSolenoid.set(true);
				currentGearState = GearStates.HIGH_GEAR;
			}
			break;
		}
	}
	
	public void setGearState(boolean state) {
		gearSolenoid.set(state);
		if (state == true) {
			currentGearState = GearStates.HIGH_GEAR;
		} else {
			currentGearState = GearStates.LOW_GEAR;
		}
	}
}
