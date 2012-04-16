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
package es.ucm.look.ar.math.collision.debug;

import javax.microedition.khronos.opengles.GL10;

import es.ucm.look.ar.ar3D.core.drawables.primitives.CirclePrimitive;
import es.ucm.look.ar.ar3D.core.drawables.primitives.PointPrimitive;
import es.ucm.look.ar.math.collision.SphericalArmature;
import es.ucm.look.ar.math.geom.Point3;
import es.ucm.look.ar.math.geom.Ray;


public class SphericalDebugArmature extends SphericalArmature implements DebugArmature {
	
	private CirclePrimitive c;
	
	private PointPrimitive p;
	
	public SphericalDebugArmature(Point3 center, float radius) {
		super(center, radius);
		c = new CirclePrimitive(center, radius, 20);
		p = new PointPrimitive(new Point3(0,0,0) );
	}
	
	private float ang = 0;
	@Override
	public void debugDraw(GL10 gl) {
		gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
		gl.glPushMatrix();
		gl.glRotatef(ang++, 1, 1, 1);
		c.draw(gl);
		gl.glPopMatrix();
		p.draw(gl);
	}
	
	public Point3 getIntersectionPoint(Ray r) {
		Point3 p = super.getIntersectionPoint(r);
		if ( p != null ){
			this.p.setPoint(p);
		}
		return p;
	}
}
