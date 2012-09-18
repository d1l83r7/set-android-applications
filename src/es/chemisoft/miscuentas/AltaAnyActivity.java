package es.chemisoft.miscuentas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import es.chemisoft.bbdd.DBHelper;
import es.chemisoft.bbdd.DataBaseManager;

public class AltaAnyActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alta_any);
        
        Button button = (Button)findViewById(R.id.btnCrearAnyo);
        
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	EditText editText = (EditText)findViewById(R.id.descAnyo);
            	String text = editText.getText().toString();
            	
            	DataBaseManager dbManager = DataBaseManager.getIntance(getBaseContext());
            	DBHelper ata = dbManager.getAccesoTablaAnyo();
            	ata.onInsertAnyo(text);
            	
            	Intent i = new Intent(getBaseContext(),MainActivity.class);
            	startActivity(i);
            }
        });
	}
}
