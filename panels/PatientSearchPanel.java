package panels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import storage.PatientInfo;
import ds.Traveller;
import dto.Patient;
import dto.PatientDetails;
import frames.patients.PatientEditFrame;
import frames.patients.PatientHistoryFrame;
import immunization.ImmunizationManager;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;

public class PatientSearchPanel extends JPanel {

	private static final long serialVersionUID = -2193316112347185479L;
	private JTextField textField;
	private JTable table;
	private JComboBox<String> comboBox;
	private static final String[] COLUMNS = {"Registeration Id", "Phone Number", "Name"}; 

	/**
	 * Create the frame.
	 */
	public PatientSearchPanel() {
		setBounds(100, 100, 669, 600);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblPatientPhoneNumber = new JLabel("Search By");
		lblPatientPhoneNumber.setBounds(10, 11, 78, 14);
		panel.add(lblPatientPhoneNumber);
		
		textField = new JTextField();
		textField.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				searchAndUpdate();
			}
		});
		textField.setBounds(250, 8, 210, 20);
		panel.add(textField);
		textField.setColumns(10);
		textField.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "Enter");
		textField.getActionMap().put("Enter", new AbstractAction() {
			private static final long serialVersionUID = -5250939285538217812L;
			@Override
			public void actionPerformed(ActionEvent arg0) {
				searchAndUpdate();
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 53, 601, 495);
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				searchAndUpdate();
			}
		});
		btnSearch.setBounds(481, 7, 130, 23);
		panel.add(btnSearch);
		
		JButton btnDeletePatient = new JButton("View And Edit ");
		btnDeletePatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				edit();
			}
		});
		btnDeletePatient.setBounds(10, 559, 130, 23);
		panel.add(btnDeletePatient);
		
		comboBox = new JComboBox<>();
		comboBox.setModel(new DefaultComboBoxModel<String>(COLUMNS));
		comboBox.setBounds(84, 8, 147, 20);
		panel.add(comboBox);
		
		JButton btnImmunizationStatus = new JButton("Immunization Status");
		btnImmunizationStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = null;
				if(table.getSelectedRow() >= 0) {
					id = table.getValueAt(table.getSelectedRow(), 0).toString();
					new ImmunizationManager(id).openOutFile();
				}				
			}
		});
		btnImmunizationStatus.setBounds(308, 559, 165, 23);
		panel.add(btnImmunizationStatus);
		
		JButton btnPatientHistory = new JButton("Patient History");
		btnPatientHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = null;
				if(table.getSelectedRow() >= 0) {
					id = table.getValueAt(table.getSelectedRow(), 0).toString();
					new PatientHistoryFrame(id).setVisible(true);
				}
			}
		});
		btnPatientHistory.setBounds(159, 559, 126, 23);
		panel.add(btnPatientHistory);
	}

	/*private void delete() {
		if(table.getSelectedRow() != -1){
			int option = JOptionPane.showConfirmDialog(null, "Are You Sure Want to Delete.");
			if(option == JOptionPane.NO_OPTION) return;
			String key = table.getModel().getValueAt(table.getSelectedRow(), 0).toString();
			Patient patient = PatientInfo.getInstance().getPatient(key);
			PatientInfo.getInstance().delete(patient);
			searchAndUpdate();
		}else{
			JOptionPane.showMessageDialog(this, "Select a row from table to delete");
		}
	}*/
	private void edit(){
		if(table.getSelectedRow() != -1){
			String key = table.getModel().getValueAt(table.getSelectedRow(), 0).toString();
			Patient patient = PatientInfo.getInstance().getPatient(key);
			PatientDetails patientDetails = PatientInfo.getInstance().getPatientDetails(patient);
			System.out.println("Edit func called: "+key);
			new PatientEditFrame(patient, patientDetails).setVisible(true);
		}else{
			JOptionPane.showMessageDialog(this, "Select a row from table to delete");
		}
	}

	private void searchAndUpdate() {
		if(isValidUserData()) {
			String val = textField.getText();
			updateTable(val, comboBox.getSelectedItem().toString().trim().toLowerCase());
		}
	}

	private void updateTable(String val, String type) {
		Traveller patientTraveller = PatientInfo.getInstance().searchPatient(val, type);
		table.setModel(new DefaultTableModel(COLUMNS, patientTraveller.size()));
		for(int i=0; i<patientTraveller.size(); i++){
			Patient next = (Patient) patientTraveller.next();
			table.getModel().setValueAt(next.getId(), i, 0);
			table.getModel().setValueAt(next.getPhoneNumber(), i, 1);
			table.getModel().setValueAt(next.getName(), i, 2);
		}
	}
	
	private boolean isValidUserData() {
		if(textField.getText() == null || textField.getText().length() < 1) return false;
		return true;
	}
}
