package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import ds.LinkedList;

import javax.swing.JOptionPane;

import ds.Container;
import dto.Patient;

public class PatientTableProcessing extends TableProcessing{

private Connection connection;
	
	public PatientTableProcessing() {
		connection = DatabaseConnection.getConnection();
	}
	
	public int getSize() {
		return getSize("patient");
	}

	public boolean addToDB(Patient patient){
		String insertQuery = "insert into patient (id, name, phone_number) "
				+ "values(?, ?, ?);";
		try(PreparedStatement stmt = connection.prepareStatement(insertQuery)){
			
			stmt.setString(1, patient.getId());
			stmt.setString(2, patient.getName());
			stmt.setString(3, patient.getPhoneNumber());
			
			stmt.executeUpdate();
		}catch(SQLException ex){
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error in saving to database: "
					+ ""+ex.getMessage());
			return false;
		}
		return true;
	}

	public void removeFromDB(Patient patient) {
		try(Statement stmt = connection.createStatement()) {
			stmt.executeUpdate("DELETE FROM PATIENT WHERE id = '"+patient.getId()+"'");
		}catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error in Deleting Patient: "
					+ ""+e.getMessage());
			e.printStackTrace();
		}
	}

	public Container getList() {
		LinkedList patients = new LinkedList();
		ResultSet result = null;
		try(Statement stmt = connection.createStatement()) {
			result = stmt.executeQuery("select id, name, phone_number from patient;");
			while(result.next()){
				Patient pat = new Patient();
				
				pat.setId(result.getString("id"));
				pat.setName(result.getString("name"));
				pat.setPhoneNumber(result.getString("phone_number"));
				
				patients.add(pat);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error in Patien table processing: "
					+ ""+e.getMessage());
			e.printStackTrace();
		}
		return patients;
	}
	
	/**
	 * HKC(hexa decimal 4 digit sequence)(last two digit of year)
	 * @return example : HKC00A116
	 */
	public String getNextId(){
		StringBuilder id = new StringBuilder("HKC");
		StringBuilder sequence = new StringBuilder(Integer.toHexString(getSize()+1));
		while(sequence.length() < 4) sequence.insert(0, 0);
		id.append(sequence);
		String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR))
				.substring(2, 4);
		id.append(year);
		return id.toString().toUpperCase();
	}

	public LinkedList searchPatients(String name){
		LinkedList patients = new LinkedList();
		ResultSet result = null;
		try(Statement stmt = connection.createStatement()) {
			result = stmt.executeQuery("select id, name, phone_number from patient where name LIKE '"+name+"%';");
			while(result.next()){
				Patient pat = new Patient();
				
				pat.setId(result.getString("id"));
				pat.setName(result.getString("name"));
				pat.setPhoneNumber(result.getString("phone_number"));
				
				patients.add(pat);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error in Patien table processing: "
					+ ""+e.getMessage());
			e.printStackTrace();
		}
		return patients;
	}

	public void updateToDB(Patient patient) {
		String updateQuery = "UPDATE patient set name = ?, phone_number = ? where id = ?;";
		try(PreparedStatement pStmt = connection.prepareStatement(updateQuery)){
			
			pStmt.setString(1, patient.getName());
			pStmt.setString(2, patient.getPhoneNumber());
			pStmt.setString(3, patient.getId());
			pStmt.executeUpdate();
			
		}catch(SQLException ex){
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error in saving to database: "
					+ ""+ex.getMessage());
		}
	}
	
	

}
