package org.usfirst.frc.team2996.robot;

import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.kauailabs.navx.frc.*;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DeadReckoningAutos {

	static final int WHEEL_DIAMETER = 6; // in inches
	static final int ENCODER_TICKS_PER_REV = 360 * 4;
	static final double DISTANCE_PER_ENCODER_TICK = ((WHEEL_DIAMETER * Math.PI) / ENCODER_TICKS_PER_REV); // in inches
	
	static final double GYRO_CONSTANT = 0.03;
	
	static final double TURNING_GYRO_OFFSET = 70;
	static final double TURNING_GYRO_OFFSET2 = 10;
	static final double TURNING_GYRO_OFFSET3 = 0;
	
	private DifferentialDrive robotDrive;

	private SensorCollection frontLeftSensors;
	private SensorCollection frontRightSensors;
	
	private Intake intake;
	private Arm arm;
	
	private AHRS navX;
	
	int frontLeftEncoder;
	int frontRightEncoder;
	int encoderAverage;
	double encoderAverageInches;
	
	private double turnAngle = 90;
	private boolean doneTurning = false;
	
	private int pauseCounter = 0;
	private int turnPause = 40;
	private int cubePause = 60;
	private int outtakePause = 100;
	
	private int turnLoopCounter = 0;
	private int turnLoopLimit = 40;
	
	private double autoDriveSpeed = -1;
	private double autoTurnSpeedHigh = .75;
	private double autoTurnSpeedLow = .4;
	private double autoTurnSpeedBack = .2;
	
	private double middleCrossLineForwardDistance = 95;
	
	private double middleSwitchRightBackForwardDistance = 95;
	private double middleSwitchRightBackBackwardDistance = -69;
	
	private double middleSwitchLeftBackForwardDistance1 = 35;
	private double middleSwitchLeftBackForwardDistance2 = 109;
	private double middleSwitchLeftBackForwardDistance3 = 69;
	private double middleSwitchLeftBackBackwardDistance = -69;
	
	private double leftCrossLineForwardDistance = 194;
	
	private double leftSwitchForwardDistance1 = 144;
	private double leftSwitchForwardDistance2 = 29;
	
	private double rightCrossLineForwardDistance = 194;
	
	private double rightSwitchForwardDistance1 = 144;
	private double rightSwitchForwardDistance2 = 29;
	
	public DeadReckoningAutos(Robot robot) {
		robotDrive = robot.getDrive().getRobotDrive();

		frontLeftSensors = robot.getDrive().getFrontLeftSensors();
		frontRightSensors = robot.getDrive().getFrontRightSensors();
		
		intake = robot.getIntake();
		arm = robot.getArm();
		
		navX = new AHRS(SPI.Port.kMXP);
	}
	
	public void setEncoderValues() {
		frontLeftEncoder = frontLeftSensors.getQuadraturePosition();
		frontRightEncoder = -frontRightSensors.getQuadraturePosition();
		encoderAverage = (frontLeftEncoder + frontRightEncoder) / 2;
		encoderAverageInches = encoderAverage * DISTANCE_PER_ENCODER_TICK;
	}
	
	public void gyroCorrect() {
		double angle = navX.getAngle();
		System.out.println("Gyro: " + angle);
		robotDrive.curvatureDrive(autoDriveSpeed, angle * GYRO_CONSTANT, false);
	}
	
	public void backwardGyroCorrect() {
		double angle = navX.getAngle();
		System.out.println("Gyro: " + angle);
		robotDrive.curvatureDrive(-autoDriveSpeed, angle * GYRO_CONSTANT, false);
	}
	
	public void reset() {
		robotDrive.curvatureDrive(0, 0, false);
		currentTurningState = TurningStates.HIGH_SPEED;
		frontLeftSensors.setQuadraturePosition(0, 10);
		frontRightSensors.setQuadraturePosition(0, 10);
		navX.reset();
		doneTurning = false;
		pauseCounter = 0;
		turnLoopCounter = 0;
		resetStates();
	}
	
	public void resetStates() {
		currentTurningState = TurningStates.HIGH_SPEED;
		
		currentMiddleSwitchRightBackState = MiddleSwitchRightBackStates.DRIVING_FORWARD;
		currentMiddleSwitchLeftBackState = MiddleSwitchLeftBackStates.DRIVING_FORWARD1;
		
		currentLeftSwitchState = LeftSwitchStates.DRIVING_FORWARD;
		currentRightSwitchState = RightSwitchStates.DRIVING_FORWARD;
	}
	
	public enum TurningStates{
		HIGH_SPEED, LOW_SPEED, BACK
	}
	
	TurningStates currentTurningState = TurningStates.HIGH_SPEED;
	
	public void leftTurnCheck() {
		if (!doneTurning){
			leftTurn();
		} else {
			robotDrive.curvatureDrive(0, 0, false);
			frontLeftSensors.setQuadraturePosition(0, 10);
			frontRightSensors.setQuadraturePosition(0, 10);
			navX.reset();
		}
	}
	
	public void leftTurn() {
		switch(currentTurningState) {
		case HIGH_SPEED:
			if (navX.getAngle() > (-turnAngle) + TURNING_GYRO_OFFSET) {
				robotDrive.curvatureDrive(0, autoTurnSpeedHigh, true);
			} else {
				robotDrive.curvatureDrive(0, 0, false);
				currentTurningState = TurningStates.LOW_SPEED;
			}
			break;
		case LOW_SPEED:
			if (navX.getAngle() > (-turnAngle) + TURNING_GYRO_OFFSET2) {
				robotDrive.curvatureDrive(0, autoTurnSpeedLow, true);
			} else {
				robotDrive.curvatureDrive(0, 0, false);
				currentTurningState = TurningStates.BACK;
			}
			break;
		case BACK:
			System.out.println("Back");
			if (turnLoopCounter < turnLoopLimit){
				if (navX.getAngle() < (-turnAngle) - TURNING_GYRO_OFFSET3) {
					robotDrive.curvatureDrive(0, -autoTurnSpeedBack, true);
				} else if (navX.getAngle() > (-turnAngle) + TURNING_GYRO_OFFSET3) {
					robotDrive.curvatureDrive(0, autoTurnSpeedBack, true);
				}
				turnLoopCounter++;
			} else {
				robotDrive.curvatureDrive(0, 0, false);
				doneTurning = true;
			}
			break;
		}
	}
	
	public void rightTurnCheck() {
		if (!doneTurning){
			rightTurn();
		} else {
			robotDrive.curvatureDrive(0, 0, false);
			frontLeftSensors.setQuadraturePosition(0, 10);
			frontRightSensors.setQuadraturePosition(0, 10);
			navX.reset();
		}
	}
	
	public void rightTurn() {
		System.out.println(navX.getAngle());
		switch(currentTurningState) {
		case HIGH_SPEED:
			System.out.println("High");
			if (navX.getAngle() < turnAngle - TURNING_GYRO_OFFSET) {
				robotDrive.curvatureDrive(0, -autoTurnSpeedHigh, true);
			} else {
				robotDrive.curvatureDrive(0, 0, false);
				currentTurningState = TurningStates.LOW_SPEED;
			}
			break;
		case LOW_SPEED:
			System.out.println("Low");
			if (navX.getAngle() < turnAngle - TURNING_GYRO_OFFSET2) {
				robotDrive.curvatureDrive(0, -autoTurnSpeedLow, true);
			} else {
				robotDrive.curvatureDrive(0, 0, false);
				currentTurningState = TurningStates.BACK;
			}
			break;
		case BACK:
			if (turnLoopCounter < turnLoopLimit){
				if (navX.getAngle() > (turnAngle) + TURNING_GYRO_OFFSET3) {
					System.out.println("Back1");
					robotDrive.curvatureDrive(0, autoTurnSpeedBack, true);
				} else if (navX.getAngle() < (turnAngle) - TURNING_GYRO_OFFSET3) {
					System.out.println("Back2");
					robotDrive.curvatureDrive(0, -autoTurnSpeedBack, true);
				}
				turnLoopCounter++;
			} else {
				robotDrive.curvatureDrive(0, 0, false);
				doneTurning = true;
			}
			break;
		}
	}
	
	
	public void middleCrossLine() {
		if (encoderAverageInches <= middleCrossLineForwardDistance) {
			gyroCorrect();
		} else {
			robotDrive.curvatureDrive(0, 0, false);
		}
	}
	
	
	public enum MiddleSwitchRightBackStates {
		DRIVING_FORWARD, CUBE_PAUSE, DROP_CUBE, DRIVING_BACKWARD
	}
	
	MiddleSwitchRightBackStates currentMiddleSwitchRightBackState = MiddleSwitchRightBackStates.DRIVING_FORWARD;
	
	public void middleSwitchRightBack() {
		switch (currentMiddleSwitchRightBackState) {
		case DRIVING_FORWARD:
			if (encoderAverageInches <= middleSwitchRightBackForwardDistance) {
				gyroCorrect();
			} else {
				reset();
				currentMiddleSwitchRightBackState = MiddleSwitchRightBackStates.CUBE_PAUSE;
			}
			break;
		case CUBE_PAUSE:
			if (pauseCounter < cubePause){
				pauseCounter++;
			} else {
				reset();
				currentMiddleSwitchRightBackState = MiddleSwitchRightBackStates.DROP_CUBE;
			}
			break;
		case DROP_CUBE:
			if (pauseCounter < outtakePause){
				intake.outtake();
				pauseCounter++;
			} else {
				intake.stop();
				currentMiddleSwitchRightBackState = MiddleSwitchRightBackStates.DRIVING_BACKWARD;
			}
			break;
		case DRIVING_BACKWARD:
			if (encoderAverageInches >= middleSwitchRightBackBackwardDistance) {
				backwardGyroCorrect();
			} else {
				robotDrive.curvatureDrive(0, 0, false);
				arm.setArmState(true);
			}
			break;
		}
	}
	
	
	public enum MiddleSwitchLeftBackStates {
		DRIVING_FORWARD1, TURN_PAUSE1, TURNING1, TURN_PAUSE2, DRIVING_FORWARD2, 
		TURN_PAUSE3, TURNING2, TURN_PAUSE4, DRIVING_FORWARD3, CUBE_PAUSE1, DROP_CUBE1, DRIVING_BACKWARD
	}
	
	MiddleSwitchLeftBackStates currentMiddleSwitchLeftBackState = MiddleSwitchLeftBackStates.DRIVING_FORWARD1;
	
	public void middleSwitchLeftBack() {
		switch (currentMiddleSwitchLeftBackState) {
		case DRIVING_FORWARD1:
			if (encoderAverageInches <= middleSwitchLeftBackForwardDistance1) {
				gyroCorrect();
			} else {
				reset();
				currentMiddleSwitchLeftBackState = MiddleSwitchLeftBackStates.TURN_PAUSE1;
			}
			break;
		case TURN_PAUSE1:
			if (pauseCounter < turnPause){
				pauseCounter++;
			} else {
				currentMiddleSwitchLeftBackState = MiddleSwitchLeftBackStates.TURNING1;
			}
			break;
		case TURNING1:
			if (!doneTurning) {
				leftTurn();
			} else {
				reset();
				currentMiddleSwitchLeftBackState = MiddleSwitchLeftBackStates.TURN_PAUSE2;
			}
			break;
		case TURN_PAUSE2:
			if (pauseCounter < turnPause){
				if (pauseCounter < turnPause - 1){
					navX.reset();
				}
				pauseCounter++;	
			} else {
				reset();
				currentMiddleSwitchLeftBackState = MiddleSwitchLeftBackStates.DRIVING_FORWARD2;
			}
			break;
		case DRIVING_FORWARD2:
			if (encoderAverageInches <= middleSwitchLeftBackForwardDistance2) {
				gyroCorrect();
			} else {
				reset();
				currentMiddleSwitchLeftBackState = MiddleSwitchLeftBackStates.TURN_PAUSE3;
			}
			break;
		case TURN_PAUSE3:
			if (pauseCounter < turnPause){
				pauseCounter++;
			} else {
				currentMiddleSwitchLeftBackState = MiddleSwitchLeftBackStates.TURNING2;
			}
			break;
		case TURNING2:
			if (!doneTurning) {
				rightTurn();
			} else {
				reset();
				currentMiddleSwitchLeftBackState = MiddleSwitchLeftBackStates.TURN_PAUSE4;
			}
			break;
		case TURN_PAUSE4:
			if (pauseCounter < turnPause){
				pauseCounter++;
			} else {
				currentMiddleSwitchLeftBackState = MiddleSwitchLeftBackStates.DRIVING_FORWARD3;
			}
			break;
		case DRIVING_FORWARD3:
			if (encoderAverageInches <= middleSwitchLeftBackForwardDistance3) {
				gyroCorrect();
			} else {
				reset();
				currentMiddleSwitchLeftBackState = MiddleSwitchLeftBackStates.CUBE_PAUSE1;
			}
			break;
		case CUBE_PAUSE1:
			if (pauseCounter < cubePause){
				pauseCounter++;
			} else {
				reset();
				currentMiddleSwitchLeftBackState = MiddleSwitchLeftBackStates.DROP_CUBE1;
			}
			break;
		case DROP_CUBE1:
			if (pauseCounter < outtakePause){
				intake.outtake();
				pauseCounter++;
			} else {
				intake.stop();
				reset();
				currentMiddleSwitchLeftBackState = MiddleSwitchLeftBackStates.DRIVING_BACKWARD;
			}
			break;
		case DRIVING_BACKWARD:
			if (encoderAverageInches >= middleSwitchLeftBackBackwardDistance) {
				backwardGyroCorrect();
			} else {
				robotDrive.curvatureDrive(0, 0, false);
				arm.setArmState(true);
			}
			break;
		}
	}
	
	
	public void leftCrossLine() {
		if (encoderAverageInches <= leftCrossLineForwardDistance) {
			gyroCorrect();
		} else {
			robotDrive.curvatureDrive(0, 0, false);
		}
	}
	
	
	public enum LeftSwitchStates {
		DRIVING_FORWARD, TURN_PAUSE1, TURNING, TURN_PAUSE2, DRIVING_FORWARD_AGAIN, CUBE_PAUSE, DROP_CUBE
	}
	
	LeftSwitchStates currentLeftSwitchState = LeftSwitchStates.DRIVING_FORWARD;
	
	public void leftSwitch() {
		switch (currentLeftSwitchState) {
		case DRIVING_FORWARD:
			if (encoderAverageInches <= leftSwitchForwardDistance1) {
				gyroCorrect();
			} else {
				reset();
				currentLeftSwitchState = LeftSwitchStates.TURN_PAUSE1;
			}
			break;
		case TURN_PAUSE1:
			if (pauseCounter < turnPause){
				pauseCounter++;
			} else {
				currentLeftSwitchState = LeftSwitchStates.TURNING;
			}
			break;
		case TURNING:
			if (!doneTurning) {
				rightTurn();
			} else {
				reset();
				currentLeftSwitchState = LeftSwitchStates.TURN_PAUSE2;
			}
			break;
		case TURN_PAUSE2:
			if (pauseCounter < turnPause){
				if (pauseCounter < turnPause - 1){
					navX.reset();
				}
				pauseCounter++;
			} else {
				currentLeftSwitchState = LeftSwitchStates.DRIVING_FORWARD_AGAIN;
			}
			break;
		case DRIVING_FORWARD_AGAIN:
			if (encoderAverageInches <= leftSwitchForwardDistance2) {
				gyroCorrect();
			} else {
				reset();
				currentLeftSwitchState = LeftSwitchStates.CUBE_PAUSE;
			}
			break;
		case CUBE_PAUSE:
			if (pauseCounter < cubePause){
				pauseCounter++;
			} else {
				reset();
				currentLeftSwitchState = LeftSwitchStates.DROP_CUBE;
			}
			break;
		case DROP_CUBE:
			if (pauseCounter < outtakePause){
				intake.outtake();
				pauseCounter++;
			} else {
				intake.stop();
			}
			break;
		}
	}
	
	
	public void rightCrossLine() {
		if (encoderAverageInches <= rightCrossLineForwardDistance) {
			gyroCorrect();
		} else {
			robotDrive.curvatureDrive(0, 0, false);
		}
	}
	
	
	public enum RightSwitchStates {
		DRIVING_FORWARD, TURN_PAUSE1, TURNING, TURN_PAUSE2, DRIVING_FORWARD_AGAIN, CUBE_PAUSE, DROP_CUBE
	}
	
	RightSwitchStates currentRightSwitchState = RightSwitchStates.DRIVING_FORWARD;
	
	public void rightSwitch() {
		switch (currentRightSwitchState) {
		case DRIVING_FORWARD:
			if (encoderAverageInches <= rightSwitchForwardDistance1) {
				gyroCorrect();
			} else {
				reset();
				currentRightSwitchState = RightSwitchStates.TURN_PAUSE1;
			}
			break;
		case TURN_PAUSE1:
			if (pauseCounter < turnPause){
				pauseCounter++;
			} else {
				currentRightSwitchState = RightSwitchStates.TURNING;
			}
			break;
		case TURNING:
			if (!doneTurning) {
				leftTurn();
			} else {
				reset();
				currentRightSwitchState = RightSwitchStates.TURN_PAUSE2;
			}
			break;
		case TURN_PAUSE2:
			if (pauseCounter < turnPause){
				if (pauseCounter < turnPause - 1){
					navX.reset();
				}
				pauseCounter++;
			} else {
				currentRightSwitchState = RightSwitchStates.DRIVING_FORWARD_AGAIN;
			}
			break;
		case DRIVING_FORWARD_AGAIN:
			if (encoderAverageInches < rightSwitchForwardDistance2) {
				gyroCorrect();
			} else {
				reset();
				currentRightSwitchState = RightSwitchStates.CUBE_PAUSE;
			}
			break;
		case CUBE_PAUSE:
			if (pauseCounter < cubePause){
				pauseCounter++;
			} else {
				reset();
				currentRightSwitchState = RightSwitchStates.DROP_CUBE;
			}
			break;
		case DROP_CUBE:
			if (pauseCounter < outtakePause){
				intake.outtake();
				pauseCounter++;
			} else {
				intake.stop();
			}
			break;
		}
	}
}
