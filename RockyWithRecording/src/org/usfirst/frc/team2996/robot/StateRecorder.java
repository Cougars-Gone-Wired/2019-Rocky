package org.usfirst.frc.team2996.robot;

import java.util.ArrayList;
import java.util.List;

public class StateRecorder {

	List<State> states;

	public void initialize() {
		states = new ArrayList<>();
	}
	
	// stores states of buttons and axis during teleop in state variables in a list
	public void record(Controllers controllers) {
		State s = new State();

		s.setLiftAxisState(controllers.getLiftAxis());
		s.setLiftHighGearBumperState(controllers.isLiftHighGearBumper());
		s.setLiftLowGearTriggerState(controllers.getLiftLowGearTrigger());
		s.setIntakeAxis(controllers.getIntakeAxis());
		s.setArmUpBumperState(controllers.isArmUpBumper());
		s.setArmDownTriggerState(controllers.getArmDownTrigger());
		
		s.setDriveForwardAxisState(controllers.getDriveForwardAxis());
		s.setDriveTurnAxisState(controllers.getDriveTurnAxis());
		
		states.add(s);
	}

	public List<State> getStates() {
		return states;
	}
}
