package dto;

public class Item implements Cloneable{
	
	private String name;
	private String category;
	private java.sql.Date expireDate;
	private float price;
	private int quantity;
	
	@Override
	public Item clone() {
		Item clone = null;
		try {
			clone = (Item)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		clone.setName(getName());
		clone.setQuantity(getQuantity());
		clone.setPrice(getPrice());
		clone.setCategory(getCategory());
		clone.setExpireDate(getExpireDate());
		return clone;
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public java.sql.Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(java.sql.Date expireDate) {
		this.expireDate = expireDate;
	}

	public Item(String name, float price, int quantity, String category, java.sql.Date expireDate) {
		super();
		setName(name);
		setPrice(price);
		setQuantity(quantity);
		setCategory(category);
		setExpireDate(expireDate);
	}
	public Item(String name, java.sql.Date expireDate) {
		setExpireDate(expireDate);
		setName(name);
	}
	public Item() {
	}

	public float getTotal(){
		return getPrice() * getQuantity();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
}
