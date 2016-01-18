package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class DatabaseConnection {

	static private Connection CONNECTION;
	
	public static Connection getConnection(){
		if(CONNECTION == null) initialize();
		return CONNECTION;
	}

	private static void initialize() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			CONNECTION = DriverManager.getConnection("jdbc:mysql://localhost:3306/clinic", "root", "root");
		} catch (SQLException | ClassNotFoundException  e) {
			JOptionPane.showMessageDialog(null, "Error in Openning Connection");
			e.printStackTrace();
		}
	}
}
