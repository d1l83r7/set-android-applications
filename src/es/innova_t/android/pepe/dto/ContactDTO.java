package es.innova_t.android.pepe.dto;

import java.util.List;

import android.graphics.Bitmap;

public class ContactDTO implements java.io.Serializable {
	private static final long serialVersionUID = 731016604033114077L;
	private String name;
	private List<PhoneNumberDTO> phoneNumerDTO;
	public List<PhoneNumberDTO> getPhoneNumerDTO() {
		return phoneNumerDTO;
	}
	public void setPhoneNumerDTO(List<PhoneNumberDTO> phoneNumerDTO) {
		this.phoneNumerDTO = phoneNumerDTO;
	}
	public Bitmap getContactImage() {
		return contactImage;
	}
	public void setContactImage(Bitmap contactImage) {
		this.contactImage = contactImage;
	}
	private Bitmap contactImage;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
