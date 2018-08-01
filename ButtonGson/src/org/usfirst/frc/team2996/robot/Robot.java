package org.usfirst.frc.team2996.robot;

import java.util.List;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	private Controllers controllers;
	
	private Lift lift;
	private Intake intake;
	private Arm arm;
	
	private Drive drive;
	
	private StateRecorder recorder;
	private StateRunner runner;
	
	@Override
	public void robotInit() {
		controllers = new Controllers();
		
		lift = new Lift();
		intake = new Intake();
		arm = new Arm();
		
		drive = new Drive();
		
		recorder = new StateRecorder();
		runner = new StateRunner(this);
		
		GsonSmartDash.put();
		
	}

	@Override
	public void autonomousInit() {
		drive.getDriveChangeGear().setGearState(false);
		
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
//		lift.getLiftChangeGear().setGearState(true);
		drive.getDriveChangeGear().setGearState(true);
		
		recorder.initialize();
		runner.counterInitialize();
	}
	
	@Override
	public void teleopPeriodic() {
		controllers.setControllerInputValues();
		
		lift.liftFunctions(controllers.getLiftAxis());
		intake.intakeFunctions(controllers.getIntakeAxis());
		arm.armFunctions(controllers.isArmButtonOutput());
		
		drive.arcadeDrive(controllers.getDriveForwardAxis(), controllers.getDriveTurnAxis());
		
		lift.getLiftChangeGear().changeGear(controllers.isLiftHighGearBumper(), controllers.getLiftLowGearTrigger());
		drive.getDriveChangeGear().changeGear(controllers.isDriveHighGearBumper(), controllers.getDriveLowGearTrigger());
		
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
	
	public Lift getLift() {
		return lift;
	}

	public Intake getIntake() {
		return intake;
	}

	public Arm getArm() {
		return arm;
	}

	public Drive getDrive() {
		return drive;
	}
}
