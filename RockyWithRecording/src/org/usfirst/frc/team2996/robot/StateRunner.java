package org.usfirst.frc.team2996.robot;

import java.util.List;

public class StateRunner {

	private Lift lift;
	private Intake intake;
	private Arm arm;
	
	private Drive drive;
	
	List<State> states;
	
	int counter = 0;

	public StateRunner(Robot robot) {
		this.lift = robot.getLift();
		this.intake = robot.getIntake();
		this.arm = robot.getArm();
		
		this.drive = robot.getDrive();
	}

	public void counterInitialize() {
		counter = 0;
	}

	public void setStates(List<State> states) {
		this.states = states;
	}

	// calls subsystem methods using stored button and axes states as parameters
	public void run() {
		if (counter < states.size()) {
			State s = states.get(counter);

			lift.liftFunctions(s.getLiftAxisState());
			intake.intakeFunctions(s.getIntakeAxis());
			arm.armFunctions(s.isArmUpBumperState(), s.getArmDownTriggerState());
			
			drive.arcadeDrive(s.getDriveForwardAxisState(), s.getDriveTurnAxisState());
			
			lift.getLiftChangeGear().changeGear(s.isLiftHighGearBumperState(), s.getLiftLowGearTriggerState());
			
			counter++;
		}
	}
}
