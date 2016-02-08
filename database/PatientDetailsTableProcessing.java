package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

import javax.swing.JOptionPane;

import dto.Patient;
import dto.PatientDetails;

public class PatientDetailsTableProcessing {
	
	private Connection connection;
	
	public PatientDetailsTableProcessing() {
		connection = DatabaseConnection.getConnection();
	}

	public boolean addToDB(PatientDetails detail){
		String insertQuery = "INSERT into PATIENT_DETAILS (id, is_male, pin_code, "
				+ "address, city, state, dob, notes) values(?, ?, ?, ?, ?, ?, ?, ?);";
		try(PreparedStatement stmt = connection.prepareStatement(insertQuery)){
			
			stmt.setString(1, detail.getId());
			stmt.setBoolean(2, detail.getIsMale());
			stmt.setString(3, detail.getPinCode());
			stmt.setString(4, detail.getAddress());
			stmt.setString(5, detail.getCity());
			stmt.setString(6, detail.getState());
			stmt.setDate(7, detail.getDob());
			stmt.setString(8, detail.getNotes());
			
			stmt.executeUpdate();
		}catch(SQLException ex){
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error in saving to database: "+ex.getMessage());
			return false;
		}
		return true;
	}
	
	public void removeFromDb(PatientDetails patientDetails) {
		try(Statement stmt = connection.createStatement()) {
			stmt.executeUpdate("DELETE FROM PATIENT_DETAILS WHERE id = '"+patientDetails.getId()+"'");
		}catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error in Deleting Patient");
			e.printStackTrace();
		}
	}

	public void removeFromDb(Patient patient) {
		try(Statement stmt = connection.createStatement()) {
			stmt.executeUpdate("DELETE FROM PATIENT_DETAILS WHERE id = '"+patient.getId()+"'");
		}catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error in Deleting Patient: "+e.getMessage());
			e.printStackTrace();
		}
	}

	public PatientDetails getPatientDetailsById(String id){
		PatientDetails patientDetails = new PatientDetails();
		patientDetails.setId(id);
		
		ResultSet result = null;
		try(Statement stmt = connection.createStatement()) {
			result = stmt.executeQuery("select is_male, pin_code, address, city, state, dob, "
					+ "notes, father_name, mother_name from patient_details where id = '"+id+"';");
			if(result.first()){
				
				patientDetails.setIsMale(result.getBoolean("is_male"));
				patientDetails.setPinCode(result.getString("pin_code"));
				patientDetails.setAddress(result.getString("address"));
				patientDetails.setCity(result.getString("city"));
				patientDetails.setState(result.getString("state"));
				patientDetails.setDob(result.getDate("dob"));
				patientDetails.setNotes(result.getString("notes"));
				patientDetails.setFatherName(result.getString("father_name"));
				patientDetails.setMotherName(result.getString("mother_name"));
				
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error in Patien table processing: "
					+ ""+e.getMessage());
			e.printStackTrace();
		}
		return patientDetails;
	}
	
}
