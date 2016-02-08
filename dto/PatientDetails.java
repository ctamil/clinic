package dto;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;

public class PatientDetails {

	private String id;
	private String fatherName;
	private String motherName;
	private Boolean isMale;
	private String pinCode;
	private String address;
	private String city;
	private String state;
	private Calendar dob;
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
		Date date = new Date(dob.getTimeInMillis());
		return date;
	}
	public void setDob(java.sql.Date date){
		LocalDate localDate = date.toLocalDate();
		setDob(localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear());
	}
	public void setDob(int date, int month, int year){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, date);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.YEAR, year);
		setDob(cal);
	}
	public void setDob(Calendar dob){
		if(dob == null) return;
		this.dob = dob;
		calculateAge(Calendar.getInstance().get(Calendar.YEAR), dob.get(Calendar.YEAR));
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
