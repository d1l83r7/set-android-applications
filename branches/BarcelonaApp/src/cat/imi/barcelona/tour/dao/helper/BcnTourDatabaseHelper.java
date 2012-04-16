package cat.imi.barcelona.tour.dao.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BcnTourDatabaseHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "applicationdata";

	private static final int DATABASE_VERSION = 1;

	public BcnTourDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database){}
	
	// Method is called during creation of the database
	public void onCreateMap(SQLiteDatabase database) {
		BCNTourTable.onCreate(database);
	}
	
	public void onCreateConfig(SQLiteDatabase database){
		BCNTourTable.onCreateConfig(database);
	}

	// Method is called during an upgrade of the database,
	// e.g. if you increase the database version
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		BCNTourTable.onUpgrade(database, oldVersion, newVersion);
	}
	
	public void onUpgradeConfig(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		BCNTourTable.onUpgradeConfig(database, oldVersion, newVersion);
	}
}