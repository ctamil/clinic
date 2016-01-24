package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.util.Calendar;

import javax.swing.JOptionPane;



import ds.Container;
import dto.Patient;

public class PatientTableProcessing {
	
	private Connection connection;
	
	public PatientTableProcessing() {
		connection = DatabaseConnection.getConnection();
	}
	
	public int getSize() {
		ResultSet result = null;
		try(Statement stmt = connection.createStatement()) {
			result = stmt.executeQuery("select count(contact_no) from patient;");
			if(result.first()) return result.getInt(1);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error in Patien table processing");
			e.printStackTrace();
		}
		return 0;
	}

	public boolean addToTable(Patient patient){
		String insertQuery = "insert into patient (name, is_male, contact_no, pin_code, "
				+ "address, city, state, country, dob, notes) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		try(PreparedStatement stmt = connection.prepareStatement(insertQuery)){
			
			stmt.setString(1, patient.getName());
			stmt.setBoolean(2, patient.getIsMale());
			stmt.setLong(3, patient.getContactNo());
			stmt.setString(4, patient.getPinCode());
			stmt.setString(5, patient.getAddress());
			stmt.setString(6, patient.getCity());
			stmt.setString(7, patient.getState());
			stmt.setString(8, patient.getCountry());
			stmt.setDate(9, patient.getDob());
			stmt.setString(10, patient.getNotes());
			
			stmt.executeUpdate();
		}catch(SQLException ex){
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error in saving to database");
			return false;
		}
		return true;
	}
	
	public void addToContainer(Container patients) {
		ResultSet result = null;
		try(Statement stmt = connection.createStatement()) {
			result = stmt.executeQuery("select * from patient;");
			while(result.next()){
				Patient pat = new Patient();
				
				pat.setName(result.getString("name"));
				pat.setIsMale(result.getBoolean("is_male"));
				pat.setContactNo(result.getLong("contact_no"));
				pat.setPinCode(result.getString("pin_code"));
				pat.setAddress(result.getString("address"));
				pat.setCity(result.getString("city"));
				pat.setState(result.getString("state"));
				pat.setCountry(result.getString("country"));
				Calendar date = Calendar.getInstance();
				date.setTimeInMillis(result.getDate("dob").getTime());
				pat.setDob(date);
				pat.setNotes(result.getString("notes"));
				
				patients.add(pat);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error in Patien table processing");
			e.printStackTrace();
		}
	}

	public void remove(Patient patient) {
		try(Statement stmt = connection.createStatement()) {
			stmt.executeUpdate("delete from patient where contact_no = '"+patient.getContactNo()+"'");
		}catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error in Deleting Patient");
			e.printStackTrace();
		}
	}

}
