package es.chemisoft.bbdd;
 
import java.util.LinkedList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import es.chemisoft.objetos.Anyo;
 
public class AccesoTablaAnyo extends SQLiteOpenHelper {
 
	final String TABLE_NAME_ANYO = "ANYO";
	final String ID_ANYO = "id_anyo";
	final String DESC_ANYO = "desc_anyo";
	final String TOTAL_ANYO = "total_anyo";
	
    //Sentencia SQL para crear la tabla de Usuarios
    String sqlCreateAnyo = "CREATE TABLE "+TABLE_NAME_ANYO+" ("+ID_ANYO+" INTEGER, "+DESC_ANYO+" TEXT, "+TOTAL_ANYO+" Integer)";
 
    public AccesoTablaAnyo(Context contexto) {
        super(contexto, "ANYO", null, 1);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creación de la tabla
        db.execSQL(sqlCreateAnyo);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        //NOTA: Por simplicidad del ejemplo aquí utilizamos directamente la opción de
        //      eliminar la tabla anterior y crearla de nuevo vacía con el nuevo formato.
        //      Sin embargo lo normal será que haya que migrar datos de la tabla antigua
        //      a la nueva, por lo que este método debería ser más elaborado.
 
        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_ANYO);
 
        //Se crea la nueva versión de la tabla
        db.execSQL(sqlCreateAnyo);
    }
    
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