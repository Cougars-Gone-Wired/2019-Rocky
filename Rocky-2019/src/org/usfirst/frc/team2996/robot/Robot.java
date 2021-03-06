package org.usfirst.frc.team2996.robot;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {

	private Controllers controllers;
	
	private Lift lift;
	private Intake intake;
	private Arm arm;
	
	private Drive drive;
	
	@Override
	public void robotInit() {
		controllers = new Controllers();
		
		lift = new Lift();
		intake = new Intake();
		arm = new Arm();
		
		drive = new Drive();
	}

	@Override
	public void autonomousInit() {

	}

	@Override
	public void autonomousPeriodic() {

	}

	@Override
	public void teleopInit() {

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
	}

}
