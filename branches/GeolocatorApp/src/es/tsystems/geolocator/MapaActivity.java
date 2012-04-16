package es.tsystems.geolocator;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import es.tsystems.geolocator.overlay.CustomItemizedOverlay;
import es.tsystems.geolocator.overlay.RouteOverlay;

public class MapaActivity extends MapActivity {
	private MapView mapView;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapa);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			double defaultLatitude = extras.getDouble("defaultLatitude");
			double defaultLongitude = extras.getDouble("defaultLongitude");
			double currentLongitude = extras.getDouble("currentLongitude");
			double currentLatitude = extras.getDouble("currentLatitude");

			operateMap(currentLongitude, currentLatitude);

			Location defaultLocation = new Location(
					LocationManager.GPS_PROVIDER);
			defaultLocation.setLatitude(defaultLatitude);
			defaultLocation.setLongitude(defaultLongitude);

			Location currentLocation = new Location(
					LocationManager.GPS_PROVIDER);
			currentLocation.setLatitude(currentLatitude);
			currentLocation.setLongitude(currentLongitude);

			List<Location> l = new ArrayList<Location>();
			l.add(defaultLocation);
			l.add(currentLocation);

			putPointsOnMap(l);

			traceRoute(currentLatitude, currentLongitude, defaultLatitude,
					defaultLongitude);
		}

	}

	private void traceRoute(double sourceLat, double sourceLong,
			double destinationLat, double destinationLong) {
		String pairs[] = getDirectionData(String.valueOf(sourceLat),
				String.valueOf(sourceLong), String.valueOf(destinationLat),
				String.valueOf(destinationLong));
		String[] lngLat = pairs[0].split(",");

		// STARTING POINT
		GeoPoint startGP = new GeoPoint(
				(int) (Double.parseDouble(lngLat[1]) * 1E6),
				(int) (Double.parseDouble(lngLat[0]) * 1E6));

		MapController myMC = mapView.getController();

		myMC.setCenter(startGP);
		myMC.setZoom(10);
		mapView.getOverlays().add(new RouteOverlay(startGP, startGP));

		// NAVIGATE THE PATH

		GeoPoint gp1;
		GeoPoint gp2 = startGP;

		for (int i = 1; i < pairs.length; i++) {
			lngLat = pairs[i].split(",");
			gp1 = gp2;

			gp2 = new GeoPoint((int) (Double.parseDouble(lngLat[1]) * 1E6),
					(int) (Double.parseDouble(lngLat[0]) * 1E6));
			mapView.getOverlays().add(new RouteOverlay(gp1, gp2));
			Log.d("xxx", "pair:" + pairs[i]);

			mapView.getOverlays().add(new RouteOverlay(gp2, gp2));

			mapView.getController().animateTo(startGP);
			mapView.setBuiltInZoomControls(true);
			mapView.displayZoomControls(true);

		}
	}

	private void operateMap(double longitude, double latitude) {
		mapView = (MapView) findViewById(R.id.map_view);
		mapView.setBuiltInZoomControls(true);
		MapController mc = mapView.getController();
		GeoPoint p = new GeoPoint((int) (latitude * 1E6),
				(int) (longitude * 1E6));

		mc.animateTo(p);
		mc.setZoom(12);
		mapView.invalidate();
	}

	private void putPointsOnMap(List<Location> l) {
		if (l.size() > 0) {
			List<Overlay> mapOverlays = mapView.getOverlays();
			Drawable currentPoint = this.getResources().getDrawable(
					R.drawable.currentpoint);
			CustomItemizedOverlay itemizedOverlayCurrent = new CustomItemizedOverlay(
					currentPoint, this);
			Drawable destinationPoint = this.getResources().getDrawable(
					R.drawable.destinationpoint);
			CustomItemizedOverlay itemizedOverlayDestination = new CustomItemizedOverlay(
					destinationPoint, this);

			Location currentPosition = l.get(0);
			double latitude = currentPosition.getLatitude();
			double longitude = currentPosition.getLongitude();
			GeoPoint point = new GeoPoint((int) (latitude * 1E6),
					(int) (longitude * 1E6));
			OverlayItem overlayitem = new OverlayItem(point,
					"Current position", "Current position");
			Log.w(this.getClass().getName(), "Current position" + " longitude "
					+ currentPosition.getLongitude() + " latitude "
					+ currentPosition.getLatitude());
			itemizedOverlayCurrent.addOverlay(overlayitem);

			Location destination = l.get(1);
			latitude = destination.getLatitude();
			longitude = destination.getLongitude();
			GeoPoint point2 = new GeoPoint((int) (latitude * 1E6),
					(int) (longitude * 1E6));
			OverlayItem overlayitem2 = new OverlayItem(point2, "Destination",
					"Destination");
			Log.w(this.getClass().getName(),
					"Destination" + " longitude " + destination.getLongitude()
							+ " latitude " + destination.getLatitude());
			itemizedOverlayDestination.addOverlay(overlayitem2);

			// RouteOverlay routeOverlay = new RouteOverlay(point,point2);

			mapOverlays.add(itemizedOverlayCurrent);
			mapOverlays.add(itemizedOverlayDestination);
			// mapOverlays.add(routeOverlay);
		}
	}

	private String[] getDirectionData(String sourceLat, String sourceLong,
			String destinationLat, String destinationLong) {

		String urlString = "http://maps.google.com/maps?f=d&hl=en&" + "saddr="
				+ sourceLat + "," + sourceLong + "&daddr=" + destinationLat
				+ "," + destinationLong + "&ie=UTF8&0&om=0&output=kml";
		Log.d("URL", urlString);
		Document doc = null;
		HttpURLConnection urlConnection = null;
		URL url = null;
		String pathConent = "";

		try {

			url = new URL(urlString.toString());
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setDoOutput(true);
			urlConnection.setDoInput(true);
			urlConnection.connect();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(urlConnection.getInputStream());

		} catch (Exception e) {
		}

		NodeList nl = doc.getElementsByTagName("LineString");
		for (int s = 0; s < nl.getLength(); s++) {
			Node rootNode = nl.item(s);
			NodeList configItems = rootNode.getChildNodes();
			for (int x = 0; x < configItems.getLength(); x++) {
				Node lineStringNode = configItems.item(x);
				NodeList path = lineStringNode.getChildNodes();
				pathConent = path.item(0).getNodeValue();
			}
		}
		String[] tempContent = pathConent.split(" ");
		return tempContent;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected boolean isRouteDisplayed() {
		return true;
	}
}