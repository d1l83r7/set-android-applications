package cat.imi.barcelona.tour;

import cat.imi.barcelona.tour.dao.helper.BCNTourAdapter;
import cat.imi.barcelona.tour.dao.helper.DataBaseManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class BarcelonaTourAppActivity extends Activity implements Runnable  {
	  
    private ProgressDialog pd;
    
	private LocationManager mLocationManager;
	private Location mLocation;
	private MyLocationListener mLocationListener;
	private Location currentLocation = null;
	private BCNTourAdapter bcnTourAdapter;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        createBBDD();
		Button btsearch = (Button) findViewById(R.id.btsearch);
		DataBaseManager dataBaseManager = DataBaseManager.getInstance(getBaseContext());
		bcnTourAdapter = dataBaseManager.getBcnTourAdapter();
		btsearch.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                writeSignalGPS();
            }
          
        });
		
		Button btConfig = (Button) findViewById(R.id.btConfig);
		btConfig.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getBaseContext(),BcnConfigActivity.class);
				startActivity(i);				
			}
		});
        
    }
	
	private void createBBDD(){
		if(bcnTourAdapter==null){
			bcnTourAdapter = new BCNTourAdapter(getBaseContext());
			try{
				bcnTourAdapter.open(true);
				bcnTourAdapter.updateConfigTable();
				bcnTourAdapter.createConfig("comisarias");
				bcnTourAdapter.createConfig("puntosinformacion");
				bcnTourAdapter.createConfig("restaurantes");
				bcnTourAdapter.createConfig("puntosturisticos");
				bcnTourAdapter.updateTable();
				bcnTourAdapter.createTouristPlace("1", "2.175931", "41.404344", "Sagrada Familia","El Templo Expiatorio de la Sagrada Familia (en catalán Temple Expiatori de la Sagrada Família), conocido simplemente como la Sagrada Familia (Sagrada Família), es una gran basílica católica de Barcelona (España), diseñada por el arquitecto catalán Antoni Gaudí. Iniciada en 1882, todavía está en construcción (noviembre de 2011). Es la obra maestra de Gaudí, y el máximo exponente de la arquitectura modernista catalana.");
				bcnTourAdapter.createTouristPlace("1", "2.152162", "41.414765", "Parc Güell","El Parque Güell (Park Güell en su denominación original) es un gran jardín con elementos arquitectónicos situado en la parte superior de la ciudad de Barcelona (España), en la vertiente que mira al mar de la montaña del Carmelo, no muy lejos del Tibidabo. Pertenece al barrio de La Salud, en el distrito de Gracia. Ideado como urbanización, fue diseñado por el arquitecto Antoni Gaudí, máximo exponente del modernismo catalán, por encargo del empresario Eusebi Güell. Construido entre 1900 y 1914, fue inaugurado como parque público en 1922. En 1984 la Unesco incluyó al Parque Güell dentro del Lugar Patrimonio de la Humanidad «Obras de Antoni Gaudí».");
				bcnTourAdapter.createTouristPlace("1", "2.189434", "41.404223", "Torre Agbar", "La torre Agbar es un rascacielos de Barcelona (España) ubicado en la confluencia de la avenida Diagonal y la calle Badajoz junto a la plaza de las Glorias y que marca la puerta de entrada al distrito tecnológico de Barcelona conocido como 22@. Tiene 34 plantas sobre la superficie además de cuatro plantas subterráneas para un total de 145 metros de altura, convirtiéndose, en el momento de su apertura (junio de 2005) en el tercer edificio más alto de la capital catalana, sólo superado por el Hotel Arts y la Torre Mapfre (ambos con 154 metros de altura).");
				bcnTourAdapter.createTouristPlace("2", "2.158813", "41.417114", "Comisaria de horta", "Comisaria de horta");
				bcnTourAdapter.createTouristPlace("3", "2.199755", "41.408746", "Restaurante Broqueta", "Restaurante broqueta");
				bcnTourAdapter.createTouristPlace("4", "2.159500", "41.399604", "Agència Catalana de Turisme", "Agència Catalana de Turisme");
				bcnTourAdapter.close();
			}catch(SQLiteException sqle){}
		}
	}
    
    private void setCurrentLocation(Location loc) {
    	currentLocation = loc;
    }
    
    private void writeSignalGPS() {
    	DialogInterface.OnCancelListener dialogCancel = new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(getBaseContext(), 
                        getResources().getString(R.string.gps_signal_not_found), 
                        Toast.LENGTH_LONG).show();
                handler.sendEmptyMessage(0);
            }
        };
		pd = ProgressDialog.show(this, this.getResources().getString(R.string.search), 
				this.getResources().getString(R.string.search_signal_gps), true, true, dialogCancel);
		Thread thread = new Thread(this);
		thread.start();
    }

	@Override
	public void run() {
		mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		boolean gpsEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if (gpsEnabled) {
			Looper.prepare();
			mLocationListener = new MyLocationListener();
			mLocationManager.requestLocationUpdates(
	                LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
			Looper.loop(); 
			Looper.myLooper().quit(); 
		} else if(mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			mLocationListener = new MyLocationListener();
			mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,0,mLocationListener);
			Looper.loop();
			Looper.myLooper().quit();
		} else{
			Toast.makeText(getBaseContext(), 
                  getResources().getString(R.string.gps_signal_not_found), 
                  Toast.LENGTH_LONG).show();
		}
	}
    
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			pd.dismiss();
			mLocationManager.removeUpdates(mLocationListener);
	    	if (currentLocation!=null) {
	    		double latitude = currentLocation.getLatitude();
	    		double longitude = currentLocation.getLongitude();
	    		Intent intent = new Intent(getBaseContext(),BcnMapActivity.class);
	    		intent.putExtra("latitude", latitude);
	    		intent.putExtra("longitude", longitude);
	    		startActivity(intent);
	    	}
		}
	};
	
    private class MyLocationListener implements LocationListener{
        @Override
        public void onLocationChanged(Location loc) {
            if (loc != null) {
                Toast.makeText(getBaseContext(), 
                    getResources().getString(R.string.gps_signal_found), 
                    Toast.LENGTH_LONG).show();
                setCurrentLocation(loc);
                handler.sendEmptyMessage(0);
            }
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, 
            Bundle extras) {
        }
    }
    
    @Override
	protected void onDestroy() {
		super.onDestroy();
		if (bcnTourAdapter != null) {
			bcnTourAdapter.close();
		}
	 }
}