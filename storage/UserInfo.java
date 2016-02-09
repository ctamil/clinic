package storage;

import dto.User;

public class UserInfo {

	private static User USER;
	
	private UserInfo(){
		
	}

	public static User getUser() {
		return USER;
	}

	public static void setUSER(User user) {
		UserInfo.USER = user;
	}

}
