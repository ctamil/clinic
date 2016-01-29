package storage;

import dto.User;

public class UserInfo {

	private static User USER;

	public static User getUSER() {
		if(USER == null) USER = new User("Default");
		return USER;
	}

	public static void setUSER(String name) {
		UserInfo.USER = new User(name);
	}

}
