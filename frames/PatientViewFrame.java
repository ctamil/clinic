package frames;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import dto.Patient;

import java.awt.Font;
import javax.swing.JScrollPane;

public class PatientViewFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 577606507866650948L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public PatientViewFrame(Patient patient) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 552, 494);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblName = new JLabel(patient.getName());
		lblName.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblName.setBounds(10, 11, 250, 28);
		panel.add(lblName);
		
		JLabel lblContact = new JLabel("Contact: "+patient.getContactNo());
		lblContact.setBounds(10, 50, 250, 14);
		panel.add(lblContact);
		
		JLabel lblAge = new JLabel("Age: "+patient.getAge());
		lblAge.setBounds(10, 80, 250, 14);
		panel.add(lblAge);
		
		JLabel lblGender = new JLabel("Gender: "+(patient.getIsMale() ? "Male" : "Female"));
		lblGender.setBounds(10, 110, 250, 14);
		panel.add(lblGender);
		
		JLabel label = new JLabel("Country: "+patient.getCountry());
		label.setBounds(10, 140, 250, 14);
		panel.add(label);
		
		JLabel label_1 = new JLabel("State: "+patient.getState());
		label_1.setBounds(10, 170, 250, 14);
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("District: "+patient.getCity());
		label_2.setBounds(10, 200, 250, 14);
		panel.add(label_2);
		
		JLabel label_3 = new JLabel("Pin Code: "+patient.getPinCode());
		label_3.setBounds(10, 230, 250, 14);
		panel.add(label_3);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 347, 506, 84);
		panel.add(scrollPane);
		
		JLabel label_5 = new JLabel("Additional Info: ");
		scrollPane.setColumnHeaderView(label_5);
		
		JLabel additionalInfo = new JLabel(patient.getNotes());
		scrollPane.setViewportView(additionalInfo);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 255, 506, 81);
		panel.add(scrollPane_1);
		
		JLabel label_4 = new JLabel(patient.getAddress());
		scrollPane_1.setViewportView(label_4);
		
		JLabel lblAddress = new JLabel("Address:");
		scrollPane_1.setColumnHeaderView(lblAddress);
	}
}
