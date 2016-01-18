package frames;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

public class Register extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4520649564739812454L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField txtDaymonthyear;
	private JTextField textField_1;
	private JTextField txtIndia;
	private JTextField txtTamilNadu;
	private JTextField txtCoimbatore;
	private ButtonGroup gender;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Register frame = new Register();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Register() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 593, 605);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblPatientRegisterationForm = new JLabel("Patient Registeration Form");
		lblPatientRegisterationForm.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPatientRegisterationForm.setBounds(20, 10, 221, 14);
		panel.add(lblPatientRegisterationForm);
		
		JLabel lblName = new JLabel("Name: ");
		lblName.setBounds(20, 50, 80, 14);
		panel.add(lblName);
		
		JLabel lblDateOfBirth = new JLabel("Date Of Birth: ");
		lblDateOfBirth.setBounds(20, 80, 80, 14);
		panel.add(lblDateOfBirth);
		
		JLabel lblAge = new JLabel("Age: ");
		lblAge.setBounds(391, 80, 80, 14);
		panel.add(lblAge);
		
		JLabel lblGender = new JLabel("Gender: ");
		lblGender.setBounds(20, 110, 80, 14);
		panel.add(lblGender);
		
		JLabel lblNewLabel = new JLabel("Contact No: ");
		lblNewLabel.setBounds(20, 140, 83, 14);
		panel.add(lblNewLabel);
		
		JLabel lblCountry = new JLabel("Country: ");
		lblCountry.setBounds(20, 170, 46, 14);
		panel.add(lblCountry);
		
		JLabel lblState = new JLabel("State: ");
		lblState.setBounds(20, 200, 46, 14);
		panel.add(lblState);
		
		JLabel lblDistric = new JLabel("District");
		lblDistric.setBounds(20, 230, 80, 14);
		panel.add(lblDistric);
		
		JLabel lblAddress = new JLabel("Address: ");
		lblAddress.setBounds(20, 260, 80, 14);
		panel.add(lblAddress);
		
		JLabel lblAdditionInfo = new JLabel("Addition Info: ");
		lblAdditionInfo.setBounds(20, 378, 80, 14);
		panel.add(lblAdditionInfo);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.setBounds(20, 501, 80, 23);
		panel.add(btnRegister);
		
		textField = new JTextField();
		textField.setBounds(127, 47, 237, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		txtDaymonthyear = new JTextField();
		txtDaymonthyear.setText("Day.Month.Year");
		txtDaymonthyear.setToolTipText("Day.Month.Year");
		txtDaymonthyear.setBounds(127, 77, 237, 20);
		panel.add(txtDaymonthyear);
		txtDaymonthyear.setColumns(10);
		
		JRadioButton rdbtnMale = new JRadioButton("Male");
		rdbtnMale.setBounds(127, 106, 56, 23);
		panel.add(rdbtnMale);
		
		JRadioButton rdbtnFemale = new JRadioButton("Female");
		rdbtnFemale.setBounds(193, 106, 87, 23);
		panel.add(rdbtnFemale);
		
		gender = new ButtonGroup();
		gender.add(rdbtnMale);
		gender.add(rdbtnFemale);
		
		textField_1 = new JTextField();
		textField_1.setBounds(127, 137, 237, 20);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		txtIndia = new JTextField();
		txtIndia.setText("India");
		txtIndia.setBounds(127, 167, 237, 20);
		panel.add(txtIndia);
		txtIndia.setColumns(10);
		
		txtTamilNadu = new JTextField();
		txtTamilNadu.setText("Tamil Nadu");
		txtTamilNadu.setBounds(127, 197, 237, 20);
		panel.add(txtTamilNadu);
		txtTamilNadu.setColumns(10);
		
		txtCoimbatore = new JTextField();
		txtCoimbatore.setText("Coimbatore");
		txtCoimbatore.setBounds(127, 227, 237, 20);
		panel.add(txtCoimbatore);
		txtCoimbatore.setColumns(10);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(127, 255, 237, 100);
		panel.add(textArea);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(127, 373, 237, 100);
		panel.add(textArea_1);
	}
}
