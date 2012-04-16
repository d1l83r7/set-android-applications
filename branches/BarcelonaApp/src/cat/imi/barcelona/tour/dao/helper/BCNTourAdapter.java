package cat.imi.barcelona.tour.dao.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.view.KeyCharacterMap;

public class BCNTourAdapter {

	// Database fields
	public static final String KEY_ROWID = "place_id";
	public static final String KEY_CATEGORY = "category";
	public static final String KEY_LATITUDE = "latitude";
	public static final String KEY_LONGITUDE = "longitude";
	public static final String KEY_NAME= "name";
	public static final String KEY_DESCRIPTION = "description";
	private static final String DB_TABLE = "bcn_tourist_places";
	private static final String DB_CONFIG_TABLE = "bcn_config_table";
	public static final String KEY_CONFIG_ROWID = "config_id";
	public static final String KEY_CONFIG_DESCRIPTION = "description";
	public static final String KEY_CONFIG_IS_ENABLED = "is_enabled";
	
	private Context context;
	private SQLiteDatabase db;
	private BcnTourDatabaseHelper dbHelper;

	public BCNTourAdapter(Context context) {
		this.context = context;
	}
	
	public void updateTable(){
		dbHelper.onUpgrade(db, 0, 1);
	}
	
	public void updateConfigTable(){
		dbHelper.onUpgradeConfig(db, 0, 1);
	}
	
	public void updateConfigTable(String description, String isEnabled){
		ContentValues values = createContentConfigValues(description,isEnabled);
		String sqlWhere = "description = '"+description+"'";
		db.update(DB_CONFIG_TABLE, values, sqlWhere,new String[0] );
	}

	public BCNTourAdapter open(boolean writable) throws SQLException {
		dbHelper = new BcnTourDatabaseHelper(context);
		if(writable)
			db = dbHelper.getWritableDatabase();
		else
			db = dbHelper.getReadableDatabase();
		return this;
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public long createTouristPlace(String category, String longitude, String latitude,String name, String description) {
		ContentValues values = createContentValues(category, longitude, latitude, name,
				description);
		return db.insert(DB_TABLE, null, values);
	}
	
	public long createConfig(String description) {
		ContentValues values = createContentConfigValues(description,"1");
		return db.insert(DB_CONFIG_TABLE, null, values);
	}
	
	public Cursor fetchAllTouristPlace() {
		return db.query(DB_TABLE, new String[] { KEY_ROWID, KEY_CATEGORY,
				KEY_LATITUDE,KEY_LONGITUDE,KEY_NAME, KEY_DESCRIPTION }, null, null, null, null, null, null);
	}
	
	public Cursor fetchAllTouristPlaceByCategory(String inClause) {
		String aux = KEY_CATEGORY + " "+ inClause;
		return db.query(DB_TABLE, new String[] { KEY_ROWID, KEY_CATEGORY,
				KEY_LATITUDE,KEY_LONGITUDE,KEY_NAME, KEY_DESCRIPTION }, aux, null, null, null, null, null);
	}
	
	public Cursor fetchAllConfig() {
		return db.query(DB_CONFIG_TABLE, new String[] { KEY_CONFIG_ROWID, KEY_CONFIG_DESCRIPTION,
				KEY_CONFIG_IS_ENABLED }, null, null, null, null, null, null);
	}

	public Cursor fetchOneTouristPlace(long rowId) throws SQLException {
		Cursor mCursor = db.query(true, DB_TABLE, new String[] { KEY_ROWID,
				KEY_CATEGORY, KEY_LATITUDE, KEY_LONGITUDE,KEY_NAME, KEY_DESCRIPTION }, KEY_ROWID + "="
				+ rowId, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	private ContentValues createContentValues(String category, String longitude, String latitude, String name,
			String description) {
		ContentValues values = new ContentValues();
		values.put(KEY_CATEGORY, category);
		values.put(KEY_LONGITUDE, longitude);
		values.put(KEY_LATITUDE, latitude);
		values.put(KEY_NAME, name);
		values.put(KEY_DESCRIPTION, description);
		return values;
	}
	
	private ContentValues createContentConfigValues(String description,String isEnabled) {
		ContentValues values = new ContentValues();
		values.put(KEY_CONFIG_DESCRIPTION, description);
		values.put(KEY_CONFIG_IS_ENABLED, isEnabled);
		return values;
	}
}