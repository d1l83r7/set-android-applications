package cat.imi.barcelona.tour;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import cat.imi.barcelona.tour.dao.helper.BCNTourAdapter;
import cat.imi.barcelona.tour.dao.helper.DataBaseManager;

public class BcnConfigActivity extends Activity {
	
	private CheckBox puntosTuristicos;
	private CheckBox restaurantes;
	private CheckBox comisarias;
	private CheckBox puntosInfo;
	private BCNTourAdapter bcnTourAdapter;
	
	
	
	@Override
    public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.configure);
		DataBaseManager dbManager = DataBaseManager.getInstance(getBaseContext());
		bcnTourAdapter = dbManager.getBcnTourAdapter();
		comisarias = (CheckBox)findViewById(R.id.police);
		puntosInfo = (CheckBox)findViewById(R.id.infopoint);
		puntosTuristicos = (CheckBox)findViewById(R.id.touristpoint);
		restaurantes = (CheckBox)findViewById(R.id.restaurantes);
		bcnTourAdapter.open(false);
		Cursor c = bcnTourAdapter.fetchAllConfig();
		setCheckedValues(c);
		createEvents();
		bcnTourAdapter.close();
		
	}
	
	private void setCheckedValues(Cursor c){
		for(int i=0;i<4;i++){
			c.moveToNext();
			String description = c.getString(1);
			String isEnabled = c.getString(2);
			if(description.equals("comisarias")){
				if(isEnabled.equals("0"))
					comisarias.setChecked(false);
				else
					comisarias.setChecked(true);
			}else if(description.equals("puntosinformacion")){
				if(isEnabled.equals("0"))
					puntosInfo.setChecked(false);
				else
					puntosInfo.setChecked(true);
			}else if(description.equals("restaurantes")){
				if(isEnabled.equals("0"))
					restaurantes.setChecked(false);
				else
					restaurantes.setChecked(true);
			}else if(description.equals("puntosturisticos")){
				if(isEnabled.equals("0"))
					puntosTuristicos.setChecked(false);
				else
					puntosTuristicos.setChecked(true);
			}
		}
	}
	
	private void createEvents(){
		comisarias.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				String description = "comisarias";
				String isEnabled = null;
				if(isChecked){
					isEnabled = "1";
				}else{
					isEnabled = "0";
				}
				bcnTourAdapter.open(true);
				bcnTourAdapter.updateConfigTable(description, isEnabled);
				bcnTourAdapter.close();
			}
		});
		puntosInfo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				String description = "puntosinformacion";
				String isEnabled = null;
				if(isChecked){
					isEnabled = "1";
				}else{
					isEnabled = "0";
				}
				bcnTourAdapter.open(true);
				bcnTourAdapter.updateConfigTable(description, isEnabled);
				bcnTourAdapter.close();
			}
		});
		restaurantes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				String description = "restaurantes";
				String isEnabled = null;
				if(isChecked){
					isEnabled = "1";
				}else{
					isEnabled = "0";
				}
				bcnTourAdapter.open(true);
				bcnTourAdapter.updateConfigTable(description, isEnabled);
				bcnTourAdapter.close();
			}
		});
		puntosTuristicos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				String description = "puntosturisticos";
				String isEnabled = null;
				if(isChecked){
					isEnabled = "1";
				}else{
					isEnabled = "0";
				}
				bcnTourAdapter.open(true);
				bcnTourAdapter.updateConfigTable(description, isEnabled);
				bcnTourAdapter.close();
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (bcnTourAdapter != null) {
			bcnTourAdapter.close();
		}
	}
}
