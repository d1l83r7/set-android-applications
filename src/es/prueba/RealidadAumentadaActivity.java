package es.prueba;

import android.os.Bundle;
import es.ucm.look.ar.LookAR;
import es.ucm.look.data.EntityData;
import es.ucm.look.data.LookData;

public class RealidadAumentadaActivity extends LookAR {
    /** Called when the activity is first created. */
	public RealidadAumentadaActivity (){
		super(true,true,true,true,100.0f,true) ;
	}
	@Override
	protected void onCreate (Bundle savedInstanceState){
		super.onCreate (savedInstanceState) ;
		// Create the element data
		EntityData data = new EntityData() ;
		data.setLocation(10,0,0) ;
		// Add the data to the data handler
		LookData.getInstance().getDataHandler()
		. addEntity ( data ) ;
		// Updates there cent added data
		LookData.getInstance().updateData();
		LookData.getInstance().setWorldEntityFactory(new MyWorldEntityFactory());
		// Create the element data
		data.setLocation(10, 0, 0);
		data.setPropertyValue(MyWorldEntityFactory.NAME,"Element 1") ;
		data.setPropertyValue(MyWorldEntityFactory.COLOR,"green");
		EntityData data1 = new EntityData ( ) ;
		data1.setLocation(10, 0, 5) ;
		data1.setPropertyValue(MyWorldEntityFactory.NAME,"Element 2") ;
		data1.setPropertyValue(MyWorldEntityFactory .COLOR,"red") ;
		// Add the data to the data handl e r
		LookData.getInstance().getDataHandler().addEntity(data);
		LookData.getInstance().getDataHandler().addEntity(data1);
		// Updates the r e c ent added data
		LookData.getInstance().updateData();
	
	}
}