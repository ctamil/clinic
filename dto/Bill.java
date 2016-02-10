package dto;

import java.sql.Date;

import ds.LinkedList;

/**
 * Complete Bill Details with all items, total cost,
 * patient details and user added the bill details.
 * @author Tamil
 *
 */
public class Bill {

	private long billNo;
	private String file;
	private java.sql.Date date;
	private LinkedList items;
	private double total;
	private Patient patient;
	private User user;
	private float docterFee;
	
	
	
	public Bill(long billNo, String file, Date date, User user) {
		super();
		this.billNo = billNo;
		this.file = file;
		this.date = date;
		this.user = user;
	}
	
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public java.sql.Date getDate() {
		return date;
	}
	public void setDate(java.sql.Date date) {
		this.date = date;
	}	
	
	public float getDocterFee() {
		return docterFee;
	}
	public void setDocterFee(float docterFee) {
		this.docterFee = docterFee;
	}
	public Bill(Patient patient, User user, long billNo, float docterFee) {
		items = new LinkedList();
		setPatient(patient);
		setUser(user);
		setBillNo(billNo);
		setDocterFee(docterFee);
	}
	public void removeItem(Item item){
		items.remove(item);
		total -= (item.getPrice() * item.getQuantity());
	}
	public void addItem(Item item){
		items.add(item);
		total += (item.getPrice() * item.getQuantity());
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public long getBillNo() {
		return billNo;
	}
	public void setBillNo(long billNo) {
		this.billNo = billNo;
	}
	public LinkedList getItems() {
		return items;
	}
	public double getTotal() {
		return total;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (billNo ^ (billNo >>> 32));
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
		Bill other = (Bill) obj;
		if (billNo != other.billNo)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return String.valueOf(billNo);
	}
	
}
