package cat.imi.barcelona.tour;
	 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import cat.imi.barcelona.tour.dao.helper.BCNTourAdapter;
import cat.imi.barcelona.tour.dao.helper.DataBaseManager;
import cat.imi.barcelona.tour.dto.TouristPlaceDTO;
import cat.imi.barcelona.tour.map.overlay.CustomItemizedOverlay;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.ItemizedOverlay.OnFocusChangeListener;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
 
public class BcnMapActivity extends MapActivity {	     
    private MapView mapView;
    private BCNTourAdapter dbHelper;
    private Map<String,String> items = new HashMap<String,String>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
         
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bcnmap);
        DataBaseManager dataBaseManager = DataBaseManager.getInstance(getBaseContext());
        dbHelper = dataBaseManager.getBcnTourAdapter();
        double[] res = operateMap();       
        dbHelper.open(false);
        List<String> lSelected = getSelectedItems();
        List<TouristPlaceDTO> l = obtainClosePlaces(res[0], res[1],lSelected);
        putTouristPlacessOnMap(l);
        dbHelper.close();
    }
    
    private List<String> getSelectedItems(){
    	List<String> lSelected = new ArrayList<String>();
    	Cursor c = dbHelper.fetchAllConfig();
    	c.moveToFirst();
    	for(int i=0;i<4;i++){
    		String descripcio = c.getString(1);
    		String isEnabled = c.getString(2);
    		if(descripcio.equals("comisarias")&&isEnabled.equals("1")){
        		lSelected.add("2");        		
        	}if(descripcio.equals("puntosinformacion")&&isEnabled.equals("1")){
        		lSelected.add("4");
        	}if(descripcio.equals("restaurantes")&&isEnabled.equals("1")){
        		lSelected.add("3");
        	}if(descripcio.equals("puntosturisticos")&&isEnabled.equals("1")){
        		lSelected.add("1");
        	}
    		c.moveToNext();
    	}
    	c.close();
    	return lSelected;
    	
    }
    
    private double[] operateMap(){
    	mapView = (MapView) findViewById(R.id.map_view);
        mapView.setBuiltInZoomControls(true);
        MapController mc = mapView.getController();
        Bundle extras = getIntent().getExtras(); 
        double[] res = new double[2];
        double lat = extras.getDouble("latitude");
        double lng = extras.getDouble("longitude");
        res[0]=lat;
        res[1]=lng;
        GeoPoint p = new GeoPoint(
            (int) (lat * 1E6), 
            (int) (lng * 1E6));
 
        mc.animateTo(p);
        mc.setZoom(12); 
        mapView.invalidate();
        return res;
    }
    
    private void putTouristPlacessOnMap(List<TouristPlaceDTO> l){
    	if(l.size()>0){
	    	List<Overlay> mapOverlays = mapView.getOverlays();	
	    	Drawable drawable = this.getResources().getDrawable(R.drawable.point);
	    	CustomItemizedOverlay itemizedOverlay = new CustomItemizedOverlay(drawable, this);
	        for(int i=0;i<l.size();i++){
		        TouristPlaceDTO dto = l.get(i);
		        double latitude = dto.getLatitude();
		        double longitude = dto.getLongitude();
		        GeoPoint point = new GeoPoint((int) (latitude * 1E6),(int) (longitude * 1E6));
		        OverlayItem overlayitem =
		             new OverlayItem(point, dto.getName(), dto.getName());
		        Log.w(this.getClass().getName(),dto.getName() + " longitude " + dto.getLongitude() + " latitude " + dto.getLatitude());
		        items.put(String.valueOf(point.getLongitudeE6())+String.valueOf(point.getLatitudeE6()), String.valueOf(dto.getPlaceId()));    
		        itemizedOverlay.addOverlay(overlayitem);
		        itemizedOverlay.setOnFocusChangeListener(new OnFocusChangeListener() {
					@Override
					public void onFocusChanged(ItemizedOverlay overlay,
							OverlayItem newFocus) {
						String key = String.valueOf(newFocus.getPoint().getLongitudeE6())+String.valueOf(newFocus.getPoint().getLatitudeE6());
						String placeId = items.get(key);
						Intent intent = new Intent(getBaseContext(),TouristPlaceActivity.class);
						intent.putExtra("placeId", placeId);
			    		startActivity(intent);
						
					}
				});
	        }
	        mapOverlays.add(itemizedOverlay);
    	}
    }
    
    
    public List<TouristPlaceDTO> obtainClosePlaces(double latitude, double longitude,List<String>lSelected){
    	List<TouristPlaceDTO> l = new ArrayList<TouristPlaceDTO>();
    	String inClause = "";
    	for(int i=0;i<lSelected.size();i++){
    		inClause += ","+lSelected.get(i);
    	}
    	Cursor c = null;
    	if(!inClause.equals("")){
	    	inClause = inClause.substring(1);
	    	inClause = "IN ("+inClause+")";
	    	c = dbHelper.fetchAllTouristPlaceByCategory(inClause);
    	}else {
    		c = dbHelper.fetchAllTouristPlace();
    	}
    	c.moveToFirst();
        while (c.isAfterLast() == false) {
            TouristPlaceDTO dto = constructObjectFromCursor(c);
            l.add(dto);
       	    c.moveToNext();
        }
        c.close();
    	return l;
    }
    
    private TouristPlaceDTO constructObjectFromCursor(Cursor c){
    	int placeId = c.getInt(0);
    	int category = c.getInt(1);
    	String lat = c.getString(2);
    	String lng = c.getString(3);
    	String name = c.getString(4);
    	String description = c.getString(5);
    	TouristPlaceDTO dto = new TouristPlaceDTO();
    	dto.setPlaceId(placeId);
    	dto.setName(name);
    	dto.setDescription(description);
    	dto.setLongitude(Double.parseDouble(lng));
    	dto.setLatitude(Double.parseDouble(lat));
    	dto.setCategory(category);
    	return dto;
    }
 
    @Override
	protected void onDestroy() {
		super.onDestroy();
		if (dbHelper != null) {
			dbHelper.close();
		}
	}
    
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
     
}