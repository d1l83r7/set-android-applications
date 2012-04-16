/*******************************************************************************
 * Look! is a Framework of Augmented Reality for Android. 
 * 
 * Copyright (C) 2011 
 * 		Sergio Bellón Alcarazo
 * 		Jorge Creixell Rojo
 * 		Ángel Serrano Laguna
 * 	
 * 	   Final Year Project developed to Sistemas Informáticos 2010/2011 - Facultad de Informática - Universidad Complutense de Madrid - Spain
 * 	
 * 	   Project led by: Jorge J. Gómez Sánz
 * 
 * 
 * ****************************************************************************
 * 
 * This file is part of Look! (http://lookar.sf.net/)
 * 
 * Look! is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/
 ******************************************************************************/
package es.ucm.look.ar.util;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * A class holding device's orientation
 * 
 * @author Ángel Serrano
 * 
 */
public class DeviceOrientation implements SensorEventListener {

	private static DeviceOrientation instance;

	private SensorManager mSM;

	private float[] inR = new float[16];
	private float[] outR = new float[16];
	private float[] I = new float[16];
	private float[] gravity = new float[3];
	private float[] geomag = new float[3];
	private float[] orientVals = new float[3];

	/**
	 * Device azimuth ( y coordinate )
	 */
	private float azimuth;
	
	private float azimuthOffset = 0.0f;

	/**
	 * Device pitch ( x coordinate )
	 */
	private float pitch;
	
	private float pitchOffset = 0.0f;

	/**
	 * Device roll ( z coordinate )
	 */
	private float roll;
	
	private float rollOffset = 0.0f;

	public float getAzimuth() {
		return azimuth;
	}

	public float getPitch() {
		return pitch;
	}

	public float getRoll() {
		return roll;
	}

	private DeviceOrientation(Context context) {
		mSM = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		mSM.registerListener(this, mSM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
		mSM.registerListener(this, mSM.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_GAME);
	}

	public static DeviceOrientation getDeviceOrientation(Context c) {
		if (instance == null)
			instance = new DeviceOrientation(c);
		return instance;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	/**
	 * Returns the matrix rotation for the current device orientation
	 * 
	 * @return the matrix rotation for the current device orientation
	 */
	public float[] getRotationMatrix() {
		return outR;
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE)
			return;

		switch (event.sensor.getType()) {
		case Sensor.TYPE_ACCELEROMETER:
			gravity = event.values.clone();
			break;
		case Sensor.TYPE_MAGNETIC_FIELD:
			geomag = event.values.clone();
			break;
		}

		if (gravity != null && geomag != null) {

			boolean success = SensorManager.getRotationMatrix(inR, I, gravity, geomag);
			if (success) {

				SensorManager.remapCoordinateSystem(inR, SensorManager.AXIS_X, SensorManager.AXIS_Z, outR);
				SensorManager.getOrientation(outR, orientVals);
				
				this.azimuth = orientVals[0] + azimuthOffset;
				this.pitch = orientVals[1] + pitchOffset;
				this.roll = orientVals[2] + rollOffset;

			}
		}
	}
	
	public void setAzimuthOffset(float azimuthOffset) {
		this.azimuthOffset = azimuthOffset;
	}

	public void setPitchOffset(float pitchOffset) {
		this.pitchOffset = pitchOffset;
	}

	public void setRollOffset(float rollOffset) {
		this.rollOffset = rollOffset;
	}
}
