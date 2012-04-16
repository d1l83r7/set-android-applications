package cat.imi.barcelona.tour;

import java.io.IOException;
import java.sql.DatabaseMetaData;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import cat.imi.barcelona.tour.dao.helper.BCNTourAdapter;
import cat.imi.barcelona.tour.dao.helper.DataBaseManager;
import cat.imi.barcelona.tour.dto.TouristPlaceDTO;

public class TouristPlaceActivity extends Activity {
	private BCNTourAdapter dbHelper;
	private TextView name;
	private TextView description;
	private ImageView image;
	private MediaPlayer mediaPlayer;
	Button btPlay;
	@Override
	 public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.placefile);
        Bundle bundle = getIntent().getExtras();
        String placeIdStr = bundle.getString("placeId");
        long placeId = Long.parseLong(placeIdStr);        
        TouristPlaceDTO dto = getTouristPlace(placeId);
        name = (TextView) findViewById(R.id.name);
		description = (TextView) findViewById(R.id.description);
		image = (ImageView)findViewById(R.id.image);
		name.setText(dto.getName());
		description.setText(dto.getDescription());
		btPlay = (Button)findViewById(R.id.btPlay);
		if(mediaPlayer == null){
			mediaPlayer = new MediaPlayer();
			try{
			mediaPlayer.setDataSource(getBaseContext(), Uri.parse("file://c:/android/a.mp3"));
			mediaPlayer.prepare();
			}catch(IOException ioe){}
		}
		if(dto.getPlaceId()==1){
			image.setBackgroundResource(R.drawable.sagradafamilia);	
		}else if(dto.getPlaceId()==2){
			image.setBackgroundResource(R.drawable.parcguell);
		}else if(dto.getPlaceId()==3){
			image.setBackgroundResource(R.drawable.torreagbar);
		}
		btPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	mediaPlayer.start();
            }
        });
		
         
    }
	public TouristPlaceDTO getTouristPlace(long placeId){
		DataBaseManager dataBaseManager = DataBaseManager.getInstance(getBaseContext());
		dbHelper = dataBaseManager.getBcnTourAdapter();
		dbHelper.open(false);
    	Cursor c = dbHelper.fetchOneTouristPlace(placeId);
    	TouristPlaceDTO dto = constructObjectFromCursor(c);
    	dbHelper.close();
    	return dto;
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
}
