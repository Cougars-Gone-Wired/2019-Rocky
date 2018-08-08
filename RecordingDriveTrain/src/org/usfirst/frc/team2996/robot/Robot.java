package org.usfirst.frc.team2996.robot;

import java.util.List;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	private Controllers controllers;
	
	private Drive drive;
	
	private StateRecorder recorder;
	private StateRunner runner;
	
	@Override
	public void robotInit() {
		controllers = new Controllers();
		
		drive = new Drive();
		
		recorder = new StateRecorder();
		runner = new StateRunner(this);
		
		GsonSmartDash.put();
		
	}

	@Override
	public void autonomousInit() {
		runner.counterInitialize();
		try {
			List<State> states = StateReader.read(StateLister.gsonChooser.getSelected());
			runner.setStates(states); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void autonomousPeriodic() {
		runner.run();
	}

	@Override
	public void teleopInit() {
		recorder.initialize();
		runner.counterInitialize();
	}
	
	@Override
	public void teleopPeriodic() {
		controllers.setControllerInputValues();
		
		drive.tankDrive(controllers.getDriveForwardAxis(), controllers.getDriveTurnAxis());
		
		recorder.record(controllers);
	}

	@Override
	public void disabledInit() {
		if (GsonSmartDash.shouldRecord) {
			List<State> states = recorder.getStates();
			try {
				StatesWriter.writeStates(states, GsonSmartDash.gsonFileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		StateLister.getStateNames(); 
		
		SmartDashboard.putBoolean("Should Record", false);
	}

	@Override
	public void disabledPeriodic() {
		GsonSmartDash.set();
		
		if (!GsonSmartDash.shouldRecord) {
			SmartDashboard.putString("Gson File Name", "");
		}
	}

	public Drive getDrive() {
		return drive;
	}
}
