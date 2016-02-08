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
import dto.CustomDate;
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
			JOptionPane.showMessageDialog(null, "Error in Stock table processing: "+e.getMessage());
			e.printStackTrace();
		}
		return 0;
	}

	public boolean addToDB(Item item){
		String query = "insert into stock (item_name, qty, price, category, expire_date) values(?, ?, ?, ?, ?)";
		try(PreparedStatement preStmt = connection.prepareStatement(query)) {
			
			preStmt.setString(1, item.getName());
			preStmt.setInt(2, item.getQuantity());
			preStmt.setFloat(3, item.getPrice());
			preStmt.setString(4, item.getCategory());
			preStmt.setDate(5, new Date(item.getExpireDate().getTimeInMillis()));
			preStmt.executeUpdate();
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error in Stock table processing: "+e.getMessage());
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private void updateDB(Item item) throws SQLException{
		if(item.getQuantity() > 0) updateItemToDB(item);
		else deleteItemFromDb(item);
	}

	private void updateItemToDB(Item item) throws SQLException{
		String updateQuery = "update stock set qty = ? where item_name = ? and expire_date = ?";
		PreparedStatement preStmt = connection.prepareStatement(updateQuery);
		preStmt.setInt(1, item.getQuantity());
		preStmt.setString(2, item.getName());
		preStmt.setDate(3, new Date(item.getExpireDate().getTimeInMillis()));
		preStmt.executeUpdate();
		preStmt.close();
	}

	public boolean deleteItemFromDb(Item item) {
		String deleteQuery = "delete from stock where item_name = ? and expire_date = ?";
		try(PreparedStatement preStmt = connection.prepareStatement(deleteQuery)){
			
			preStmt.setString(1, item.getName());
			preStmt.setDate(2, new Date(item.getExpireDate().getTimeInMillis()));
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
				item.setExpireDate(new CustomDate(result.getDate("expire_date")).getCalender());
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
	
	public LinkedList getexpireDatesList(String itemName){
		ResultSet result = null;
		LinkedList list = new LinkedList();
		try(Statement stmt = connection.createStatement()) {
			
			result = stmt.executeQuery("select expire_date from stock where item_name = "
					+ "'"+itemName+"'order by expire_date asc;");

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
			
			result = stmt.executeQuery("select item_name from stock where category = '"+category+"' order by item_name asc;");

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
	public int getItemQty(String itemName){
		ResultSet result = null;
		int qty = 0;
		try(Statement stmt = connection.createStatement()) {
			
			result = stmt.executeQuery("select qty from stock where item_name = '"+itemName+"';");

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
	public float getItemPrice(String itemName){
		ResultSet result = null;
		float qty = 0;
		try(Statement stmt = connection.createStatement()) {
			
			result = stmt.executeQuery("select price from stock where item_name = '"+itemName+"';");

			if(result.first())
				qty = result.getFloat("price");
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error in Stock table processing: "+e.getMessage());
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
			Date expireDate = new Date(item.getExpireDate().getTimeInMillis()); 
			String selectQuery = "select qty from stock where item_name = '"+item.getName()+"' and expire_date = '"+expireDate+"'";
			try(ResultSet result= connection.createStatement().executeQuery(selectQuery)){
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
