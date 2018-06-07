package org.usfirst.frc.team2996.robot;

import java.util.ArrayList;
import java.util.List;

public class StateRecorder {

	List<State> states;

	public void initialize() {
		states = new ArrayList<>();
	}
	
	public void record(Controllers controllers) {
		State s = new State();

		s.setLiftAxisState(controllers.getLiftAxis());
		s.setLiftHighGearBumperState(controllers.isLiftHighGearBumper());
		s.setLiftLowGearTriggerState(controllers.getLiftLowGearTrigger());
		s.setIntakeTriggerState(controllers.getIntakeTrigger());
		s.setOuttakeTriggerState(controllers.getOuttakeTrigger());
		s.setArmButtonOutputState(controllers.isArmButtonOutput());
		
		s.setDriveForwardAxisState(controllers.getDriveForwardAxis());
		s.setDriveTurnAxisState(controllers.getDriveTurnAxis());
		s.setDriveHighGearBumperState(controllers.isDriveHighGearBumper());
		s.setDriveLowGearTriggerState(controllers.getDriveLowGearTrigger());
		
		states.add(s);
	}

	public List<State> getStates() {
		return states;
	}
}
