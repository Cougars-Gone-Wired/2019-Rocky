package org.usfirst.frc.team2996.robot;

import com.kauailabs.navx.frc.*;

import edu.wpi.first.wpilibj.SPI;

public class IMU {

	// Create IMU
		public AHRS navX;

		// Cumulative turning heading
		public double cumulativeCommandedHeading = 0.0;

		public IMU() {
			try {
				// Initialize IMU
				navX = new AHRS(SPI.Port.kMXP);
			} catch (Exception e) {
				System.out.println("IMU failed to connect");
			}
		}

		public float getAngle() {
			return (float) navX.getAngle();
		}

		/**
		 * Use this to get the current heading
		 * 
		 * @return the degrees - is left + is right
		 */
		public float getHeading() {
			return navX.getYaw();
		}

		/**
		 * This resets the IMU
		 * 
		 */
		public void reset() {
			navX.reset();
		}
}
