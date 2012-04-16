package es.innova_t.android.pepe.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import es.innova_t.android.pepe.R;
import es.innova_t.android.pepe.adapter.ContactAdapter;
import es.innova_t.android.pepe.dto.ContactDTO;

public class AndroidGalleryActivity extends Activity {
	
	private long selectedImage;
	private List<ImageView> lImage = new ArrayList<ImageView>();
	private List<ContactDTO> lDTO = new ArrayList<ContactDTO>();
	private final Logger log = Logger.getLogger(AndroidGalleryActivity.class);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		if(log.isDebugEnabled()){
			log.debug("-> onCreate()");
		}
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	    LinearLayout gallery = (LinearLayout) findViewById(R.id.gallery);
	    ContactAdapter contactAdapter = ContactAdapter.getInstance();
	    lDTO = contactAdapter.getlContact();
	    for(int i=0;i<lDTO.size();i++){
	    	LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(500, 700);
	    	LinearLayout grid = new LinearLayout(this);
	    	grid.setOrientation(LinearLayout.VERTICAL);
	    	grid.setGravity(Gravity.CENTER);
	    	ContactDTO cDTO = lDTO.get(i);
	    	ImageView iView = new ImageView(this);
	    	iView.setImageBitmap(cDTO.getContactImage());
	    	iView.setId(i);
	    	registerForContextMenu(iView);
	    	lImage.add(iView);
	    	grid.addView(iView);
	    	TextView name = new TextView(this);
	    	name.setText("Name: " + cDTO.getName());
	    	name.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
	    	grid.addView(name);
	    	TextView number = new TextView(this);
	    	String num = "";
	    	if(cDTO.getPhoneNumerDTO().size()>0){
	    		num = cDTO.getPhoneNumerDTO().get(0).getNumber();
	    	}
	    	number.setText("Number: "+ num);
	    	number.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
	    	grid.addView(number);
	    	gallery.addView(grid,p);
	    }
	    if(log.isDebugEnabled()){
			log.debug("<- onCreate()");
		}
	}
	
    private void call(String number) {
    	if(log.isDebugEnabled()){
			log.debug("-> call(number: "+number+")");
		}
        try {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+number));
            startActivity(callIntent);
        } catch (ActivityNotFoundException e) {
            Log.e("helloandroid dialing example", "Call failed", e);
        }
        if(log.isDebugEnabled()){
			log.debug("<- call()");
		}
    }
    
    private void sendSMS(String number){
    	if(log.isDebugEnabled()){
			log.debug("-> sendSMS(number: "+number+")");
		}
    	startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));
    	if(log.isDebugEnabled()){
			log.debug("<- sendSMS()");
		}
    }

	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	                                ContextMenuInfo menuInfo){
		if(log.isDebugEnabled()){
			log.debug("-> onCreateContextMenu(ContextMenu: "+ menu + ", View: " + 
					", ContextMenuInfo " + menuInfo+ ")");
		}
		selectedImage = v.getId();
		menuInfo = new AdapterView.AdapterContextMenuInfo(v,0,v.getId());
	    super.onCreateContextMenu(menu, v, menuInfo);
	 
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    if(log.isDebugEnabled()){
			log.debug("<- onCreateContextMenu()");
		}
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if(log.isDebugEnabled()){
			log.debug("-> onContextItemSelected(MenuItem: "+item+")");
		}
		ContactDTO contact = lDTO.get((int)selectedImage);
		String number = contact.getPhoneNumerDTO().get(0).getNumber();
		boolean option = true;
	    switch (item.getItemId()) {
	        case R.id.phone:
	            call(number);
	            option = true;
	        case R.id.sendSMS:
	        	sendSMS(number);
	            option=true;
	        default:
	            option = super.onContextItemSelected(item);
	    }
	    if(log.isDebugEnabled()){
			log.debug("<- onContextItemSelected(option: "+option+")");
		}
	    return option;
	}
}