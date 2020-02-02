package org.firstinspires.ftc.robotcontroller.external.samples;

import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.geometry.Vector2d;
import com.arcrobotics.ftclib.hardware.motors.MotorImplEx;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class MecanumDrivingSample extends LinearOpMode {

    private MecanumDrive driveTrain;

    // the motors used here are goBILDA Yellow Jackets, 435 rpm

    @Override
    public void runOpMode() throws InterruptedException {

        driveTrain = new MecanumDrive(
                new MotorImplEx(hardwareMap, "frontLeft", 383.6),
                new MotorImplEx(hardwareMap, "frontRight", 383.6),
                new MotorImplEx(hardwareMap, "backLeft", 383.6),
                new MotorImplEx(hardwareMap, "backRight", 383.6)
        );

        waitForStart();

        driveWithVector(new Vector2d(12,3));
        sleep(1000);
        driveWithVector(new Vector2d(0,0));

    }

    private void driveWithVector(Vector2d vector) {
        double[] speeds = normalize(new double[]{vector.getX(), vector.getY()});
        driveTrain.driveRobotCentric(speeds[0], speeds[1],0);
    }

    /**
     * Normalize the wheel speeds if any value is greater than 1
     */
    private double[] normalize(double[] wheelSpeeds) {
        double maxMagnitude = Math.abs(wheelSpeeds[0]);
        for (int i = 1; i < wheelSpeeds.length; i++) {
            double temp = Math.abs(wheelSpeeds[i]);
            if (maxMagnitude < temp) {
                maxMagnitude = temp;
            }
        }
        if (maxMagnitude > 1.0) {
            for (int i = 0; i < wheelSpeeds.length; i++) {
                wheelSpeeds[i] = wheelSpeeds[i] / maxMagnitude;
            }
        }

        return wheelSpeeds;
    }

}
