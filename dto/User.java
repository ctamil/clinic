package dto;

public class User {

	private String name;
	private boolean isAdmin;
	private String password;
	
	
	
	public User(String name, boolean isAdmin, String password) {
		this(name, isAdmin);
		this.password = password;
	}
	public User(String name, boolean isAdmin) {
		this(name);
		this.isAdmin = isAdmin;
	}
	public User(String name, String pass){
		this(name);
		this.password = pass;
	}
	public User(String name) {
		super();
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	public String getName() {
		return name;
	}
	public boolean isAdmin(){
		return isAdmin;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
