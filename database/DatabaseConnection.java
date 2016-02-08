package database;

/*import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;*/
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
		String ip = getIP();
		if(ip == null) return;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			CONNECTION = DriverManager.getConnection("jdbc:mysql://"+ip+":3306/clinic", "root", "root");
		} catch (SQLException | ClassNotFoundException  e) {
			JOptionPane.showMessageDialog(null, "Error in Openning Connection: "+e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * reads and returns the ip address of database
	 * from database_ip.txt file from project folder.
	 * reads the first line of the file and returns.
	 * @return ip address of database.
	 */
	private static String getIP() {
		/*File file = new File(System.getProperty("user.dir")+"\\database_ip.txt");
		try (BufferedReader in = new BufferedReader(new FileReader(file))){
			String address = in.readLine().trim();
			return address;
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Ip address File not found: "+e.getMessage());
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;*/
		return "localhost"; //Temporary line.  
	}
}
