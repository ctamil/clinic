package frames;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;

import billing.BillManager;
import billing.DocumentPrinter;
import storage.PatientInfo;
import storage.UserInfo;
import database.BillTableProcessing;
import database.StockTableProcessing;
import ds.Traveller;
import dto.Bill;
import dto.Item;
import dto.Patient;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;

public class BillingFrame extends JFrame {

	private static final long serialVersionUID = 2464166899373394596L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private StockTableProcessing stockTable;
	private JTable table;
	private JComboBox<String> comboBox;
	private JLabel label;


	/**
	 * Creates the frame.
	 */
	public BillingFrame() {
		stockTable = new StockTableProcessing();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 734, 543);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblPatientNumber = new JLabel("Patient Phone Number: ");
		lblPatientNumber.setBounds(10, 11, 173, 14);
		panel.add(lblPatientNumber);
		
		textField = new JTextField();
		textField.setBounds(178, 8, 221, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblItemName = new JLabel("Item Name: ");
		lblItemName.setBounds(10, 60, 99, 14);
		panel.add(lblItemName);
		
		JLabel lblQuantity = new JLabel("Quantity: ");
		lblQuantity.setBounds(368, 60, 74, 14);
		panel.add(lblQuantity);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 85, 688, 334);
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(new String[]{"Item Name", "Quantity", "Price", "Total"}, 0));
		table.setEnabled(false);
		
		textField_1 = new JTextField();
		textField_1.setBounds(452, 57, 137, 20);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addToTable();
			}
		});
		btnAdd.setBounds(599, 56, 99, 23);
		panel.add(btnAdd);
		
		comboBox = new JComboBox<>();
		comboBox.setBounds(100, 57, 221, 20);
		panel.add(comboBox);
		addItems(comboBox);
		
		JLabel lblTotalAmount = new JLabel("Total Amount ");
		lblTotalAmount.setBounds(10, 434, 74, 14);
		panel.add(lblTotalAmount);
		
		JButton btnRoundUp = new JButton("Round Up");
		btnRoundUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Float val = Float.parseFloat(label.getText());
				label.setText(String.valueOf(Math.ceil(val)));
			}
		});
		btnRoundUp.setBounds(178, 430, 105, 23);
		panel.add(btnRoundUp);
		
		JButton btnRoundDown = new JButton("Round Down");
		btnRoundDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Float val = Float.parseFloat(label.getText());
				label.setText(String.valueOf(Math.floor(val)));
			}
		});
		btnRoundDown.setBounds(312, 430, 105, 23);
		panel.add(btnRoundDown);
		
		JButton btnGenerateBill = new JButton("Save");
		btnGenerateBill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File file = saveBill();
				if(file != null) {
					System.out.println(file);
					reset();
				}
			}
		});
		btnGenerateBill.setBounds(446, 430, 105, 23);
		panel.add(btnGenerateBill);
		
		JButton btnGeneratePrint = new JButton("Save & Print");
		btnGeneratePrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				File file = saveBill();
				if(file != null) {
					System.out.println(file);
					DocumentPrinter.print(file);
					reset();
				}
			}
		});
		btnGeneratePrint.setBounds(572, 430, 126, 23);
		panel.add(btnGeneratePrint);
		
		label = new JLabel("0.0");
		label.setBounds(89, 434, 46, 14);
		panel.add(label);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new NavigationFrame().setVisible(true);
				dispose();
			}
		});
		btnBack.setBounds(572, 463, 126, 23);
		panel.add(btnBack);
	}

	protected void reset() {
		new BillingFrame().setVisible(true);
		dispose();
	}

	/**
	 * Validates the input data, then 
	 * updates to stock table(reduces items from the stock)
	 * if that was successfully done then it generates the bill and
	 * adds id to the bill table.
	 * @return bill document when it was successfully created else it returns null.
	 */
	private File saveBill() {
		Patient patient = null;

		if(textField.getText() != null && textField.getText().length() >= 1) 
			patient = PatientInfo.getInstance().get(textField.getText());

		if(patient == null){
			JOptionPane.showMessageDialog(this, "Patient Number not found");
			return null;
		}
		
		if(table.getRowCount() < 1) {
			JOptionPane.showMessageDialog(this, "No Item Found");
			return null;
		}

		Bill bill = new Bill(patient, UserInfo.getUSER(), new BillTableProcessing().getId());
		for(int i=0; i<table.getRowCount(); i++){
			String itemName = table.getValueAt(i, 0).toString();
			int qty = Integer.parseInt(table.getValueAt(i, 1).toString());
			float price = Float.parseFloat(table.getValueAt(i, 2).toString());
			Item item = new Item(itemName, price, qty);
			bill.addItem(item);
		}
		if(stockTable.updateBill(bill)) {
			new BillTableProcessing().addId(bill.getId());
			return new BillManager(bill).generateBill(); 
		}
		else return null;
	}

	/**
	 * adds all the items from stock table to combobox.
	 * @param comboBox
	 */
	private void addItems(JComboBox<String> comboBox) {
		Traveller traveller = stockTable.itemList().traveller();
		while(traveller.hasNext()) 
			comboBox.addItem(traveller.next().toString());
	}
	
	/**
	 * adds the item to billing table (not to DB table), selected from the combobox
	 */
	private void addToTable() {
		if(!isValidToAddInTable()) return;
		int lastRow = table.getRowCount();
		TableModel model = new DefaultTableModel(new String[]{"Item Name", "Quantity", "Price", "Total"}, lastRow+1);
		for(int i=0; i<table.getRowCount(); i++){
			model.setValueAt(table.getValueAt(i, 0), i, 0);
			model.setValueAt(table.getValueAt(i, 1), i, 1);
			model.setValueAt(table.getValueAt(i, 2), i, 2);
			model.setValueAt(table.getValueAt(i, 3), i, 3);
			
		}
		table.setModel(model);
		
		String item = comboBox.getSelectedItem().toString();
		int qty = Integer.parseInt(textField_1.getText());
		float price = stockTable.getItemPrice(item);
		table.setValueAt(item, lastRow, 0);
		table.setValueAt(qty, lastRow, 1);
		table.setValueAt(price, lastRow, 2);
		table.setValueAt(qty * price, lastRow, 3);
		updateTotal();
	}

	/**
	 * Each time a item added to billing table (not DB table) total is updated.
	 */
	private void updateTotal() {
		float total = 0.0f;
		for(int i=0; i<table.getRowCount(); i++)
			total += Float.parseFloat(table.getValueAt(i, 3).toString());
		label.setText(String.valueOf(total));
	}

	/**
	 * 
	 * @return true if user input was valid
	 */
	private boolean isValidToAddInTable() {
		String item = comboBox.getSelectedItem().toString();
		for(int i=0; i<table.getRowCount(); i++){
			if(item.equals(table.getValueAt(i, 0))) {
				JOptionPane.showMessageDialog(this, "Item already in billing table");
				return false;
			}
		}
		
		int availableQty = stockTable.getItemQty(item);
		int billedQty = 0;
		
		try{
			billedQty = Integer.parseInt(textField_1.getText());
		}catch(NullPointerException | NumberFormatException exp){
			JOptionPane.showMessageDialog(this, "Invalid Input");
			return false;
		}
		
		if(billedQty <= availableQty) return true;
		else {
			JOptionPane.showMessageDialog(this, "Available Quantity : "+availableQty);
			return false;
		}
	}

	
}
