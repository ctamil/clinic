package frames;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;

import storage.PatientInfo;
import ds.Traveller;
import dto.Patient;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.regex.Pattern;

public class PatientSearchFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2193316112347185479L;
	private JPanel contentPane;
	private JTextField textField;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PatientSearchFrame frame = new PatientSearchFrame();
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
	public PatientSearchFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 552, 641);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblPatientPhoneNumber = new JLabel("Patient Phone Number");
		lblPatientPhoneNumber.setBounds(10, 11, 151, 14);
		panel.add(lblPatientPhoneNumber);
		
		textField = new JTextField();
		textField.setBounds(166, 8, 210, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 47, 506, 495);
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				searchAndUpdate();
			}
		});
		btnSearch.setBounds(386, 7, 130, 23);
		panel.add(btnSearch);
		
		JButton btnViewPatientDetails = new JButton("View Patient Details");
		btnViewPatientDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String number = table.getModel().getValueAt(table.getSelectedRow(), 0).toString();
				new PatientViewFrame(PatientInfo.getInstance().get(number)).setVisible(true);
			}
		});
		btnViewPatientDetails.setBounds(10, 559, 191, 23);
		panel.add(btnViewPatientDetails);
		
		JButton btnDeletePatient = new JButton("Delete Patient");
		btnDeletePatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(isValidUserData()){
					String number = table.getModel().getValueAt(table.getSelectedRow(), 0).toString();
					Patient patient = PatientInfo.getInstance().get(number);
					PatientInfo.getInstance().delete(patient);
					searchAndUpdate();
				}else{
					JOptionPane.showMessageDialog(contentPane, "Invalid Input");
				}
			}
		});
		btnDeletePatient.setBounds(211, 559, 130, 23);
		panel.add(btnDeletePatient);
	}

	private void searchAndUpdate() {
		if(isValidUserData()) {
			String number = textField.getText();
			updateTable(number);
		}else{
			JOptionPane.showMessageDialog(contentPane, "Invalid Input");
		}
	}

	private void updateTable(String number) {
		Traveller patientTraveller = PatientInfo.getInstance().prefix(number);
		table.setModel(new DefaultTableModel(new String[]{"Number", "Name"}, patientTraveller.size()));
		for(int i=0; i<patientTraveller.size(); i++){
			Patient next = (Patient) patientTraveller.next();
			table.getModel().setValueAt(next.getContactNo(), i, 0);
			table.getModel().setValueAt(next.getName(), i, 1);
		}
	}
	
	private boolean isValidUserData() {
		if(textField.getText() == null || textField.getText().length() < 1 || textField.getText().length() > 10) return false;
		String number = textField.getText();
		Pattern contactPattern = Pattern.compile("^[1-9][0-9]*$");
		return contactPattern.matcher(number).find();
	}
}