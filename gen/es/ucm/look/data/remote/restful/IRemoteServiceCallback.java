/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\Android\\Look\\src\\es\\ucm\\look\\data\\remote\\restful\\IRemoteServiceCallback.aidl
 */
package es.ucm.look.data.remote.restful;
public interface IRemoteServiceCallback extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements es.ucm.look.data.remote.restful.IRemoteServiceCallback
{
private static final java.lang.String DESCRIPTOR = "es.ucm.look.data.remote.restful.IRemoteServiceCallback";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an es.ucm.look.data.remote.restful.IRemoteServiceCallback interface,
 * generating a proxy if needed.
 */
public static es.ucm.look.data.remote.restful.IRemoteServiceCallback asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = (android.os.IInterface)obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof es.ucm.look.data.remote.restful.IRemoteServiceCallback))) {
return ((es.ucm.look.data.remote.restful.IRemoteServiceCallback)iin);
}
return new es.ucm.look.data.remote.restful.IRemoteServiceCallback.Stub.Proxy(obj);
}
public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_userLogIn:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.userLogIn(_arg0);
return true;
}
case TRANSACTION_sendResponse1:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.sendResponse1(_arg0);
return true;
}
case TRANSACTION_sendResponse2:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.sendResponse2(_arg0);
return true;
}
case TRANSACTION_sendResponse3:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.sendResponse3(_arg0);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements es.ucm.look.data.remote.restful.IRemoteServiceCallback
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
/**
	 * Is called when to get logged
	 
	 * @param result
	 *        The Callbacks with our ID
	 * 
	 */
public void userLogIn(java.lang.String result) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(result);
mRemote.transact(Stub.TRANSACTION_userLogIn, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
public void sendResponse1(java.lang.String result) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(result);
mRemote.transact(Stub.TRANSACTION_sendResponse1, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
public void sendResponse2(java.lang.String result) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(result);
mRemote.transact(Stub.TRANSACTION_sendResponse2, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
public void sendResponse3(java.lang.String result) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(result);
mRemote.transact(Stub.TRANSACTION_sendResponse3, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
}
static final int TRANSACTION_userLogIn = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_sendResponse1 = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_sendResponse2 = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_sendResponse3 = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
}
/**
	 * Is called when to get logged
	 
	 * @param result
	 *        The Callbacks with our ID
	 * 
	 */
public void userLogIn(java.lang.String result) throws android.os.RemoteException;
public void sendResponse1(java.lang.String result) throws android.os.RemoteException;
public void sendResponse2(java.lang.String result) throws android.os.RemoteException;
public void sendResponse3(java.lang.String result) throws android.os.RemoteException;
}
