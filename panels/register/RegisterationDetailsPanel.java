package panels.register;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.util.Calendar;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import database.PatientTableProcessing;
import dto.CustomDate;
import dto.Patient;
import dto.PatientDetails;
import panels.DatePanel;
import storage.PatientInfo;

import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class RegisterationDetailsPanel extends JPanel {

	private static final long serialVersionUID = -5925247410239156802L;
	private JTextField txtName;
	private JTextField txtContact;
	private JTextField txtState;
	private JTextField txtCity;
	private JTextField txtPinCode;
	private JTextField txtMother;
	private JTextField txtFather;
	private JTextArea textInfo;
	private JTextArea textAddress;
	private DatePanel datePanel;
	private JRadioButton rdbtnFemale;
	private JRadioButton rdbtnMale;
	private ButtonGroup gender;
	

	public RegisterationDetailsPanel() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblName = new JLabel("Name *: ");
		lblName.setBounds(10, 14, 151, 14);
		panel.add(lblName);
		
		JLabel lblDateOfBirth = new JLabel("Date Of Birth *: ");
		lblDateOfBirth.setBounds(10, 118, 151, 14);
		panel.add(lblDateOfBirth);
		
		JLabel label_2 = new JLabel("Gender: ");
		label_2.setBounds(10, 162, 151, 14);
		panel.add(label_2);
		
		JLabel lblContactNo = new JLabel("Contact No *: ");
		lblContactNo.setBounds(10, 191, 151, 14);
		panel.add(lblContactNo);
		
		JLabel label_4 = new JLabel("State: ");
		label_4.setBounds(10, 367, 151, 14);
		panel.add(label_4);
		
		JLabel label_5 = new JLabel("District:");
		label_5.setBounds(10, 336, 151, 14);
		panel.add(label_5);
		
		JLabel label_6 = new JLabel("Pin Code: ");
		label_6.setBounds(10, 396, 151, 14);
		panel.add(label_6);
		
		JLabel label_7 = new JLabel("Address: ");
		label_7.setBounds(10, 221, 151, 14);
		panel.add(label_7);
		
		JLabel label_8 = new JLabel("Addition Info: ");
		label_8.setBounds(10, 421, 151, 14);
		panel.add(label_8);
		
		txtName = new JTextField();
		txtName.setColumns(10);
		txtName.setBounds(209, 11, 307, 20);
		panel.add(txtName);
		
		rdbtnMale = new JRadioButton("Male");
		rdbtnMale.setBounds(210, 158, 56, 23);
		panel.add(rdbtnMale);
		
		rdbtnFemale = new JRadioButton("Female");
		rdbtnFemale.setBounds(276, 158, 87, 23);
		panel.add(rdbtnFemale);
		
		gender = new ButtonGroup();
		gender.add(rdbtnMale);
		gender.add(rdbtnFemale);
		
		txtContact = new JTextField();
		txtContact.setColumns(10);
		txtContact.setBounds(209, 187, 307, 20);
		panel.add(txtContact);
		
		txtState = new JTextField();
		txtState.setText("Tamil Nadu");
		txtState.setColumns(10);
		txtState.setBounds(209, 363, 307, 20);
		panel.add(txtState);
		
		txtCity = new JTextField();
		txtCity.setText("Coimbatore");
		txtCity.setColumns(10);
		txtCity.setBounds(209, 332, 307, 20);
		panel.add(txtCity);
		
		txtPinCode = new JTextField();
		txtPinCode.setColumns(10);
		txtPinCode.setBounds(209, 392, 307, 20);
		panel.add(txtPinCode);
		
		datePanel = DatePanel.getInstance();
		datePanel.setBounds(209, 105, 415, 39);
		panel.add(datePanel);
		
		JLabel label_9 = new JLabel("Mother Name: ");
		label_9.setBounds(10, 45, 138, 14);
		panel.add(label_9);
		
		txtMother = new JTextField();
		txtMother.setColumns(10);
		txtMother.setBounds(209, 42, 307, 20);
		panel.add(txtMother);
		
		JLabel label_10 = new JLabel("Father Name: ");
		label_10.setBounds(10, 80, 151, 14);
		panel.add(label_10);
		
		txtFather = new JTextField();
		txtFather.setColumns(10);
		txtFather.setBounds(209, 73, 307, 20);
		panel.add(txtFather);
		
		JScrollPane addressscrollPane = new JScrollPane();
		addressscrollPane.setBounds(209, 221, 307, 100);
		panel.add(addressscrollPane);
		
		textAddress = new JTextArea();
		addressscrollPane.setViewportView(textAddress);
		
		JScrollPane infoScrollPane = new JScrollPane();
		infoScrollPane.setBounds(209, 421, 307, 100);
		panel.add(infoScrollPane);
		
		textInfo = new JTextArea();
		infoScrollPane.setViewportView(textInfo);
		
	}

	public RegisterationDetailsPanel(Patient patient, PatientDetails patientDetails) {
		this();
		if(patient.getName() != null) txtName.setText(patient.getName());
		if(patientDetails.getIsMale()) rdbtnMale.setSelected(true);
		else rdbtnFemale.setSelected(true);
		if(patient.getPhoneNumber() != null) txtContact.setText(patient.getPhoneNumber());
		if(patientDetails.getState() != null) txtState.setText(patientDetails.getState());
		if(patientDetails.getCity() != null) txtCity.setText(patientDetails.getCity());
		if(patientDetails.getPinCode() != null) txtPinCode.setText(patientDetails.getPinCode());
		if(patientDetails.getDob() != null) datePanel.setDate(new CustomDate(patientDetails.getDob()));
		if(patientDetails.getMotherName() != null) txtMother.setText(patientDetails.getMotherName());
		if(patientDetails.getFatherName() != null) txtFather.setText(patientDetails.getFatherName());
		if(patientDetails.getAddress() != null) textAddress.setText(patientDetails.getAddress());
		if(patientDetails.getNotes() != null) textInfo.setText(patientDetails.getNotes());
	}

	public void reset(){
		txtName.setText("");
		txtMother.setText("");
		txtFather.setText("");
		datePanel.reset();
		txtContact.setText("");
		textAddress.setText("");
		textInfo.setText("");
		txtCity.setText("");
		txtState.setText("");
		txtPinCode.setText("");
	}
	
	public boolean update(String regId){
		if(!isValidUserData()) return false;
		Patient patient = getPatient(regId);
		PatientDetails patientDetails = getPatientDetails(patient);
		return PatientInfo.getInstance().update(patient, patientDetails);
	}
	
	public boolean register() {
		if(!isValidUserData()) return false;
		
		String contact = txtContact.getText().trim();
		if(PatientInfo.getInstance().contains(contact)) return returnFalseWithMessage("Number is already registered in system.");
		
		Patient patient = getPatient(null);
		PatientDetails patientDetails = getPatientDetails(patient);
		JOptionPane.showMessageDialog(this, "Registeration ID: "+patient.getId());
		return PatientInfo.getInstance().add(patient, patientDetails);
	}
	
	private Patient getPatient(String regId){
		Patient patient = new Patient();
		if(regId == null) patient.setId(new PatientTableProcessing().getNextId()); //create new reg id.
		else patient.setId(regId);
		patient.setName(txtName.getText().trim());
		patient.setPhoneNumber(txtContact.getText());
		return patient;
	}
	
	private PatientDetails getPatientDetails(Patient patient){
		PatientDetails patientDetails = new PatientDetails();
		patientDetails.setId(patient.getId());
		if(rdbtnMale.isSelected()) patientDetails.setIsMale(true);
		else if(rdbtnFemale.isSelected()) patientDetails.setIsMale(false);
		patientDetails.setDob(datePanel.getDate().getSQLDate());
		patientDetails.setFatherName(txtFather.getText());
		patientDetails.setMotherName(txtMother.getText());
		patientDetails.setState(txtState.getText());
		patientDetails.setCity(txtCity.getText());
		patientDetails.setAddress(textAddress.getText());
		patientDetails.setNotes(textInfo.getText());
		patientDetails.setPinCode(txtPinCode.getText());
		return patientDetails;
	}
	
	private boolean isValidUserData() {
		if(txtName.getText() == null || txtName.getText().length() < 1) return returnFalseWithMessage("Enter patient name");
		
		Calendar date = datePanel.getDate().getCalenderDate();
		if(date == null || Calendar.getInstance().compareTo(date) <= 0) return returnFalseWithMessage("Enter valid date");   
		
		String contact = txtContact.getText();
		if(contact == null) return returnFalseWithMessage("Enter Contact Number");
		contact = contact.trim();
		Pattern contactPattern = Pattern.compile("^[1-9][0-9]{9}$");
		if(!contactPattern.matcher(contact.trim()).find()) return returnFalseWithMessage("Found Invalid Contact number");

		return true;
	}
	
	private boolean returnFalseWithMessage(String message){
		JOptionPane.showMessageDialog(this, message);
		return false;
	}
}
