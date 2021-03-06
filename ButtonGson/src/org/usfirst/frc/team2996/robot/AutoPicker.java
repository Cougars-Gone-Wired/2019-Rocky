package org.usfirst.frc.team2996.robot;

import java.util.List;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoPicker {
	
	private static final String RECORDING = "Recording";
	private static final String RECORDING_TEST = "Recording Test";
	private static final String DEAD_RECKONING = "Dead Reckoning";
	
	private static final String MIDDLE = "Middle";
	private static final String LEFT = "Left";
	private static final String RIGHT = "Right";

	private static final String SWITCH = "Switch";
	private static final String SCALE = "Scale";

	private static final String LLL = "LLL";
	private static final String RRR = "RRR";
	private static final String LRL = "LRL";
	private static final String RLR = "RLR";
	
	private SendableChooser<String> autoType = new SendableChooser<>();
	private SendableChooser<String> position = new SendableChooser<>();
	private SendableChooser<String> priority = new SendableChooser<>();
	
	String selectedAutoType;
	String currentPosition;
	String currentPriority;
	String fieldConfiguration;
	
	public enum AutoStates {
		MIDDLE_CROSS_LINE, MIDDLE_SWITCH_RIGHT, MIDDLE_SWITCH_LEFT,
		LEFT_CROSS_LINE, LEFT_SWITCH, LEFT_SCALE_LEFT, LEFT_SCALE_RIGHT,
		RIGHT_CROSS_LINE, RIGHT_SWITCH, RIGHT_SCALE_RIGHT, RIGHT_SCALE_LEFT,
		DO_NOTHING
	}
	
	public AutoStates autoChanger;
	
	private StateRunner runner;
	private RecordingAutoTester recordingAutoTester;
	private DeadReckoningAutos deadReckoningAutos;
	
	public AutoPicker(Robot robot) {
		autoType.addObject("Recording", RECORDING);
		autoType.addObject("Recording Test", RECORDING_TEST);
		autoType.addObject("Dead Reckoning", DEAD_RECKONING);
		
		position.addDefault("Middle", MIDDLE); 
		position.addObject("Left", LEFT);
		position.addObject("Right", RIGHT);

		priority.addObject("Switch", SWITCH);
		priority.addObject("Scale", SCALE);
		
		SmartDashboard.putData("Auto Type", autoType);
		SmartDashboard.putData("Robot Position", position);
		SmartDashboard.putData("Auto Priority", priority);
		
		runner = new StateRunner(robot);
		recordingAutoTester = new RecordingAutoTester(robot);
		deadReckoningAutos = new DeadReckoningAutos(robot);
	}
	
	public void pickAuto() {
		selectedAutoType = autoType.getSelected();
		currentPosition = position.getSelected();
		currentPriority = priority.getSelected();
		fieldConfiguration = DriverStation.getInstance().getGameSpecificMessage();
		
		SmartDashboard.putString("Field Config", fieldConfiguration);
		
		String fileName = "default";
		
		switch(currentPosition) {
		case MIDDLE:
			switch(fieldConfiguration) {
			case LLL:
			case LRL:
				autoChanger = AutoStates.MIDDLE_SWITCH_LEFT;
				break;
			case RRR:
			case RLR:
				autoChanger = AutoStates.MIDDLE_SWITCH_RIGHT;
				break;
			default:
				autoChanger = AutoStates.MIDDLE_CROSS_LINE;
				break;
			}
			break;
		case LEFT:
			switch(currentPriority) {
			case SWITCH:
				switch(fieldConfiguration) {
				case RRR:
				case RLR:
					autoChanger = AutoStates.LEFT_CROSS_LINE;
					break;
				case LLL:
				case LRL:
					autoChanger = AutoStates.LEFT_SWITCH;
					break;
				default:
					autoChanger = AutoStates.MIDDLE_CROSS_LINE;
					break;
				}
				break;
			case SCALE:
				switch(fieldConfiguration) {
				case RLR:
				case LLL:
					autoChanger = AutoStates.LEFT_SCALE_LEFT;
					break;
				case RRR:
				case LRL:
					autoChanger = AutoStates.LEFT_SCALE_RIGHT;
					break;
				default:
					autoChanger = AutoStates.MIDDLE_CROSS_LINE;
					break;
				}
				break;
			default:
				autoChanger = AutoStates.DO_NOTHING;
				break;
			}
			break;
		case RIGHT:
			switch(currentPriority) {
			case SWITCH:
				switch(fieldConfiguration) {
				case RRR:
				case RLR:
					autoChanger = AutoStates.RIGHT_SWITCH;
					break;
				case LLL:
				case LRL:
					autoChanger = AutoStates.RIGHT_CROSS_LINE;
					break;
				default:
					autoChanger = AutoStates.MIDDLE_CROSS_LINE;
					break;
				}
				break;
			case SCALE:
				switch(fieldConfiguration) {
				case RLR:
				case LLL:
					autoChanger = AutoStates.RIGHT_SCALE_LEFT;
					break;
				case RRR:
				case LRL:
					autoChanger = AutoStates.RIGHT_SCALE_RIGHT;
					break;
				default:
					autoChanger = AutoStates.MIDDLE_CROSS_LINE;
					break;
				}
				break;
			default:
				autoChanger = AutoStates.DO_NOTHING;
				break;
			}
			break;
		default:
			autoChanger = AutoStates.DO_NOTHING;
			break;
		}
		
		switch(selectedAutoType) {
		case RECORDING:
			switch(autoChanger) {
			case MIDDLE_CROSS_LINE:
				fileName = "default";
				break;
			case MIDDLE_SWITCH_LEFT:
				fileName = "default";
				break;
			case MIDDLE_SWITCH_RIGHT:
				fileName = "default";
				break;
			case LEFT_CROSS_LINE:
				fileName = "default";
				break;
			case LEFT_SWITCH:
				fileName = "default";
				break;
			case LEFT_SCALE_LEFT:
				fileName = "default";
				break;
			case LEFT_SCALE_RIGHT:
				fileName = "default";
				break;
			case RIGHT_CROSS_LINE:
				fileName = "default";
				break;
			case RIGHT_SWITCH:
				fileName = "default";
				break;
			case RIGHT_SCALE_LEFT:
				fileName = "default";
				break;
			case RIGHT_SCALE_RIGHT:
				fileName = "default";
				break;
			case DO_NOTHING:
				fileName = "default";
				break;
			}
			runner.counterInitialize();
			try {
				List<State> states = StateReader.read("/home/lvuser/gsonFiles/" + fileName);
				runner.setStates(states); 
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case RECORDING_TEST:
			recordingAutoTester.getStates();
			break;
		case DEAD_RECKONING:
			deadReckoningAutos.reset();
			break;
		}
	}
	
	public void runAuto() {
		selectedAutoType = autoType.getSelected();
		
		switch(selectedAutoType) {
		case RECORDING:
			runner.run();
			break;
		case RECORDING_TEST:
			recordingAutoTester.runAuto();
			break;
		case DEAD_RECKONING:
			deadReckoningAutos.setEncoderValues();
			switch(autoChanger) {
			case MIDDLE_CROSS_LINE:
				deadReckoningAutos.middleCrossLine();
				break;
			case MIDDLE_SWITCH_LEFT:
				deadReckoningAutos.middleSwitchLeftBack();
				break;
			case MIDDLE_SWITCH_RIGHT:
				deadReckoningAutos.middleSwitchRightBack();
				break;
			case LEFT_CROSS_LINE:
				deadReckoningAutos.leftCrossLine();
				break;
			case LEFT_SWITCH:
				deadReckoningAutos.leftSwitch();
				break;
			case LEFT_SCALE_LEFT:
				deadReckoningAutos.leftCrossLine();
				break;
			case LEFT_SCALE_RIGHT:
				deadReckoningAutos.leftCrossLine();
				break;
			case RIGHT_CROSS_LINE:
				deadReckoningAutos.rightCrossLine();
				break;
			case RIGHT_SWITCH:
				deadReckoningAutos.rightSwitch();
				break;
			case RIGHT_SCALE_LEFT:
				deadReckoningAutos.rightCrossLine();
				break;
			case RIGHT_SCALE_RIGHT:
				deadReckoningAutos.rightCrossLine();
				break;
			case DO_NOTHING:
				break;
			}
			break;
		}
	}
}
