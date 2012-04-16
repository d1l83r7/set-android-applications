package es.innova_t.android.pepe.dto;

import java.io.Serializable;

public class PhoneNumberDTO implements Serializable {
	private static final long serialVersionUID = 7840928577279735431L;
	private String number;
	private int labelResourceType;
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public int getLabelResourceType() {
		return labelResourceType;
	}
	public void setLabelResourceType(int labelResourceType) {
		this.labelResourceType = labelResourceType;
	}
	
}
