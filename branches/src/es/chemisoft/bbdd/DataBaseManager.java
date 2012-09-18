package es.chemisoft.bbdd;

import android.content.Context;

public class DataBaseManager {
	private static DataBaseManager dbManager = null;
	private DBHelper dbHelper = null;
	
	public static DataBaseManager getIntance(Context c){
		if(dbManager==null){
			dbManager = new DataBaseManager(c);
		}
		return dbManager;
	}
	
	public DataBaseManager(Context c){
		dbHelper = new DBHelper(c);
	}
	
	public DBHelper getAccesoTablaAnyo(){
		return dbHelper;
	}
}
