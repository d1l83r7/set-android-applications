/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\Android\\Look\\src\\es\\ucm\\look\\data\\remote\\restful\\RemoteDataHandler.aidl
 */
package es.ucm.look.data.remote.restful;
public interface RemoteDataHandler extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements es.ucm.look.data.remote.restful.RemoteDataHandler
{
private static final java.lang.String DESCRIPTOR = "es.ucm.look.data.remote.restful.RemoteDataHandler";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an es.ucm.look.data.remote.restful.RemoteDataHandler interface,
 * generating a proxy if needed.
 */
public static es.ucm.look.data.remote.restful.RemoteDataHandler asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = (android.os.IInterface)obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof es.ucm.look.data.remote.restful.RemoteDataHandler))) {
return ((es.ucm.look.data.remote.restful.RemoteDataHandler)iin);
}
return new es.ucm.look.data.remote.restful.RemoteDataHandler.Stub.Proxy(obj);
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
case TRANSACTION_addElement:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
float _arg1;
_arg1 = data.readFloat();
float _arg2;
_arg2 = data.readFloat();
float _arg3;
_arg3 = data.readFloat();
java.util.Map _arg4;
java.lang.ClassLoader cl = (java.lang.ClassLoader)this.getClass().getClassLoader();
_arg4 = data.readHashMap(cl);
this.addElement(_arg0, _arg1, _arg2, _arg3, _arg4);
reply.writeNoException();
return true;
}
case TRANSACTION_updateElementPosition:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
float _arg1;
_arg1 = data.readFloat();
float _arg2;
_arg2 = data.readFloat();
float _arg3;
_arg3 = data.readFloat();
this.updateElementPosition(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
return true;
}
case TRANSACTION_updateOrAddProperty:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
java.lang.String _arg2;
_arg2 = data.readString();
this.updateOrAddProperty(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
case TRANSACTION_doLogin:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
this.doLogin(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_getElementsUpdated:
{
data.enforceInterface(DESCRIPTOR);
float _arg0;
_arg0 = data.readFloat();
float _arg1;
_arg1 = data.readFloat();
float _arg2;
_arg2 = data.readFloat();
float _arg3;
_arg3 = data.readFloat();
java.lang.String _arg4;
_arg4 = data.readString();
java.util.List _result = this.getElementsUpdated(_arg0, _arg1, _arg2, _arg3, _arg4);
reply.writeNoException();
reply.writeList(_result);
return true;
}
case TRANSACTION_registerCallback:
{
data.enforceInterface(DESCRIPTOR);
es.ucm.look.data.remote.restful.IRemoteServiceCallback _arg0;
_arg0 = es.ucm.look.data.remote.restful.IRemoteServiceCallback.Stub.asInterface(data.readStrongBinder());
this.registerCallback(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements es.ucm.look.data.remote.restful.RemoteDataHandler
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
	 * Adds an entity to the world in a position
	 * 
	 * @param type
	 *            type of the entity
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param z
	 *            z coordinate
	 * @param properties
	 *            Map with the properties of the entity
	 * 
	 */
public void addElement(java.lang.String type, float x, float y, float z, java.util.Map properties) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(type);
_data.writeFloat(x);
_data.writeFloat(y);
_data.writeFloat(z);
_data.writeMap(properties);
mRemote.transact(Stub.TRANSACTION_addElement, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
	 * Updates an element position
	 * 
	 * @param id
	 *            element's id
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param z
	 *            z coordinate
	 */
public void updateElementPosition(int id, float x, float y, float z) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(id);
_data.writeFloat(x);
_data.writeFloat(y);
_data.writeFloat(z);
mRemote.transact(Stub.TRANSACTION_updateElementPosition, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
	 * Updates or add a property
	 * 
	 * @param id
	 *            elemnt's id
	 * @param propertyName
	 *            property name
	 * @param propertyValue
	 *            property value
	 */
public void updateOrAddProperty(int id, java.lang.String propertyName, java.lang.String propertyValue) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(id);
_data.writeString(propertyName);
_data.writeString(propertyValue);
mRemote.transact(Stub.TRANSACTION_updateOrAddProperty, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
	 * For to do a Login, need to the username and password
	 * 
	 * @param username
	 *            username
	 * @param propertyName
	 *            password
	 */
public void doLogin(java.lang.String username, java.lang.String password) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(username);
_data.writeString(password);
mRemote.transact(Stub.TRANSACTION_doLogin, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
	 * For to do a Login, need to the username and password
	 * 
	 * @param username
	 *            username
	 * @param propertyName
	 *            password
	 */
public java.util.List getElementsUpdated(float x, float y, float z, float radius, java.lang.String date) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeFloat(x);
_data.writeFloat(y);
_data.writeFloat(z);
_data.writeFloat(radius);
_data.writeString(date);
mRemote.transact(Stub.TRANSACTION_getElementsUpdated, _data, _reply, 0);
_reply.readException();
java.lang.ClassLoader cl = (java.lang.ClassLoader)this.getClass().getClassLoader();
_result = _reply.readArrayList(cl);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void registerCallback(es.ucm.look.data.remote.restful.IRemoteServiceCallback cb) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((cb!=null))?(cb.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_registerCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_addElement = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_updateElementPosition = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_updateOrAddProperty = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_doLogin = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_getElementsUpdated = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_registerCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
}
/**
	 * Adds an entity to the world in a position
	 * 
	 * @param type
	 *            type of the entity
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param z
	 *            z coordinate
	 * @param properties
	 *            Map with the properties of the entity
	 * 
	 */
public void addElement(java.lang.String type, float x, float y, float z, java.util.Map properties) throws android.os.RemoteException;
/**
	 * Updates an element position
	 * 
	 * @param id
	 *            element's id
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param z
	 *            z coordinate
	 */
public void updateElementPosition(int id, float x, float y, float z) throws android.os.RemoteException;
/**
	 * Updates or add a property
	 * 
	 * @param id
	 *            elemnt's id
	 * @param propertyName
	 *            property name
	 * @param propertyValue
	 *            property value
	 */
public void updateOrAddProperty(int id, java.lang.String propertyName, java.lang.String propertyValue) throws android.os.RemoteException;
/**
	 * For to do a Login, need to the username and password
	 * 
	 * @param username
	 *            username
	 * @param propertyName
	 *            password
	 */
public void doLogin(java.lang.String username, java.lang.String password) throws android.os.RemoteException;
/**
	 * For to do a Login, need to the username and password
	 * 
	 * @param username
	 *            username
	 * @param propertyName
	 *            password
	 */
public java.util.List getElementsUpdated(float x, float y, float z, float radius, java.lang.String date) throws android.os.RemoteException;
public void registerCallback(es.ucm.look.data.remote.restful.IRemoteServiceCallback cb) throws android.os.RemoteException;
}
