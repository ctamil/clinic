package dto;

import java.sql.Date;

public class PatientDetails {

	private String id;
	private String fatherName;
	private String motherName;
	private Boolean isMale;
	private String pinCode;
	private String address;
	private String city;
	private String state;
	private java.sql.Date dob;
	private int age;
	private String notes;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public PatientDetails(){}
	
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public int getAge(){
		return age;
	}
	public Date getDob(){
		return dob;
	}
	
	@SuppressWarnings("deprecation")
	public void setDob(java.sql.Date dob){
		calculateAge(new java.util.Date().getYear(), dob.getYear());
		this.dob = dob;
	}
	
	private void calculateAge(int currYear, int dobYear) {
		age = currYear - dobYear;
	}
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Boolean getIsMale() {
		return isMale;
	}
	public void setIsMale(Boolean isMale) {
		this.isMale = isMale;
	}
	
	@Override
	public String toString() {
		return getId();
	}
}
