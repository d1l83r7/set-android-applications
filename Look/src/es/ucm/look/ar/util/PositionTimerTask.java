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

import java.util.TimerTask;

import android.content.Context;
import android.view.Display;
import android.view.Surface;
import es.ucm.look.data.World;
import es.ucm.look.locationProvider.LocationProvider;

public class PositionTimerTask extends TimerTask {

	private LocationProvider locationProvider;
	private float factor;
	private Display display;
	private World world;
	private float[] lastPosition;

	public PositionTimerTask(Display d, Context c, World world) {
		locationProvider = new LocationProvider(c);
		display = d;
		this.world = world;
		factor = 2.0f;
	}

	@Override
	public void run() {
		locationProvider.run();
		float[] f = LocationProvider.getPosition();

		if (lastPosition != null) {
			float diffX = f[0] - lastPosition[0];
			float diffY = f[1] - lastPosition[1];

				synchronized (world.getLocation()) {
					int rotation = display.getRotation();
					switch (rotation) {
					case Surface.ROTATION_0:
						world.getLocation().add(diffX * factor, 0.0f, diffY * factor);
						break;
					case Surface.ROTATION_90:
						world.getLocation().add(diffY * factor, 0.0f, -diffX * factor);
						break;
					case Surface.ROTATION_270:
						world.getLocation().add(-diffY * factor, 0.0f, diffX * factor);
						break;

					}
				}

		}

		lastPosition = f;
	}
}
