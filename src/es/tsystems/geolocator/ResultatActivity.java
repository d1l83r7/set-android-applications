package es.tsystems.geolocator;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultatActivity extends Activity{
	
	private double defaultLongitude;
	private double defaultLatitude;
	private TextView distanceText;
	private TextView currentLocationText; 
	private TextView defaultLocationText;
	private double currentLongitude;
	private double currentLatitude;
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.resultat);
	        defaultLocationText = (TextView)findViewById(R.id.defaultLocation);		
			currentLocationText = (TextView)findViewById(R.id.currentLocation);
			distanceText = (TextView)findViewById(R.id.distance);
			
			Bundle extras = getIntent().getExtras(); 
	        currentLatitude = extras.getDouble("currentLatitude");
	        currentLongitude = extras.getDouble("currentLongitude");
	        defaultLatitude = extras.getDouble("defaultLatitude");
	        defaultLongitude = extras.getDouble("defaultLongitude");
			
			defaultLocationText.setText(String.valueOf(defaultLongitude)+", "+String.valueOf(defaultLatitude));
			currentLocationText.setText(String.valueOf(currentLongitude)+", "+String.valueOf(currentLatitude));
			Location defaultLocation = new Location(LocationManager.GPS_PROVIDER);
			defaultLocation.setLatitude(defaultLatitude);
			defaultLocation.setLongitude(defaultLongitude);
			
			Location currentLocation = new Location(LocationManager.GPS_PROVIDER);
			currentLocation.setLongitude(currentLongitude);
			currentLocation.setLatitude(currentLatitude);
			
			float meters = defaultLocation.distanceTo(currentLocation);  		
			
			distanceText.setText(String.valueOf(meters) + " meters");
			
			Button btViewMap = (Button)findViewById(R.id.btviewmap);
			btViewMap.setOnClickListener(new View.OnClickListener() {

	            public void onClick(View view) {
	                viewMap();
	            }
	          
	        });
	        
	    }
	 
	 private void viewMap(){
		 Intent i = new Intent(getBaseContext(),MapaActivity.class);
		 i.putExtra("defaultLatitude", defaultLatitude);
		 i.putExtra("defaultLongitude", defaultLongitude);
		 i.putExtra("currentLongitude", currentLongitude);
		 i.putExtra("currentLatitude", currentLatitude);
		 startActivity(i);
	 }

}
