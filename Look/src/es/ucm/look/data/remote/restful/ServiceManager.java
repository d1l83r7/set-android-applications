/*******************************************************************************
 * Look! is a Framework of Augmented Reality for Android. 
 * 
 * Copyright (C) 2011 
 * 		Sergio Bellón Alcarazo
 * 		Jorge Creixell Rojo
 * 		Ángel Serrano Laguna
 * 	
 * 	   Final Year Project developed to Sistemas Informáticos 2010/2011 - Facultad de Informática - Universidad Complutense de Madrid - Spain
 * 	
 * 	   Project led by: Jorge J. Gómez Sánz
 * 
 * 
 * ****************************************************************************
 * 
 * This file is part of Look! (http://lookar.sf.net/)
 * 
 * Look! is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/
 ******************************************************************************/
package es.ucm.look.data.remote.restful;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;
import es.ucm.look.data.remote.LookProperties;

/**
 * The ServiceManager is the one who connect the service and manage their
 * Callbacks
 * 
 * @author Sergio
 * 
 */
public class ServiceManager {

	protected static final String LOG_TAG = "Look ServiceManager";
	public RemoteDataHandler restfulService;
	protected RestfulServiceConnection conn;
	protected boolean started = false;
	protected Context context;

	/**
	 * Constructor class
	 * @param context
	 * 		Context application
	 */
	public ServiceManager(Context context) {
		this.context = context;
	}

	/**
	 * To start the service
	 */
	public void startService() {
		if (started) {
			Toast.makeText(context, "Service already started",
					Toast.LENGTH_SHORT).show();
		} else {

			Intent i = new Intent(context, LookService.class);
			context.startService(i);
			started = true;
		}
	}

	/**
	 * To stop the service
	 */
	public void stopService() {
		if (!started) {
			Toast.makeText(context, "Service not yet started",
					Toast.LENGTH_SHORT).show();
		} else {
			Intent i = new Intent(context, LookService.class);
			context.stopService(i);
			started = false;
		}
	}

	/**
	 * To bind the Service
	 */
	public void bindService() {
		if (conn == null) {
			conn = new RestfulServiceConnection();
			Intent i = new Intent(context, LookService.class);
			boolean connection = context.bindService(i, conn,
					Context.BIND_AUTO_CREATE);
			if (!connection) {
				Log.i(LOG_TAG,
						"There is a serious problem with bindService on ServiceManager");
			}

		} else {
			Toast.makeText(context, "Cannot bind - service already bound",
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Destroy the service
	 */
	public void destroy() {
		releaseService();
	}

	/**
	 * Release the service
	 */
	private void releaseService() {
		if (conn != null) {
			context.unbindService(conn);
			conn = null;
			Log.d(LOG_TAG, "unbindService()");
		} else {
			Toast.makeText(context, "Cannot unbind - service not bound",
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * To do the Connection in the service with the callbaks
	 * @author Sergio
	 *
	 */
	public class RestfulServiceConnection implements ServiceConnection {
		@Override
		public void onServiceConnected(ComponentName className,
				IBinder boundService) {
			restfulService = RemoteDataHandler.Stub
					.asInterface((IBinder) boundService);
			try {
				restfulService.registerCallback(mCallback);
			} catch (RemoteException e) {
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName className) {
			restfulService = null;
		}
	};

	/**
	 * To Manage the Callbacks
	 */
	private IRemoteServiceCallback mCallback = new IRemoteServiceCallback.Stub() {

		@Override
		public void userLogIn(String result) throws RemoteException {
			if (mHandler != null)
				mHandler.sendMessage(mHandler.obtainMessage(
						LookProperties.ACTION_LOGIN, result));

		}

		@Override
		public void sendResponse1(String result) throws RemoteException {
			if (mHandler != null)
				mHandler.sendMessage(mHandler.obtainMessage(
						LookProperties.ACTION_ADD_ELEMENT, result));

		}

		@Override
		public void sendResponse2(String result) throws RemoteException {
			if (mHandler != null)
				mHandler.sendMessage(mHandler.obtainMessage(
						LookProperties.UPDATE_DB, result));

		}

		@Override
		public void sendResponse3(String result) throws RemoteException {
			if (mHandler != null)
				mHandler.sendMessage(mHandler.obtainMessage(
						LookProperties.ACTION_MODIFY_PROPERTY, result));

		}
	};

	
	private Handler mHandler;

	/**
	 * Set the handler to retrive the Callbacks to an application
	 * 
	 * @param handler
	 */
	public void setHandler(Handler handler) {
		mHandler = handler;
	}
}
