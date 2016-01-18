package database;

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

	
	public void addToContainer(Container patients) {
		ResultSet result = null;
		try(Statement stmt = connection.createStatement()) {
			result = stmt.executeQuery("select * from patient;");
			while(result.next()){
				Patient pat = new Patient();
				
				pat.setName(result.getString("name"));
				pat.setIsMale(result.getBoolean("is_male"));
				pat.setContactNo(result.getLong("contact_no"));
				pat.setPinCode(result.getInt("pin_code"));
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

}
