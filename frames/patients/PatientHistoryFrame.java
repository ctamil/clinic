package frames.patients;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import database.BillTableProcessing;
import ds.LinkedList;
import ds.Traveller;
import dto.Bill;
import dto.CustomDate;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;

import utils.FileRunner;
import utils.LocalNetworkFileSearch;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PatientHistoryFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1576856753090519630L;
	private JPanel contentPane;
	private JTable table;
	private String[] COLUMNS = {"Bill No", "Date", "File"};
	private BillTableProcessing billTable;

	/**
	 * Create the frame.
	 */
	public PatientHistoryFrame(String patientId) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		billTable = new BillTableProcessing();
		setBounds(100, 100, 767, 531);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblPatientRegId = new JLabel("Patient Name");
		lblPatientRegId.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPatientRegId.setBounds(10, 11, 123, 14);
		panel.add(lblPatientRegId);
		
		JLabel lblContactNumber = new JLabel("Contact number: ");
		lblContactNumber.setBounds(331, 13, 106, 14);
		panel.add(lblContactNumber);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 47, 741, 392);
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnViewBill = new JButton("View Bill");
		btnViewBill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openBill();
			}
			private void openBill() {
				if(table.getSelectedRow() > -1) {
					String filePath = table.getValueAt(table.getSelectedRow(), 2).toString();
					if(!FileRunner.openWord(filePath)){ //if not exits search in network drivers.
						String subPath = filePath.substring(filePath.indexOf("Cli"), filePath.length()); //taking only the sub path because 
						//ClinicManagement folder only is shared so path frm that is only needed to search.
						LocalNetworkFileSearch.find(subPath);
					}
				}
			}
		});
		btnViewBill.setBounds(10, 453, 123, 23);
		panel.add(btnViewBill);
		fillTable(patientId);
	}

	private void fillTable(String patientId) {
		LinkedList list = billTable.getBills(patientId);
		table.setModel(new DefaultTableModel(COLUMNS, list.size()));
		Traveller traveller = list.traveller(); 
		int row = 0;
		while(traveller.hasNext()){
			Bill bill = (Bill) traveller.next();
			table.setValueAt(bill.getBillNo(), row, 0);
			table.setValueAt(new CustomDate(bill.getDate()), row, 1);
			table.setValueAt(bill.getFile(), row, 2);
			row++;
		}
	}
}
