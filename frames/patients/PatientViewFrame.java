package frames.patients;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JFrame;
import javax.swing.JLabel;

import dto.Patient;
import dto.PatientDetails;

import java.awt.Font;

import javax.swing.JScrollPane;

public class PatientViewFrame extends JFrame {

	private static final long serialVersionUID = 577606507866650948L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public PatientViewFrame(Patient patient, PatientDetails patientDetails) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 552, 494);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		add(contentPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblName = new JLabel(patient.getName());
		lblName.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblName.setBounds(10, 11, 250, 28);
		panel.add(lblName);
		
		JLabel lblContact = new JLabel("Contact: "+patient.getPhoneNumber());
		lblContact.setBounds(10, 100, 506, 14);
		panel.add(lblContact);
		
		JLabel lblAge = new JLabel("Age: "+patientDetails.getAge());
		lblAge.setBounds(10, 125, 506, 14);
		panel.add(lblAge);
		
		String value = patientDetails.getIsMale() == null ? "NULL" : 
			patientDetails.getIsMale() ? "Male" : "Female";
		JLabel lblGender = new JLabel("Gender: "+value);
		lblGender.setBounds(10, 155, 506, 14);
		panel.add(lblGender);
		
		JLabel label_1 = new JLabel("State: "+patientDetails.getState());
		label_1.setBounds(10, 180, 506, 14);
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("District: "+patientDetails.getCity());
		label_2.setBounds(10, 205, 506, 14);
		panel.add(label_2);
		
		JLabel label_3 = new JLabel("Pin Code: "+patientDetails.getPinCode());
		label_3.setBounds(10, 230, 506, 14);
		panel.add(label_3);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 347, 506, 84);
		panel.add(scrollPane);
		
		JLabel label_5 = new JLabel("Additional Info: ");
		scrollPane.setColumnHeaderView(label_5);
		
		JLabel additionalInfo = new JLabel(patientDetails.getNotes());
		scrollPane.setViewportView(additionalInfo);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 255, 506, 81);
		panel.add(scrollPane_1);
		
		JLabel label_4 = new JLabel(patientDetails.getAddress());
		scrollPane_1.setViewportView(label_4);
		
		JLabel lblAddress = new JLabel("Address:");
		scrollPane_1.setColumnHeaderView(lblAddress);
		
		JLabel lblFatherName = new JLabel("Father name: "+patientDetails.getFatherName());
		lblFatherName.setBounds(10, 50, 506, 14);
		panel.add(lblFatherName);
		
		JLabel lblMotherName = new JLabel("Mother name: "+patientDetails.getMotherName());
		lblMotherName.setBounds(10, 75, 506, 14);
		panel.add(lblMotherName);
	}
}
