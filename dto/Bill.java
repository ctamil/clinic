package dto;

import ds.LinkedList;

/**
 * Complete Bill Details with all items, total cost,
 * patient details and user added the bill details.
 * @author Tamil
 *
 */
public class Bill {

	private LinkedList items;
	private double total;
	private Patient patient;
	private User user;
	private int id;
	private float docterFee;
	
	
	public float getDocterFee() {
		return docterFee;
	}
	public void setDocterFee(float docterFee) {
		this.docterFee = docterFee;
	}
	public Bill(Patient patient, User user, int id, float docterFee) {
		items = new LinkedList();
		setPatient(patient);
		setUser(user);
		setId(id);
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
		result = prime * result + (int) (id ^ (id >>> 32));
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
		if (id != other.id)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return String.valueOf(id);
	}
	
}
