package es.tsystems.geolocator;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

public class GeoLocatorActivity extends Activity implements Runnable {
    /** Called when the activity is first created. */
	
	private ProgressDialog pd;
	private LocationManager mLocationManager;
	private MyLocationListener mLocationListener;
	private Location currentLocation = null;
	private double currentLongitude;
	private double currentLatitude;
	private final double defaultLongitude = 2.173662;
	private final double defaultLatitude = 41.401536;

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button btsearch = (Button) findViewById(R.id.btsearch);
        btsearch.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                writeSignalGPS();
            }
          
        });
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
	    		currentLatitude = currentLocation.getLatitude();
	    		currentLongitude = currentLocation.getLongitude();
	    		viewMap();
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
    
    private void viewMap(){
		 Intent i = new Intent(getBaseContext(),ResultatActivity.class);
		 i.putExtra("defaultLatitude", defaultLatitude);
		 i.putExtra("defaultLongitude", defaultLongitude);
		 i.putExtra("currentLongitude", currentLongitude);
		 i.putExtra("currentLatitude", currentLatitude);
		 startActivity(i);
	 }
    
    private void setCurrentLocation(Location myLocation){
    	currentLocation = myLocation;
    }
    
    @Override
	protected void onDestroy() {
		super.onDestroy();
	 }
}