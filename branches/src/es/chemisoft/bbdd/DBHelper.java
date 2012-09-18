package es.chemisoft.bbdd;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;

import es.chemisoft.objetos.Anyo;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{
	 
	final String TABLE_NAME_ANYO = "ANYO";
	final String ID_ANYO = "_id_anyo";
	final String DESC_ANYO = "desc_anyo";
	final String TOTAL_ANYO = "total_anyo";
	
	//Ruta por defecto de las bases de datos en el sistema Android
//	private static String DB_PATH = "/data/data/es.chemisoft.miscuentas/databases/";
//	private static String DB_PATH = "C:\\Documents and Settings\\Jrodrig5\\workspace\\MisCuentas\\assets\\";
	private static String DB_PATH = "C:\\";
	 
	private static String DB_NAME = "database.db";
	 
	private SQLiteDatabase myDataBase;
	 
	private final Context myContext;
	 
	/**
	* Constructor
	* Toma referencia hacia el contexto de la aplicación que lo invoca para poder acceder a los 'assets' y 'resources' de la aplicación.
	* Crea un objeto DBOpenHelper que nos permitirá controlar la apertura de la base de datos.
	* @param context
	*/
	public DBHelper(Context context) {
	 
	super(context, DB_NAME, null, 1);
	this.myContext = context;
	 
	}
	 
	/**
	* Crea una base de datos vacía en el sistema y la reescribe con nuestro fichero de base de datos.
	* */
	public void createDataBase() throws IOException{
	 
	boolean dbExist = checkDataBase();
	 
	if(dbExist){
	//la base de datos existe y no hacemos nada.
	}else{
	//Llamando a este método se crea la base de datos vacía en la ruta por defecto del sistema
	//de nuestra aplicación por lo que podremos sobreescribirla con nuestra base de datos.
	this.getReadableDatabase();
	 
	try {
	 
	copyDataBase();
	 
	} catch (IOException e) {
	throw new Error("Error copiando Base de Datos");
	}
	}
	 
	}
	 
	/**
	* Comprueba si la base de datos existe para evitar copiar siempre el fichero cada vez que se abra la aplicación.
	* @return true si existe, false si no existe
	*/
	private boolean checkDataBase(){
	 
	SQLiteDatabase checkDB = null;
	 
	try{
	 
	String myPath = DB_PATH + DB_NAME;
	checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
	 
	}catch(SQLiteException e){
	 
	//si llegamos aqui es porque la base de datos no existe todavía.
	 
	}
	if(checkDB != null){
	 
	checkDB.close();
	 
	}
	return checkDB != null ? true : false;
	}
	 
	/**
	* Copia nuestra base de datos desde la carpeta assets a la recién creada
	* base de datos en la carpeta de sistema, desde dónde podremos acceder a ella.
	* Esto se hace con bytestream.
	* */
	private void copyDataBase() throws IOException{
	 
	//Abrimos el fichero de base de datos como entrada
	InputStream myInput = myContext.getAssets().open(DB_NAME);
	 
	//Ruta a la base de datos vacía recién creada
//	String outFileName = DB_PATH + DB_NAME;
	String outFileName = DB_PATH + "a.db";
	 
	//Abrimos la base de datos vacía como salida
	OutputStream myOutput = new FileOutputStream(outFileName);
	 
	//Transferimos los bytes desde el fichero de entrada al de salida
	byte[] buffer = new byte[1024];
	int length;
	while ((length = myInput.read(buffer))>0){
	myOutput.write(buffer, 0, length);
	}
	 
	//Liberamos los streams
	myOutput.flush();
	myOutput.close();
	myInput.close();
	 
	}
	 
	public void open() throws SQLException{
	 
	//Abre la base de datos
	try {
	createDataBase();
	} catch (IOException e) {
	throw new Error("Ha sido imposible crear la Base de Datos");
	}
	 
	String myPath = DB_PATH + DB_NAME;
	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
	 
	}
	 
	@Override
	public synchronized void close() {
	if(myDataBase != null)
	myDataBase.close();
	super.close();
	}
	 
	@Override
	public void onCreate(SQLiteDatabase db) {
	 
	}
	 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	 
	}
	/**
	* A continuación se crearán los métodos de lectura, inserción, actualización
	* y borrado de la base de datos.
	* */
	
	public void onInsertAnyo(String descAnyo){
    	SQLiteDatabase db = this.getReadableDatabase();    	
    	int idAnyo = getLastId();
    	int totalAnyo = 0;
    	String sql =   "INSERT INTO ANYO ("+ID_ANYO+", "+DESC_ANYO+", "+TOTAL_ANYO+") " +
    	        "VALUES (" + idAnyo + ", " + descAnyo + ", "+ totalAnyo +")";
    	db.rawQuery(sql, null);
    }
    
    private int getLastId(){
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor cursor = db.query(TABLE_NAME_ANYO, new String[] {ID_ANYO}, 
              null, null, null, null, ID_ANYO + " DESC");
    	
    	int index = cursor.getColumnIndex(ID_ANYO);
    	int lastId = 0;
    	if(index == -1){
    		 lastId = cursor.getInt(index);
    	}
    	
    	return lastId+1;
    }
    
    public LinkedList<Anyo> onSelectAnyo(){
    	LinkedList<Anyo> anyos = new LinkedList<Anyo>();
    	SQLiteDatabase db = this.getReadableDatabase();
//        String select = "Select "+ID_ANYO+", "+DESC_ANYO+", "+TOTAL_ANYO+" from "+TABLE_NAME_ANYO;        
        Cursor cursor = db.query(TABLE_NAME_ANYO, new String[] {ID_ANYO, DESC_ANYO, TOTAL_ANYO}, 
                null, null, null, null, null);
        
        cursor.moveToFirst();
        while(cursor.isAfterLast()==false){
        	int index1 = cursor.getColumnIndex(ID_ANYO);
        	int index2 = cursor.getColumnIndex(DESC_ANYO);
        	int index3 = cursor.getColumnIndex(TOTAL_ANYO);
        	
        	int idAnyo = cursor.getInt(index1);
        	String descAnyo = cursor.getString(index2);
        	int totalAnyo = cursor.getInt(index3);
        	
        	Anyo anyo = new Anyo(idAnyo,descAnyo,totalAnyo);
        	anyos.add(anyo);
        }
        return anyos;
    }
}