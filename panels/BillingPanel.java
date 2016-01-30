package panels;

import java.awt.BorderLayout;

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
import java.awt.Font;
import java.awt.Color;

public class BillingPanel extends JPanel {

	private static final long serialVersionUID = 2464166899373394596L;
	private JTextField textField;
	private JTextField textField_1;
	private StockTableProcessing stockTable;
	private JTable table;
	private JComboBox<String> comboBox;
	private JLabel label;


	/**
	 * Creates the frame.
	 */
	public BillingPanel() {
		stockTable = new StockTableProcessing();
		setBounds(100, 100, 784, 552);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblPatientNumber = new JLabel("Phone Number: ");
		lblPatientNumber.setBounds(10, 14, 90, 14);
		panel.add(lblPatientNumber);
		
		textField = new JTextField();
		textField.setBounds(141, 11, 221, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblItemName = new JLabel("Item Name: ");
		lblItemName.setBounds(10, 63, 90, 14);
		panel.add(lblItemName);
		
		JLabel lblQuantity = new JLabel("Quantity: ");
		lblQuantity.setBounds(10, 108, 74, 14);
		panel.add(lblQuantity);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 145, 754, 334);
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		setTableModel();
		table.setEnabled(false);
		
		textField_1 = new JTextField();
		textField_1.setBounds(141, 105, 221, 20);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addToTable();
			}
		});
		btnAdd.setBounds(410, 104, 99, 23);
		panel.add(btnAdd);
		
		comboBox = new JComboBox<>();
		comboBox.setBounds(141, 60, 221, 20);
		panel.add(comboBox);
		addItems();
		
		JLabel lblTotalAmount = new JLabel("Total Amount:");
		lblTotalAmount.setBounds(435, 505, 152, 14);
		panel.add(lblTotalAmount);
		
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
		btnGenerateBill.setBounds(10, 501, 105, 23);
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
		btnGeneratePrint.setBounds(125, 501, 126, 23);
		panel.add(btnGeneratePrint);
		
		label = new JLabel("0.0");
		label.setForeground(Color.BLUE);
		label.setFont(new Font("Times New Roman", Font.BOLD, 18));
		label.setBounds(597, 498, 143, 29);
		panel.add(label);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addItems();
			}
		});
		btnRefresh.setBounds(410, 59, 99, 23);
		panel.add(btnRefresh);
		
		JButton btnNewButton = new JButton("Reset");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reset();
			}
		});
		btnNewButton.setBounds(261, 501, 89, 23);
		panel.add(btnNewButton);
	}

	private void setTableModel() {
		table.setModel(new DefaultTableModel(new String[]{"Item Name", "Quantity", "Price", "Total"}, 0));
	}

	protected void reset() {
		setTableModel();
		textField.setText("");
		textField_1.setText("");
		label.setText("0.0");
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
	private void addItems() {
		comboBox.removeAllItems();
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
