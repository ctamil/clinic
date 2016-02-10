package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import ds.LinkedList;
import ds.Traveller;

public class CategoryTableProcessing extends TableProcessing{
	
	private Connection connection;
	
	public CategoryTableProcessing() {
		connection = DatabaseConnection.getConnection();
	}
	
	public int getSize() {
		return getSize("category");
	}

	public LinkedList categoryList(){
		ResultSet result = null;
		LinkedList list = new LinkedList();
		try(Statement stmt = connection.createStatement()) {
			
			result = stmt.executeQuery("select name from category order by name asc;");

			while(result.next()){
				String item = result.getString("name");
				list.add(item);
			}
				
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error in category table processing: "+e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	public boolean addToDB(String category) {
		String insertQuery = "insert into category (name) values(?) ;";
		try(PreparedStatement stmt = connection.prepareStatement(insertQuery)){
			stmt.setString(1, category);
			stmt.executeUpdate();
		}catch(SQLException ex){
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error in saving to database: "
					+ ""+ex.getMessage());
			return false;
		}
		return true;
	}
	
	public void updateCategory(JComboBox<String> box){
		if(box != null && box.getItemCount() > 0) box.removeAllItems();
		Traveller travller = categoryList().traveller();
		while(travller.hasNext()) box.addItem(travller.next().toString());
	}
	
}
