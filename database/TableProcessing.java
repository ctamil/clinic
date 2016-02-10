package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class TableProcessing {
	
	private Connection connection;
	
	public TableProcessing() {
		connection = DatabaseConnection.getConnection();
	}
	
	public int getSize(String tableName) {
		ResultSet result = null;
		try(Statement stmt = connection.createStatement()) {
			result = stmt.executeQuery("select count(*) from "+tableName+";");
			if(result.first()) return result.getInt(1);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error in Bill table processing");
			e.printStackTrace();
		}
		return 0;
	}

}
