package dto;

public class Item implements Cloneable{
	
	private String name;
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
		return clone;
	}
	
	public Item(String name, float price, int quantity) {
		super();
		setName(name);
		setPrice(price);
		setQuantity(quantity);
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Float.floatToIntBits(price);
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
		Item other = (Item) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Float.floatToIntBits(price) != Float.floatToIntBits(other.price))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
}
