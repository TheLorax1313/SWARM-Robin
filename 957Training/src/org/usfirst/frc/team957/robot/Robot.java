
package org.usfirst.frc.team957.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

/*      */
// This is a comment

public class Robot extends IterativeRobot {
	//Defines the variables as members of our Robot class
    RobotDrive myRobot;
    Joystick controller;
    //Joystick rightStick;
    int autoLoopCounter;    
    final String defaultAuto = "Default";
    final String customAuto = "My Auto";
    String autoSelected;
    SendableChooser chooser;
    CANTalon frontLeft;
    CANTalon rearLeft;
    CANTalon frontRight;
    CANTalon rearRight;
    Talon shooter, feeder;
    int MaxLiftPower = 1;
    double currentPower = 0;
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	// Create and initialize joysticks
        controller = new Joystick(0);
        //rightStick = new Joystick(2);
        // Create and initialize Talons (number is the CAN bus ID and needs to match RoboRio configuration)
        frontLeft = new CANTalon(2);
        rearLeft = new CANTalon(3);
        frontRight = new CANTalon(0);
        rearRight = new CANTalon(1);
        shooter = new Talon(0);
        feeder = new Talon(1);
        
        // Create and initialize drive object for Robot. 
        myRobot = new RobotDrive(frontLeft, rearLeft, frontRight, rearRight);
        // Invert the motors on the left (may need to switch this to the right)
        myRobot.setInvertedMotor(MotorType.kFrontLeft, true);
        myRobot.setInvertedMotor(MotorType.kRearLeft, true);
       
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", defaultAuto);
        chooser.addObject("My Auto", customAuto);
        SmartDashboard.putData("Auto choices", chooser);  
    }
    
	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the switch structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
    public void autonomousInit() {
        autoLoopCounter = 0; //This resets the loop counter to 0
        autoSelected = (String) chooser.getSelected();
//		autoSelected = SmartDashboard.getString("Auto Selector", defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        switch(autoSelected) {
    	case customAuto:
        //Put custom auto code here   
            break;
    	case defaultAuto:
    	default:
    	//Put default auto code here
	        if(autoLoopCounter < 100) { //Checks to see if the counter has reached 100 yet
	            myRobot.drive(-0.5, 0.0);  //If the robot hasn't reached 100 packets yet, the robot is set to drive forward at half speed, the next line increments the counter by 1
	            autoLoopCounter++;
	       } else {
	            myRobot.drive(0.0, 0.0); //If the robot has reached 100 packets, this line tells the robot to stop
	       }
            break;
    	}
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        myRobot.tankDrive( controller.getRawAxis(1), (-1) * controller.getRawAxis(5));

		//This line drives the robot using the values of the joysticks and the motor controllers selected above
        // 3 is the right trigger
        int feederOn =  controller.getRawButton(2) ? 1 : 0;
        feeder.set(controller.getRawAxis(3));
        shooter.set(feederOn);
        SmartDashboard.putNumber("Trigger Value: ", controller.getRawAxis(3));
        
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	LiveWindow.run();
    }
    
}
