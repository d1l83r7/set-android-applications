package es.chemisoft.listener;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import es.chemisoft.miscuentas.R;
import es.chemisoft.objetos.Anyo;

public class MyOnItemSelectedListener implements OnItemSelectedListener {
	public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
		if (parent.getId() == R.id.spinner) {
			int anyoId = ((Anyo) parent.getItemAtPosition(pos)).getId();
		}
		//Podemos hacer varios ifs o un switchs por si tenemos varios spinners.
	}
	public void onNothingSelected(AdapterView<?> parent) {
		// Do nothing.
	}
}
