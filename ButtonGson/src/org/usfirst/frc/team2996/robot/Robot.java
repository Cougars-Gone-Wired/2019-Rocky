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
	
	private AutoPicker autoPicker;
	
	@Override
	public void robotInit() {
		controllers = new Controllers();
		
		lift = new Lift();
		intake = new Intake();
		arm = new Arm();
		
		drive = new Drive();
		
		recorder = new StateRecorder();
		
		autoPicker = new AutoPicker(this);
	}

	@Override
	public void autonomousInit() {
		drive.getDriveChangeGear().setGearState(false);
		
		autoPicker.pickAuto();
	}

	@Override
	public void autonomousPeriodic() {
		autoPicker.runAuto();
	}

	@Override
	public void teleopInit() {
		drive.getDriveChangeGear().setGearState(true);
		
		recorder.initialize();
	}
	
	@Override
	public void teleopPeriodic() {
		controllers.setControllerInputValues();
		
		lift.liftFunctions(controllers.getLiftAxis());
		intake.intakeFunctions(controllers.getIntakeAxis());
		arm.armFunctions(controllers.isArmUpBumper(), controllers.getArmDownTrigger());
		
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
