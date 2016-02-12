package frames.patients;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dto.Patient;
import dto.PatientDetails;
import panels.register.RegisterationDetailsPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PatientEditFrame extends JFrame {

	private static final long serialVersionUID = -7048120901151460017L;
	private JPanel contentPane;
	private RegisterationDetailsPanel regDetailsPanel;

	/**
	 * Create the frame.
	 */
	public PatientEditFrame(Patient patient, PatientDetails patientDetails) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 774, 638);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		regDetailsPanel = new RegisterationDetailsPanel(patient, patientDetails);
		regDetailsPanel.setBounds(10, 11, 728, 529);
		panel.add(regDetailsPanel);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				regDetailsPanel.update();
			}
		});
		btnUpdate.setBounds(10, 551, 150, 23);
		panel.add(btnUpdate);
	}
}
