package cat.imi.barcelona.tour.dao.helper;

import android.content.Context;

public class DataBaseManager {
	private BCNTourAdapter bcnTourAdapter;
	private static DataBaseManager dataBaseManager;
	
	public static DataBaseManager getInstance(Context context){
		if(dataBaseManager==null){
			dataBaseManager = new DataBaseManager(context);
		}
		return dataBaseManager;
	}
	
	private DataBaseManager(Context context){
		bcnTourAdapter = new BCNTourAdapter(context);
	}
	
	public BCNTourAdapter getBcnTourAdapter(){
		return bcnTourAdapter;
	}
}
