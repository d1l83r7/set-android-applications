package cat.imi.barcelona.tour.dao.helper;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class BCNTourTable {
	// Database creation SQL statement
	private static final String DATABASE_CREATE = "create table bcn_tourist_places "
			+ " (place_id integer primary key autoincrement, "
			+ " category integer not null, "
			+ " name text not null,"
			+ " description text not null,"
			+ " latitude text not null," 
			+ " longitude text not null);";
	
	private static final String DATA_BASE_CONFIG_CREATE = "create table bcn_config_table "
			+ "(config_id integer primary key autoincrement, "
			+ "description text not null, "
			+ "is_enabled text not null);";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);		
	}
	
	public static void onCreateConfig(SQLiteDatabase database){
		database.execSQL(DATA_BASE_CONFIG_CREATE);
	}
	

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(BCNTourTable.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS bcn_tourist_places");
		onCreate(database);
	}
	
	public static void onUpgradeConfig(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(BCNTourTable.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS bcn_config_table");
		onCreateConfig(database);
	}
}