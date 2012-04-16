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
package es.ucm.look.data.local.contentprovider.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import es.ucm.look.data.local.contentprovider.LookContentProvider;
import es.ucm.look.data.remote.LookProperties;

/**
 * It class help to {@link LookContentProvider}, it has the Database Version,
 * the Tables Names and it creates a new Database SQLite on the device if it not
 * exits
 * 
 * @author Sergio
 * 
 */
public class LookSQLHelper extends SQLiteOpenHelper {

	public static final int DATABASE_VERSION = 1;

	public static final String MAIN_TABLENAME = "mainTable";
	public static final String PROPERTIES_TABLENAME = "propertiesTable";

	public static final String MAIN_PROJECTION_ALL_FIELDS[] = new String[] {
			LookProperties.FIELD_ID, LookProperties.FIELD_POS_X,
			LookProperties.FIELD_POS_Y, LookProperties.FIELD_POS_Z,
			LookProperties.FIELD_LAST_UPDATE };

	public static final String PROPERTIES_PROJECTION_ALL_FIELDS[] = new String[] {
			LookProperties.FIELD_ID, LookProperties.FIELD_PROPERTY,
			LookProperties.FIELD_VALUE };

	public static final String PROPERTIES_PROJECTION_ID[] = new String[] { LookProperties.FIELD_ID };

	public LookSQLHelper(Context context, String databaseName) {
		super(context, databaseName, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		/*
		 * Esto se ejecutara solo si se va a crear la base de datos. Si solo se
		 * va a acceder a ella no se ejecutara
		 */

		db.execSQL("CREATE TABLE " + MAIN_TABLENAME + " ("
				+ LookProperties.FIELD_ID + " INTEGER PRIMARY KEY,"
				+ LookProperties.FIELD_POS_X + " REAL,"
				+ LookProperties.FIELD_POS_Y + " REAL,"
				+ LookProperties.FIELD_POS_Z + " REAL,"
				+ LookProperties.FIELD_LAST_UPDATE + " DATE)");

		db.execSQL("CREATE TABLE " + PROPERTIES_TABLENAME + " ("
				+ LookProperties.FIELD_ID + " INTEGER,"
				+ LookProperties.FIELD_PROPERTY + " TEXT,"
				+ LookProperties.FIELD_VALUE + " TEXT," + "PRIMARY KEY("
				+ LookProperties.FIELD_ID + "," + LookProperties.FIELD_PROPERTY
				+ "))");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {

	}

	@Override
	public void onOpen(SQLiteDatabase db) {

	}

}
