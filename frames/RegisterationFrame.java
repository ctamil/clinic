package frames;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

import storage.PatientInfo;
import dto.Patient;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.regex.Pattern;

public class RegisterationFrame extends JFrame {


	private static final long serialVersionUID = 4520649564739812454L;
	private JPanel contentPane;
	private JPanel panel;
	private JTextField txtName;
	private JTextField txtDaymonthyear;
	private JTextField txtContact;
	private JTextField txtCountry;
	private JTextField txtState;
	private JTextField txtCity;
	private ButtonGroup gender;
	private JTextArea textAddress;
	private JTextArea textInfo;
	private JRadioButton rdbtnMale;
	private JRadioButton rdbtnFemale;
	private JTextField txtPinCode;
	private JButton btnBack;
	private JButton btnReset;

	/**
	 * Create the frame.
	 */
	public RegisterationFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 552, 641);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblPatientRegisterationForm = new JLabel("Patient Registration Form");
		lblPatientRegisterationForm.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPatientRegisterationForm.setBounds(20, 10, 221, 14);
		panel.add(lblPatientRegisterationForm);
		
		JLabel lblName = new JLabel("Name: ");
		lblName.setBounds(20, 50, 151, 14);
		panel.add(lblName);
		
		JLabel lblDateOfBirth = new JLabel("Date Of Birth: ");
		lblDateOfBirth.setBounds(20, 80, 151, 14);
		panel.add(lblDateOfBirth);
		
		JLabel lblGender = new JLabel("Gender: ");
		lblGender.setBounds(20, 110, 151, 14);
		panel.add(lblGender);
		
		JLabel lblNewLabel = new JLabel("Contact No: ");
		lblNewLabel.setBounds(20, 140, 151, 14);
		panel.add(lblNewLabel);
		
		JLabel lblCountry = new JLabel("Country: ");
		lblCountry.setBounds(20, 170, 151, 14);
		panel.add(lblCountry);
		
		JLabel lblState = new JLabel("State: ");
		lblState.setBounds(20, 200, 151, 14);
		panel.add(lblState);
		
		JLabel lblDistric = new JLabel("District");
		lblDistric.setBounds(20, 230, 151, 14);
		panel.add(lblDistric);
		
		JLabel lblPinCode = new JLabel("Pin Code: ");
		lblPinCode.setBounds(20, 260, 151, 14);
		panel.add(lblPinCode);
		
		JLabel lblAddress = new JLabel("Address: ");
		lblAddress.setBounds(20, 290, 151, 14);
		panel.add(lblAddress);
		
		JLabel lblAdditionInfo = new JLabel("Addition Info: ");
		lblAdditionInfo.setBounds(20, 405, 151, 14);
		panel.add(lblAdditionInfo);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(isValidUserData()) 
					if(register()){
						JOptionPane.showMessageDialog(panel, "Registeration Completed");
						reset();
					}
				else JOptionPane.showMessageDialog(null, "Registration Not Completed");
			}
		});
		btnRegister.setBounds(20, 553, 138, 23);
		panel.add(btnRegister);
		
		txtName = new JTextField();
		txtName.setBounds(219, 47, 237, 20);
		panel.add(txtName);
		txtName.setColumns(10);
		
		txtDaymonthyear = new JTextField();
		txtDaymonthyear.setText("");
		txtDaymonthyear.setToolTipText("Day.Month.Year");
		txtDaymonthyear.setBounds(219, 77, 237, 20);
		panel.add(txtDaymonthyear);
		txtDaymonthyear.setColumns(10);
		
		rdbtnMale = new JRadioButton("Male");
		rdbtnMale.setBounds(219, 104, 56, 23);
		panel.add(rdbtnMale);
		rdbtnMale.setSelected(true);
		
		rdbtnFemale = new JRadioButton("Female");
		rdbtnFemale.setBounds(285, 104, 87, 23);
		panel.add(rdbtnFemale);
		
		gender = new ButtonGroup();
		gender.add(rdbtnMale);
		gender.add(rdbtnFemale);
		
		txtContact = new JTextField();
		txtContact.setBounds(219, 136, 237, 20);
		panel.add(txtContact);
		txtContact.setColumns(10);
		
		txtCountry = new JTextField();
		txtCountry.setText("India");
		txtCountry.setBounds(219, 166, 237, 20);
		panel.add(txtCountry);
		txtCountry.setColumns(10);
		
		txtState = new JTextField();
		txtState.setText("Tamil Nadu");
		txtState.setBounds(219, 196, 237, 20);
		panel.add(txtState);
		txtState.setColumns(10);
		
		txtCity = new JTextField();
		txtCity.setText("Coimbatore");
		txtCity.setBounds(219, 226, 237, 20);
		panel.add(txtCity);
		txtCity.setColumns(10);
		
		textAddress = new JTextArea();
		textAddress.setBounds(219, 284, 237, 100);
		panel.add(textAddress);
		
		textInfo = new JTextArea();
		textInfo.setBounds(219, 399, 237, 100);
		panel.add(textInfo);
		
		txtPinCode = new JTextField();
		txtPinCode.setColumns(10);
		txtPinCode.setBounds(219, 256, 237, 20);
		panel.add(txtPinCode);
		
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new NavigationFrame().setVisible(true);
				dispose();
			}
		});
		btnBack.setBounds(342, 553, 114, 23);
		panel.add(btnBack);
		
		btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		btnReset.setBounds(181, 553, 138, 23);
		panel.add(btnReset);
		
		
	}
	
	private void reset(){
		dispose();
		new RegisterationFrame().setVisible(true);
	}
	
	private boolean register() {
		Patient patient = new Patient();
		
		patient.setName(txtName.getText().trim());
		
		patient.setDob(getDate());
		
		if(rdbtnMale.isSelected()) patient.setIsMale(true);
		else if(rdbtnFemale.isSelected()) patient.setIsMale(false);
		
		patient.setContactNo(Long.parseLong(txtContact.getText().trim()));
		patient.setCountry(txtCountry.getText().trim());
		patient.setState(txtState.getText());
		patient.setCity(txtCity.getText());
		patient.setAddress(textAddress.getText());
		patient.setNotes(textInfo.getText());
		patient.setPinCode(txtPinCode.getText());
		
		return PatientInfo.getInstance().add(patient);
	} 
	
	private Calendar getDate() {
		int date[] = splitToDate(txtDaymonthyear.getText().trim());
		Calendar cal = Calendar.getInstance();
		cal.set(date[0], date[1], date[2]);
		return cal;
	}

	private int[] splitToDate(String string) {
		int date[] = new int[3];
		date[2] = Integer.parseInt(string.substring(0, 2)); //day
		date[1] = Integer.parseInt(string.substring(3, 5))-1; //month starts with 0.
		date[0] = Integer.parseInt(string.substring(6, 10)); //year
		return date;
	}

	private boolean isValidUserData() {
		if(txtName.getText() == null || txtName.getText().length() < 1) return returnFalseWithMessage("Enter patient name");
		
		String date = txtDaymonthyear.getText();
		if(date == null) return returnFalseWithMessage("Enter Date Of Birth");
		Pattern datePattern = Pattern.compile("^[0123][0-9][.][012][0-9][.][0-9]{4}$");
		if(!datePattern.matcher(date.trim()).find()) return returnFalseWithMessage("Enter Date if format: (DD.MM.YYYY)");
		Calendar cal = Calendar.getInstance();
		if(cal.compareTo(getDate()) <= 0) return returnFalseWithMessage("Enter valid date");   
		
		String contact = txtContact.getText();
		if(contact == null) return returnFalseWithMessage("Enter Contact Number");
		contact = contact.trim();
		Pattern contactPattern = Pattern.compile("^[1-9][0-9]{9}$");
		if(!contactPattern.matcher(contact.trim()).find()) return returnFalseWithMessage("Found Invalid Contact number");
		if(PatientInfo.getInstance().contains(contact)) return returnFalseWithMessage("Number is already registered in system.");
		if(txtCountry.getText() == null || txtState.getText() == null || txtCity.getText() == null || txtPinCode.getText() == null ||
				txtCountry.getText().length() < 1 || txtState.getText().length() < 1 || txtCity.getText().length() < 1 || 
				txtPinCode.getText().length() < 1)
			return returnFalseWithMessage("Registeration not completed, Missing Datas");
			
		return true;
	}
	
	private boolean returnFalseWithMessage(String message){
		JOptionPane.showMessageDialog(this, message);
		return false;
	}
}
