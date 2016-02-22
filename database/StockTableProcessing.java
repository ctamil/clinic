package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import ds.LinkedList;
import ds.Traveller;
import dto.Bill;
import dto.Item;

public class StockTableProcessing extends TableProcessing{

	private Connection connection;

	public StockTableProcessing() {
		connection = DatabaseConnection.getConnection();
	}

	public int getSize() {
		return getSize("stock");
	}

	public boolean addToDB(Item item){
		String query = "insert into stock (item_name, qty, price, category, expire_date) values(?, ?, ?, ?, ?)";
		int oldQty = 0;
		try {
			if((oldQty = getItemQty(item.getName(), item.getCategory(), item.getExpireDate(), item.getPrice())) > 0) { //if item already exists
				updateItemToDB(item, oldQty);
			}else {
				PreparedStatement preStmt = connection.prepareStatement(query);
				preStmt.setString(1, item.getName());
				preStmt.setInt(2, item.getQuantity());
				preStmt.setFloat(3, item.getPrice());
				preStmt.setString(4, item.getCategory());
				preStmt.setDate(5, item.getExpireDate());
				preStmt.executeUpdate();
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error in Stock table processing: "+e.getMessage());
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private void updateDB(Item item) throws SQLException{
		if(item.getQuantity() > 0) updateItemToDB(item, 0);
		else deleteItemFromDb(item);
	}

	private void updateItemToDB(Item item, int oldQty) throws SQLException{
		String updateQuery = "update stock set qty = ? where item_name = ? and expire_date = ? and category = ? and price = ?;";
		PreparedStatement preStmt = connection.prepareStatement(updateQuery);
		preStmt.setInt(1, item.getQuantity() + oldQty);
		preStmt.setString(2, item.getName());
		preStmt.setDate(3, item.getExpireDate());
		preStmt.setString(4, item.getCategory());
		preStmt.setFloat(5, item.getPrice());
		
		preStmt.executeUpdate();
		preStmt.close();
	}

	public boolean deleteItemFromDb(Item item) {
		String deleteQuery = "delete from stock where item_name = ? and expire_date = ? and category = ? and price = ?";
		try(PreparedStatement preStmt = connection.prepareStatement(deleteQuery)){
			
			preStmt.setString(1, item.getName());
			preStmt.setDate(2, item.getExpireDate());
			preStmt.setString(3, item.getCategory());
			preStmt.setFloat(4, item.getPrice());
			
			preStmt.executeUpdate();
			
		}catch(SQLException ex){
			JOptionPane.showMessageDialog(null, "Error in Stock table processing: "+ex.getMessage());
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	private String buildQuery(String searchType, String searchKey, String orderByColumn, String orderType){
		StringBuilder query = new StringBuilder("select item_name, qty, price, category, expire_date from stock ");
		if(searchType != null && searchKey != null && searchType.length() > 0 && searchKey.length() > 0){ //building the query to search
			if(searchType.toLowerCase().contains("item")) query.append("where item_name = ");
			else if(searchType.toLowerCase().contains("category")) query.append("where category = ");
			query.append("'"+searchKey+"' ");
		}
		if(orderByColumn != null && orderByColumn.length() > 0){ //building query to sort the result.
			query.append("order by ");
			if(orderByColumn.toLowerCase().contains("item")) query.append("item_name ");
			else if(orderByColumn.toLowerCase().contains("category")) query.append("category ");
			else if(orderByColumn.toLowerCase().contains("quantity")) query.append("qty ");
			else if(orderByColumn.toLowerCase().contains("price")) query.append("price ");
			else if(orderByColumn.toLowerCase().contains("expire")) query.append("expire_date ");
			if(orderType == null || orderType.equals("ASC")) query.append("ASC ");
			else  query.append("DESC ");
		}
		query.append(";"); //completed building query;
		System.err.println("builded Query: "+query);
		return query.toString();
	}

	/**
	 * @return list of item_name's from stock table in ascending order.
	 */
	public LinkedList itemList(String searchType, String searchKey, String orderByColumn, String orderType){
		String query = buildQuery(searchType, searchKey, orderByColumn, orderType);
		ResultSet result = null;
		LinkedList list = new LinkedList();
		try(Statement stmt = connection.createStatement()) {
			result = stmt.executeQuery(query);
			while(result.next()){
				Item item = new Item();
				
				item.setName(result.getString("item_name"));
				item.setCategory(result.getString("category"));
				item.setExpireDate(result.getDate("expire_date"));
				item.setPrice(result.getFloat("price"));
				item.setQuantity(result.getInt("qty"));
				
				list.add(item);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error in Stock table processing: "+e.getMessage());
			e.printStackTrace();
		}
		return list;
	}
	
	public LinkedList getexpireDatesList(String itemName, String category){
		ResultSet result = null;
		LinkedList list = new LinkedList();
		try(Statement stmt = connection.createStatement()) {
			
			result = stmt.executeQuery("select distinct expire_date from stock where item_name = "
					+ "'"+itemName+"' and category = '"+category+"' order by expire_date asc;");

			while(result.next()){
				Date date = result.getDate("expire_date");
				list.add(date);
			}
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error in Stock table processing: "+e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	public LinkedList itemList(String category){
		ResultSet result = null;
		LinkedList list = new LinkedList();
		try(Statement stmt = connection.createStatement()) {
			
			result = stmt.executeQuery("select distinct item_name from stock where category = '"+category+"' order by item_name asc;");

			while(result.next()){
				String item = result.getString("item_name");
				list.add(item);
			}
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error in Stock table processing: "+e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @param itemName
	 * @return the qty value from stock table for the particular item.
	 */
	public int getItemQty(String itemName, String category, java.sql.Date date, float price){
		ResultSet result = null;
		int qty = 0;
		String quey = "select qty from stock where item_name = ? and category = ? and expire_date = ? and price = ?;";
		try(PreparedStatement stmt = connection.prepareStatement(quey)) {
			
			System.out.println("getItemQty: "+itemName+":"+category+":"+date);
			stmt.setString(1, itemName);
			stmt.setString(2, category);
			stmt.setDate(3, date);
			stmt.setFloat(4, price);
			result = stmt.executeQuery();
			
			if(result.first())
				qty = result.getInt("qty");
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error in Stock table processing: "+e.getMessage());
			e.printStackTrace();
		}
		return qty;
	}

	/**
	 * @param itemName name of the item
	 * @return returns price of the particular item from stock table;
	 */
	public LinkedList getItemPrices(String itemName, String category, java.sql.Date date){
		ResultSet result = null;
		LinkedList list = new LinkedList();
		String quey = "select distinct price from stock where item_name = ? and category = ? and expire_date = ?;";
		try(PreparedStatement stmt = connection.prepareStatement(quey)) {
			
			stmt.setString(1, itemName);
			stmt.setString(2, category);
			stmt.setDate(3, date);
			result = stmt.executeQuery();

			while(result.next()) list.add(result.getFloat("price"));
				
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error in Stock table processing: "+e.getMessage());
			e.printStackTrace();
		}
		return list;
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
			String selectQuery = "select qty from stock where item_name = ? and expire_date = ? and category = ?"
					+ " and price = ?;";
			try(PreparedStatement preStmt = connection.prepareStatement(selectQuery)){
				preStmt.setString(1, item.getName());
				preStmt.setDate(2, item.getExpireDate());
				preStmt.setString(3, item.getCategory());
				preStmt.setFloat(4, item.getPrice());
				ResultSet result= preStmt.executeQuery(selectQuery);
				if(result.first()){
					int oldQty = result.getInt(1);
					Item itemClone = item.clone();
					itemClone.setQuantity(oldQty - item.getQuantity());
					updateDB(itemClone);
				}else {
					System.out.println("No item found on stock");
					return false;
				}
			}catch(SQLException exp){
				JOptionPane.showMessageDialog(null, "Error in Stock table processing: "+exp.getMessage());
				exp.printStackTrace();
				return false;
			}
		}
		return true;
	}
}
