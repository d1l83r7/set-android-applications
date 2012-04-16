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
package es.ucm.look.data.local;

import java.util.Date;
import java.util.List;

import android.content.Context;
import es.ucm.look.data.EntityData;
import es.ucm.look.data.LookData;
import es.ucm.look.data.interfaces.DataHandler;
import es.ucm.look.data.local.contentprovider.LookContentProvider;

/**
 * To have the persistence to a database, implements {@link DataHandler}
 * 
 * @author Sergio
 *
 */
public class DBDataHandler implements DataHandler {
	
	private LookContentProvider dataBase;
	
	public DBDataHandler( ){
		dataBase = LookContentProvider.getInstance();
	}
	
	public DBDataHandler( Context c ){
		dataBase = LookContentProvider.getInstance(c);
	}

	@Override
	public void addEntity(EntityData data) {
		dataBase.addEntity(data);
		
	}

	@Override
	public List<EntityData> getElementsUpdated(float x, float y, float z,
			float radius, Date date) {
		return dataBase.getElementsUpdated(x, y, z, radius, date);
	}

	@Override
	public void updatePosition(EntityData data, float x, float y, float z) {
		dataBase.updatePosition(data, x, y, z);	
	}

	@Override
	public void updateProperty(EntityData data, String property, String newValue) {
		dataBase.updateProperty(data, property, newValue);
		LookData.getInstance().updateData();
	}

}
