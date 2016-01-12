package dto;

public class Patient {

	private String name;
	private long contactNo;
	private int pinCode;
	private String address;
	private String city;
	private String state;
	private String country;
	
	public int getPinCode() {
		return pinCode;
	}
	public void setPinCode(int pinCode) {
		this.pinCode = pinCode;
	}
	public Patient(String name, long contactNo, int pinCode, String address,
			String city, String state, String country) {
		super();
		setName(name);
		setContactNo(contactNo);
		setPinCode(pinCode);
		setAddress(address);
		setCity(city);
		setState(state);
		setCountry(country);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (contactNo ^ (contactNo >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Patient other = (Patient) obj;
		if (contactNo != other.contactNo)
			return false;
		return true;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		name = name.toLowerCase();
		this.name = name;
	}
	public long getContactNo() {
		return contactNo;
	}
	public void setContactNo(long contactNo) {
		this.contactNo = contactNo;
	}
	public String getAddress() {
		address = address.toLowerCase();
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		if(city == null || state.equals("")) city = "coimbatore";
		city = city.toLowerCase();
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		if(state == null || state.equals("")) state = "tamil nadu";
		state = state.toLowerCase();
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		if(country == null || country.equals("")) country = "india";
		country = country.toLowerCase();
		this.country = country;
	}
	
	@Override
	public String toString() {
		return "Patient [name=" + name + ", contactNo=" + contactNo
				+ ", pinCode=" + pinCode + ", address=" + address + ", city="
				+ city + ", state=" + state + ", country=" + country + "]";
	}

	
}
