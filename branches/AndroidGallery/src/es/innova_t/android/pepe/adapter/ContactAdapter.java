package es.innova_t.android.pepe.adapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import es.innova_t.android.pepe.dto.ContactDTO;
import es.innova_t.android.pepe.dto.PhoneNumberDTO;

public class ContactAdapter {
	
	private Activity activity;
	private static ContactAdapter contactAdapter;
	private static List<ContactDTO>lContact;
	private final Logger log = Logger.getLogger(ContactAdapter.class);
	
	public List<ContactDTO> getlContact() {
		return lContact;
	}

	private ContactAdapter(){
		lContact = getContacts();
		
	};
	
	public static ContactAdapter getInstance(){
		if(contactAdapter==null)
			contactAdapter = new ContactAdapter();
		return contactAdapter;
	}
	
	
	private List<ContactDTO> getContacts(){
		if(log.isDebugEnabled()){
			log.debug("-> getContacts()");
		}
		List<ContactDTO>l=new ArrayList<ContactDTO>();
		
		List<Integer>lContacts = getListContacts();
		
		for(int i=0;i<lContacts.size();i++){
			Integer contactId = lContacts.get(i);
			String name = getContactName(contactId);
			List<PhoneNumberDTO> lPhoneNumer = getContactPhoneNumbers(contactId);
			String photoId = getContactPhotoId(contactId);
			Bitmap bitmap = null;
			if(photoId!=null){
				 bitmap = getContactPhoto(photoId);
			}
			
			ContactDTO contactDTO = new ContactDTO();
			contactDTO.setContactImage(bitmap);
			contactDTO.setName(name);
			contactDTO.setPhoneNumerDTO(lPhoneNumer);
			l.add(contactDTO);
		}
		if(log.isDebugEnabled()){
			log.debug("<- getContacts("+l.toString()+")");
		}
		return l;
		
	}
	
	private List<Integer> getListContacts(){
		if(log.isDebugEnabled()){
			log.debug("-> getListContacts()");
		}
		List<Integer> lContactId = new ArrayList<Integer>();
		String[] projection = new String[] {
				RawContacts.CONTACT_ID // the contact id column
				};
		
		Cursor rawContacts = activity.managedQuery(
					RawContacts.CONTENT_URI,    // the URI for raw contact provider
					projection,
					null,                   // selection = null, retrieve all entries
					null,                   // selection is without parameters
					null);                  // do not order
		
		int contactIdColumnIndex = rawContacts.getColumnIndex(RawContacts.CONTACT_ID);
	
		if(rawContacts.moveToFirst()) {
			    while(!rawContacts.isAfterLast()) {     // still a valid entry left?
			        int contactId = rawContacts.getInt(contactIdColumnIndex);
			        lContactId.add(contactId);
			        rawContacts.moveToNext();           // move to the next entry
			    }
		}
		if(log.isDebugEnabled()){
			log.debug("<- getListContacts("+lContactId.toString()+")");
		}
		return lContactId;				
	}
	
	private String getContactPhotoId(Integer contactId){
		if(log.isDebugEnabled()){
			log.debug("-> getContactPhotoId(contactId: "+contactId.toString()+")");
		}
		String photoId = null;
		String[] projection = new String[] {
			    Contacts.PHOTO_ID       // the id of the column in the data table for the image
			};

		Cursor contact = activity.managedQuery(
	        Contacts.CONTENT_URI,
	        projection,
	        Contacts._ID + "=?",    // filter entries on the basis of the contact id
	        new String[]{String.valueOf(contactId)},    // the parameter to which the contact id column is compared to
	        null);
		
		if(contact.moveToFirst()) {
		    photoId = contact.getString(
		            contact.getColumnIndex(Contacts.PHOTO_ID));
		}
		contact.close();
		if(log.isDebugEnabled()){
			log.debug("<- getContactPhotoId(photoId: "+photoId+")");
		}
		return photoId;

	}
	
	private String getContactName(Integer contactId){
		if(log.isDebugEnabled()){
			log.debug("-> getContactName(contactId: "+contactId.toString()+")");
		}
		String name = null;		
		String[] projection = new String[] {
		    Contacts.DISPLAY_NAME  // the name of the contact
		};
		
		Cursor contact = activity.managedQuery(
            Contacts.CONTENT_URI,
            projection,
            Contacts._ID + "=?",    // filter entries on the basis of the contact id
            new String[]{String.valueOf(contactId)},    // the parameter to which the contact id column is compared to
            null);
		
		if(contact.moveToFirst()) {
			name = contact.getString(
		    contact.getColumnIndex(Contacts.DISPLAY_NAME));
		}
		contact.close();
		if(log.isDebugEnabled()){
			log.debug("<- getContactName(name: "+name+")");
		}
		return name;
		
	}
	
	private Bitmap getContactPhoto(String photoId){
		if(log.isDebugEnabled()){
			log.debug("-> getContactPhoto(photoId: "+photoId+")");
		}
		Bitmap photoBitmap = null;
		Cursor photo = activity.managedQuery(
	        Data.CONTENT_URI,
	        new String[] {Photo.PHOTO},     // column for the blob
	        Data._ID + "=?",                // select row by id
	        new String[]{photoId},          // filter by photoId
	        null);
		if(photo.moveToFirst()) {
		    byte[] photoBlob = photo.getBlob(
		            photo.getColumnIndex(Photo.PHOTO));
		    photoBitmap = BitmapFactory.decodeByteArray(
		            photoBlob, 0, photoBlob.length);
		}
		photo.close();
		if(log.isDebugEnabled()){
			log.debug("<- getContactPhoto(photoBitmap : "+photoBitmap+")");
		}
		return photoBitmap ;
	}
	
	private List<PhoneNumberDTO> getContactPhoneNumbers(Integer contactId){
		if(log.isDebugEnabled()){
			log.debug("-> getContactPhoneNumbers(contactId : "+contactId.toString()+")");
		}
		List<PhoneNumberDTO> nums = new ArrayList<PhoneNumberDTO>();
		String[] projection = new String[] {
	        Phone.NUMBER,
	        Phone.TYPE,
		};
		Cursor phone = activity.managedQuery(
			Phone.CONTENT_URI,
            projection,
            Data.CONTACT_ID + "=?",
            new String[]{String.valueOf(contactId)},
            null);
		
		if(phone.moveToFirst()) {
		    int contactNumberColumnIndex = phone.getColumnIndex(Phone.NUMBER);
		    int contactTypeColumnIndex = phone.getColumnIndex(Phone.TYPE);
		 
		    while(!phone.isAfterLast()) {
		        String number = phone.getString(contactNumberColumnIndex);
		        int type = phone.getInt(contactTypeColumnIndex);
		        int typeLabelResource = Phone.getTypeLabelResource(type);
		        PhoneNumberDTO pn = new PhoneNumberDTO();
		        pn.setLabelResourceType(typeLabelResource);
		        pn.setNumber(number);
		        nums.add(pn);
		        phone.moveToNext();
		    }
		 
		}
		phone.close();
		if(log.isDebugEnabled()){
			log.debug("<- getContactPhoneNumbers(nums : "+nums.size()+")");
		}
		return nums;
	}
}
