package org.usfirst.frc.team2996.robot;

import java.util.List;

public class RecordingAutoTester {

	private StateRunner runner;
	
	public RecordingAutoTester(Robot robot) {
		runner = new StateRunner(robot);
		
		GsonSmartDash.put();
	}
	
	public void getStates() {
		runner.counterInitialize();
		try {
			List<State> states = StateReader.read(StateLister.gsonChooser.getSelected());
			runner.setStates(states); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void runAuto() {
		runner.run();
	}
}
