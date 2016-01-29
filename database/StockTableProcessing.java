package database;

import java.sql.*;

import javax.swing.JOptionPane;

import ds.LinkedList;
import ds.Traveller;
import dto.Bill;
import dto.Item;

public class StockTableProcessing {

	private Connection connection;
	
	public StockTableProcessing() {
		connection = DatabaseConnection.getConnection();
	}
	
	public int getSize() {
		ResultSet result = null;
		try(Statement stmt = connection.createStatement()) {
			result = stmt.executeQuery("select count(*) from stock;");
			if(result.first()) return result.getInt(1);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error in Stock table processing");
			e.printStackTrace();
		}
		return 0;
	}
	
	public boolean addToTable(Item item){
		ResultSet result = null;
		try(Statement stmt = connection.createStatement()) {
			result = stmt.executeQuery("select item_name, qty, price from stock where item_name = '"+item.getName()+"';");
			if(result.first() && result.getString(1).equals(item.getName())){ //if found the same item
				
				float oldPrice = result.getFloat(3);
				int oldQty = result.getInt(2);
				item.setQuantity(item.getQuantity()+oldQty);
				result.close();
				stmt.close();
				
				try{
					if(oldPrice != item.getPrice())
						item.setPrice(Float.parseFloat(JOptionPane.showInputDialog("Old Stock Price : "+oldPrice+"\nNew Stock Price : "+
					item.getPrice()+"\nEnter the price in below text box to update in stock table.")));
				}catch(NumberFormatException exp){
					JOptionPane.showMessageDialog(null, "Invalid Input");
					return false;
				}	
				
				updateTable(item);
				
			}else addNewStock(item); //only exected when adding a new item.
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error in Stock table processing");
			e.printStackTrace();
		}
		return true;
	}
	
	private void addNewStock(Item item) throws SQLException{
		PreparedStatement preStmt = connection.prepareStatement("insert into stock (item_name, "
				+ "qty, price) values(?,?,?)");
		preStmt.setString(1, item.getName());
		preStmt.setInt(2, item.getQuantity());
		preStmt.setFloat(3, item.getPrice());
		preStmt.executeUpdate();
	}

	private void updateTable(Item item) throws SQLException{
		PreparedStatement preStmt = connection.prepareStatement("update stock set qty = ?, price = ? where item_name = ?;");
		preStmt.setInt(1, item.getQuantity());
		preStmt.setFloat(2, item.getPrice());
		preStmt.setString(3, item.getName());
		preStmt.executeUpdate();
		preStmt.close();
	}
	
	/**
	 * 
	 * @return list of stock from stock table and it was ordered by qty column
	 */
	public LinkedList toLinkedList(){
		ResultSet result = null;
		LinkedList list = new LinkedList();
		try(Statement stmt = connection.createStatement()) {
			result = stmt.executeQuery("select * from stock order by qty desc;");
			while(result.next()){
				Item stock = new Item();
				
				stock.setName(result.getString("item_name"));
				stock.setQuantity(result.getInt("qty"));
				stock.setPrice(result.getFloat("price"));
				
				list.add(stock);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error in Stock table processing");
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 
	 * @return list of item_name's from stock table in ascending order.
	 */
	public LinkedList itemList(){
		ResultSet result = null;
		LinkedList list = new LinkedList();
		try(Statement stmt = connection.createStatement()) {
			
			result = stmt.executeQuery("select item_name from stock order by item_name asc;");

			while(result.next()){
				String item = result.getString("item_name");
				list.add(item);
			}
				
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error in Stock table processing");
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * @param itemName
	 * @return the qty value from stock table for the particular item.
	 */
	public int getItemQty(String itemName){
		ResultSet result = null;
		int qty = 0;
		try(Statement stmt = connection.createStatement()) {
			
			result = stmt.executeQuery("select qty from stock where item_name = '"+itemName+"';");

			if(result.first())
				qty = result.getInt("qty");
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error in Stock table processing");
			e.printStackTrace();
		}
		return qty;
	}
	
	/**
	 * @param itemName name of the item
	 * @return returns price of the particular item from stock table;
	 */
	public float getItemPrice(String itemName){
		ResultSet result = null;
		float qty = 0;
		try(Statement stmt = connection.createStatement()) {
			
			result = stmt.executeQuery("select price from stock where item_name = '"+itemName+"';");

			if(result.first())
				qty = result.getFloat("price");
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error in Stock table processing");
			e.printStackTrace();
		}
		return qty;
	}

	/**
	 * traverse through the items in the bill and 
	 * reduces the billed item from stock tables - qty column.
	 * @param bill
	 * @return false if caught an sql exception
	 */
	public boolean updateBill(Bill bill){
		Traveller trav = bill.getItems().traveller();
		while(trav.hasNext()){
			Item item = (Item) trav.next();
			try(ResultSet result= connection.createStatement().executeQuery("select qty from stock where item_name = '"+item.getName()+"';")){
				if(result.first()){
					int oldQty = result.getInt(1);
					Item itemClone = item.clone();
					itemClone.setQuantity(oldQty - item.getQuantity());
					updateTable(itemClone);
				}else return false;
			}catch(SQLException exp){
				JOptionPane.showMessageDialog(null, "Error in Stock table processing");
				return false;
			}
		}
		return true;
	}

	/**
	 * Deletes the particular item from stock table.
	 * @param itemName
	 */
	public void deleteItem(String itemName) {
		try(Statement stmt = connection.createStatement()){
			String deleteQuery = "delete from stock where item_name = '"+itemName+"';";
			stmt.executeUpdate(deleteQuery);
		}catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error in Stock table processing");
			e.printStackTrace();
		}
	}
}
