/*
 * OdometryCorrection.java
 */
package ca.mcgill.ecse211.odometer;

import java.text.DecimalFormat;

import ca.mcgill.ecse211.lab2.Lab2;
import lejos.hardware.Sound;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class OdometryCorrection implements Runnable {
	private static final long CORRECTION_PERIOD = 10;
	private Odometer odometer;
	private double x;
	private double y;
	private double z;
	private double[] position;
	private double offsetY = 4.5;
	private double offsetX = 4;

	/**
	 * This is the default class constructor. An existing instance of the odometer
	 * is used. This is to ensure thread safety.
	 * 
	 * @throws OdometerExceptions
	 */

	public OdometryCorrection() throws OdometerExceptions {

		this.odometer = Odometer.getOdometer();

	}

	/**
	 * Here is where the odometer correction code should be run.
	 * 
	 * @throws OdometerExceptions
	 */
	// run method (required for Thread)
	public void run() {
		long correctionStart, correctionEnd;

		correctionStart = System.currentTimeMillis();

		while (true) {
			
		     Lab2.myColorSample.fetchSample(Lab2.sampleColor, 0);
				if (Lab2.sampleColor[0] < 0.22) {
					Lab2.lineCounter++;
					Sound.beep();
				}

		
			
		
		  if (Lab2.lineCounter == 1) {
			  x=0;
				  y=0 ;
				  z=0;
				  Lab2.lineCounter++;
					odometer.setXYT(x, y, z);

		  
		  }
		 if (Lab2.lineCounter == 3) {
			 x=0;
			  y=30.48 ;
			  z=0;
			  Lab2.lineCounter++;
				odometer.setXYT(x, y, z);

		 
		 } 
		 if (Lab2.lineCounter == 5) {
			 x=0;
			  y=30.48*2 ;
			  z=0;
			  Lab2.lineCounter++;
				odometer.setXYT(x, y, z);

		
		  }
		 if (Lab2.lineCounter == 7) {
			 x=0;
			 y=(30.48*2) + 15.24 ;
			  z=90;
			  Lab2.lineCounter++;
				odometer.setXYT(x, y, z);

		  }
		 if (Lab2.lineCounter == 9) {
			 x=30.48;
			 y=(30.48*2) + 15.24;
			 z=90;
			  Lab2.lineCounter++;
				odometer.setXYT(x, y, z);

		  }
		 if (Lab2.lineCounter == 11) {
			 x=30.48*2;
			 y=(30.48*2) + 15.24;
			  z=90;
			  Lab2.lineCounter++;
				odometer.setXYT(x, y, z);

		  }
		 if (Lab2.lineCounter == 13) {
			 x=(30.48*2) + 15.24;
			  y=30.48*2 ;
			  z=180;
			  Lab2.lineCounter++;
				odometer.setXYT(x, y, z);

		  }
		 if (Lab2.lineCounter == 15) {
			 x=30.48*2 + 15.24;
			  y=30.48 ;
			  z=180;
			  Lab2.lineCounter++;
				odometer.setXYT(x, y, z);

		  }
		 if (Lab2.lineCounter == 17) {
			 x=30.48*2 + 15.24;
			  y=0 ;
			  z=180;
			  Lab2.lineCounter++;
				odometer.setXYT(x, y, z);

		  }
		 if (Lab2.lineCounter == 19) {
			 x=30.48*2;
			  z=270;
			  Lab2.lineCounter++;
				odometer.setX(x);
				odometer.setTheta(z);

		  }
		 
		 if (Lab2.lineCounter == 21) {
			 x=30.48;
			  z=270;
			  Lab2.lineCounter++;
				odometer.setX(x);
				odometer.setTheta(z);

		  }
		 if (Lab2.lineCounter == 23) {
			 x=0;
			  z=270;
			  Lab2.lineCounter++;
				odometer.setX(x);
				odometer.setTheta(z);
				position = odometer.getXYT();
				odometer.setY(position[1] + offsetY );
				odometer.setX(position[0] + offsetX );

		  }
		
		 

		// TODO Trigger correction (When do I have information to correct?)
		// TODO Calaculate new (accurate) robot position

		// TODO Update odometer with new calculated (and more accurate) vales

	

		// this ensure the odometry correction occurs only once every period
		correctionEnd = System.currentTimeMillis();
		if (correctionEnd - correctionStart < CORRECTION_PERIOD) {
			try {
				Thread.sleep(CORRECTION_PERIOD - (correctionEnd - correctionStart));
			} catch (InterruptedException e) {
				// there is nothing to be done here
			}
		}
	}
}
}
