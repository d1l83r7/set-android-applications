package es.chemisoft.miscuentas;

import java.util.LinkedList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import es.chemisoft.bbdd.DBHelper;
import es.chemisoft.bbdd.DataBaseManager;
import es.chemisoft.objetos.Anyo;

public class MainActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
      //Creamos la lista
        DataBaseManager dbManager = DataBaseManager.getIntance(getBaseContext());
    	DBHelper ata = dbManager.getAccesoTablaAnyo();
        ata.open();
        LinkedList<Anyo> anyos = ata.onSelectAnyo();
        //Creamos el adaptador
        ArrayAdapter<Anyo> spinner_adapter = new ArrayAdapter<Anyo>(this, android.R.layout.simple_spinner_item, anyos);
        spinner_adapter.areAllItemsEnabled();
        //Añadimos el layout para el menú y se lo damos al spinner
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinner_adapter);
        spinner.setEnabled(true);
        
        Button btAlta = (Button)findViewById(R.id.btnAlta);
        btAlta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent i = new Intent(getBaseContext(), AltaAnyActivity.class);
                startActivity(i);
            }
        });
        		
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_main, menu);
//        return true;
//    }
}
