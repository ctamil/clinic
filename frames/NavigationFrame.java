package frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class NavigationFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8446770981149789815L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public NavigationFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 345, 312);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnPatientRegister = new JButton("Patient Registeration ");
		btnPatientRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new RegisterationFrame().setVisible(true);
				dispose();
			}
		});
		btnPatientRegister.setBounds(40, 20, 250, 35);
		contentPane.add(btnPatientRegister);
		
		JButton btnCreateBill = new JButton("Create Bill");
		btnCreateBill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new BillingFrame().setVisible(true);
				dispose();
			}
		});
		btnCreateBill.setBounds(40, 70, 250, 35);
		contentPane.add(btnCreateBill);
		
		JButton btnPatientLookupTable = new JButton("Patient Lookup");
		btnPatientLookupTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new PatientSearchFrame().setVisible(true);
				dispose();
			}
		});
		btnPatientLookupTable.setBounds(40, 120, 250, 35);
		contentPane.add(btnPatientLookupTable);
		
		JButton btnAvailableStock = new JButton("Available Stock");
		btnAvailableStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AvailableStockFrame().setVisible(true);
				dispose();
			}
		});
		btnAvailableStock.setBounds(40, 170, 250, 35);
		contentPane.add(btnAvailableStock);
		
		JButton btnAddStock = new JButton("Add Stock");
		btnAddStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddStockFrame().setVisible(true);
				dispose();
			}
		});
		btnAddStock.setBounds(40, 220, 250, 35);
		contentPane.add(btnAddStock);
	}
}
