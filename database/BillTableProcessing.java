package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class BillTableProcessing {

	private Connection connection;
	
	public BillTableProcessing() {
		connection = DatabaseConnection.getConnection();
	}
	
	/**
	 * returns number of rows in bill table
	 */
	private int getSize() {
		ResultSet result = null;
		try(Statement stmt = connection.createStatement()) {
			result = stmt.executeQuery("select count(*) from bill;");
			if(result.first()) return result.getInt(1);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error in Bill table processing");
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * gets number of rows in bill table and returns puls one with that.
	 * @return 1 + bill table size;
	 */
	public int getId(){
		return getSize()+1;
	}
	
	public void addId(int id){
		try(Statement stmt = connection.createStatement()){
			String insertQuery = "insert into bill (id) values("+id+");";
			stmt.executeUpdate(insertQuery);
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, "Error in Bill table processing");
			e.printStackTrace();
		}
	}
	
}
