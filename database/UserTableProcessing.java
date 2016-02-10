package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import ds.LinkedList;
import dto.User;

public class UserTableProcessing extends TableProcessing{
	
	private Connection connection;

	public UserTableProcessing() {
		connection = DatabaseConnection.getConnection();
	}
	
	public int getSize(){
		return getSize("user");
	}
	
	public LinkedList userList(){
		ResultSet result = null;
		LinkedList list = new LinkedList();
		try(Statement stmt = connection.createStatement()) {
			
			result = stmt.executeQuery("select name, is_admin from user");

			while(result.next()){
				String name = result.getString("name");
				Boolean isAdmin = result.getBoolean("is_admin");
				list.add(new User(name, isAdmin));
			}
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error in User table processing: "+e.getMessage());
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean isValidUser(String name, String password){
		String query = "select password from user where name = '"+name+"';";
		try(ResultSet result = connection.createStatement().executeQuery(query)){
			if(result.first()) if(result.getString("password").equals(password)) return true;
		}catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error in User table processing: "+e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean isAdmin(String name){
		String query = "select is_admin from user where name = '"+name+"';";
		try(ResultSet result = connection.createStatement().executeQuery(query)){
			if(result.first()) return result.getBoolean("is_admin");
		}catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error in User table processing: "+e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean addToDB(User user){
		String query = "insert into user (name, password, is_admin) values(?, ?, ?)";
		try(PreparedStatement preStmt = connection.prepareStatement(query)) {
			
			preStmt.setString(1, user.getName());
			preStmt.setString(2, user.getPassword());
			preStmt.setBoolean(3, user.isAdmin());
			preStmt.executeUpdate();
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error in User table processing: "+e.getMessage());
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean contains(String userName) {
		String query = "select name from user where name = '"+userName+"';";
		try(ResultSet result = connection.createStatement().executeQuery(query)){
			if(result.first()) return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error in User table processing: "+e.getMessage());
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public void delete(String userName) {
		String query = "delete from user where name = '"+userName+"'";
		try(Statement stmt = connection.createStatement()){
			stmt.executeUpdate(query);
		}catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error in User table processing: "+e.getMessage());
			e.printStackTrace();
		}
	}

	public User getUser(String userName) {
		String query = "select is_admin from user where name = '"+userName+"'";
		try(ResultSet result = connection.createStatement().executeQuery(query)){
			if(result.first()){
				boolean isAdmin = result.getBoolean("is_admin");
				User user = new User(userName, isAdmin);
				return user;
			}
		}catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error in User table processing: "+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}
