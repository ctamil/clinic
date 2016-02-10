package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import ds.LinkedList;
import dto.Bill;

public class BillTableProcessing extends TableProcessing{

	private Connection connection;
	
	public BillTableProcessing() {
		connection = DatabaseConnection.getConnection();
	}
	
	/**
	 * returns number of rows in bill table
	 */
	public int getSize() {
		return getSize("bill");
	}
	
	/**
	 * gets number of rows in bill table and returns puls one with that.
	 * @return 1 + bill table size;
	 */
	public int nextBillNo(){
		return getSize()+1;
	}
	
	public void addToDB(Bill bill){
		String insertQuery = "insert into bill (bill_no, patient_id, date, file, user_name) values(?, ?, ?, ?, ?);";
		try(PreparedStatement preStatement = connection.prepareStatement(insertQuery)){
			
			preStatement.setLong(1, bill.getBillNo());
			preStatement.setString(2, bill.getPatient().getId());
			preStatement.setDate(3, new java.sql.Date(System.currentTimeMillis()));
			preStatement.setString(4, bill.getFile());
			preStatement.setString(5, bill.getUser().getName());
			
			preStatement.executeUpdate();
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, "Error in Bill table processing: "+e.getMessage());
			e.printStackTrace();
		}
	}

	public LinkedList getBills(String patientId){
		String query = "select bill_no, date, file, user_name from bill where patient_id = '"+patientId+"' order by date asc";
		LinkedList list = new LinkedList();
		try(ResultSet result = connection.createStatement().executeQuery(query)){
			while(result.next()){
				Long billNo = result.getLong("bill_no");
				java.sql.Date date= result.getDate("date");
				String file = result.getString("file");
				String userName = result.getString("user_name");
				
				Bill bill = new Bill(billNo, file, date, new UserTableProcessing().getUser(userName));
				list.add(bill);
			}
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, "Error in Bill table processing: "+e.getMessage());
			e.printStackTrace();
		}
		return list;
	}
}
