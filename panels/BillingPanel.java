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
import database.CategoryTableProcessing;
import database.StockTableProcessing;
import ds.Traveller;
import dto.Bill;
import dto.CustomDate;
import dto.Item;
import dto.Patient;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Calendar;
import java.awt.Font;
import java.awt.Color;

public class BillingPanel extends JPanel {

	private static final long serialVersionUID = 2464166899373394596L;
	private JTextField txtKey;
	private JTextField txtQty;
	private StockTableProcessing stockTable;
	private JTable table;
	private JComboBox<String> itemComboBox;
	private JComboBox<String> categoryComboBox;
	private JLabel label;
	private CategoryTableProcessing categoryTable;
	private String[] COLUMNS = {"Item Name", "Quantity", "Price", "Category", "Expire Date", "Total"};
	private JTextField txtFee;
	private JComboBox<String> expiresComboBox;

	/**
	 * Creates the frame.
	 */
	public BillingPanel() {
		categoryTable = new CategoryTableProcessing();
		stockTable = new StockTableProcessing();
		setBounds(100, 100, 784, 663);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		txtKey = new JTextField();
		txtKey.setBounds(194, 21, 221, 20);
		panel.add(txtKey);
		txtKey.setColumns(10);
		
		JLabel lblItemName = new JLabel("Item Name: ");
		lblItemName.setBounds(10, 132, 90, 14);
		panel.add(lblItemName);
		
		JLabel lblQuantity = new JLabel("Quantity: ");
		lblQuantity.setBounds(10, 219, 74, 14);
		panel.add(lblQuantity);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 249, 754, 334);
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		setTableModel();
		table.setEnabled(false);
		
		txtQty = new JTextField("1");
		txtQty.setBounds(194, 216, 221, 20);
		panel.add(txtQty);
		txtQty.setColumns(10);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addToTable();
			}
		});
		btnAdd.setBounds(463, 215, 99, 23);
		panel.add(btnAdd);
		
		categoryComboBox = new JComboBox<String>();
		categoryComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateItems();
			}
		});
		categoryComboBox.setBounds(194, 90, 221, 20);
		panel.add(categoryComboBox);
		categoryTable.updateCategory(categoryComboBox);
		
		itemComboBox = new JComboBox<>();
		itemComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateExpireDate();
			}
		});
		itemComboBox.setBounds(194, 129, 221, 20);
		panel.add(itemComboBox);
		updateItems();
		
		JLabel lblTotalAmount = new JLabel("Total Amount:");
		lblTotalAmount.setBounds(435, 609, 152, 14);
		panel.add(lblTotalAmount);
		
		JButton btnGenerateBill = new JButton("Save");
		btnGenerateBill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File file = saveBill();
				if(file != null) {
					System.out.println(file);
					reset();
				}else System.out.println("no bill");
			}
		});
		btnGenerateBill.setBounds(10, 605, 105, 23);
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
		btnGeneratePrint.setBounds(125, 605, 126, 23);
		panel.add(btnGeneratePrint);
		
		label = new JLabel("0.0");
		label.setForeground(Color.BLUE);
		label.setFont(new Font("Times New Roman", Font.BOLD, 18));
		label.setBounds(597, 602, 143, 29);
		panel.add(label);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				categoryTable.updateCategory(categoryComboBox);
				updateItems();
			}
		});
		btnRefresh.setBounds(463, 171, 99, 23);
		panel.add(btnRefresh);
		
		JButton btnNewButton = new JButton("Reset");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reset();
			}
		});
		btnNewButton.setBounds(261, 605, 89, 23);
		panel.add(btnNewButton);
		
		JLabel lblCategory = new JLabel("Category");
		lblCategory.setBounds(10, 93, 90, 14);
		panel.add(lblCategory);
		
		JLabel lblNumberOrId = new JLabel("Number or ID: ");
		lblNumberOrId.setBounds(10, 24, 90, 14);
		panel.add(lblNumberOrId);
		
		JLabel lblExpiresOn = new JLabel("Expires On:");
		lblExpiresOn.setBounds(10, 175, 90, 14);
		panel.add(lblExpiresOn);
		
		expiresComboBox = new JComboBox<String>();
		expiresComboBox.setBounds(194, 172, 221, 20);
		panel.add(expiresComboBox);
		
		JLabel lblDoctersFee = new JLabel("Docter's Fee: ");
		lblDoctersFee.setBounds(10, 60, 152, 14);
		panel.add(lblDoctersFee);
		
		txtFee = new JTextField();
		txtFee.setColumns(10);
		txtFee.setBounds(194, 57, 221, 20);
		panel.add(txtFee);
	}

	private void updateExpireDate() {
		if(expiresComboBox == null || itemComboBox == null || itemComboBox.getItemCount() <=0) return;
		if(expiresComboBox.getItemCount() > 0) expiresComboBox.removeAllItems(); //removing all previous items.
		String itemName = itemComboBox.getSelectedItem().toString();
		Traveller traveller = stockTable.getexpireDatesList(itemName).traveller();
		while(traveller.hasNext()) 
			expiresComboBox.addItem(new CustomDate((java.sql.Date)traveller.next()).toString());
	}

	private void setTableModel() {
		table.setModel(new DefaultTableModel(COLUMNS, 0));
	}

	protected void reset() {
		setTableModel();
		txtFee.setText("");
		txtKey.setText("");
		txtQty.setText("1");
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

		if(txtKey.getText() != null && txtKey.getText().length() >= 1)
			patient = PatientInfo.getInstance().getPatient(txtKey.getText());

		if(patient == null){
			JOptionPane.showMessageDialog(this, "Patient Number not found");
			return null;
		}
		
		if(table.getRowCount() < 1) {
			JOptionPane.showMessageDialog(this, "No Item Found");
			return null;
		}

		Float docterFee = Float.parseFloat(txtFee.getText());
		Bill bill = new Bill(patient, UserInfo.getUser(), new BillTableProcessing().nextId(), docterFee);
		for(int i=0; i<table.getRowCount(); i++){

			String itemName = table.getValueAt(i, 0).toString();
			int qty = Integer.parseInt(table.getValueAt(i, 1).toString());
			float price = Float.parseFloat(table.getValueAt(i, 2).toString());
			String category = table.getValueAt(i, 3).toString();
			Calendar date = new CustomDate(table.getValueAt(i, 4).toString()).getCalender();
			
			Item item = new Item(itemName, price, qty, category, date);
			bill.addItem(item);
		}
		if(stockTable.updateBill(bill)) {
			new BillTableProcessing().addId(bill.getId());
			return new BillManager(bill).generateBill(); 
		}
		else return null;
	}

	/**
	 * adds all the items from stock table to combo box.
	 * @param comboBox
	 */
	private void updateItems() {
		if(itemComboBox == null) return;
		if(itemComboBox.getItemCount() > 0) itemComboBox.removeAllItems();
		if(categoryComboBox == null || categoryComboBox.getSelectedItem() == null) return;
		Traveller traveller = stockTable.itemList(categoryComboBox.getSelectedItem().toString()).traveller();
		while(traveller.hasNext()) 
			itemComboBox.addItem(traveller.next().toString());
	}
	
	private void addToTable() {
		if(!isValidToAddInTable()) return;
		int lastRow = table.getRowCount();
		TableModel model = new DefaultTableModel(COLUMNS, lastRow+1);
		for(int i=0; i<table.getRowCount(); i++){
			model.setValueAt(table.getValueAt(i, 0), i, 0);
			model.setValueAt(table.getValueAt(i, 1), i, 1);
			model.setValueAt(table.getValueAt(i, 2), i, 2);
			model.setValueAt(table.getValueAt(i, 3), i, 3);
			model.setValueAt(table.getValueAt(i, 3), i, 4);
			model.setValueAt(table.getValueAt(i, 3), i, 5);
			
		}
		table.setModel(model);
		
		String item = itemComboBox.getSelectedItem().toString();
		String category = categoryComboBox.getSelectedItem().toString();
		int qty = Integer.parseInt(txtQty.getText());
		float price = stockTable.getItemPrice(item);
		String date = expiresComboBox.getSelectedItem().toString();
		
		table.setValueAt(item, lastRow, 0);
		table.setValueAt(qty, lastRow, 1);
		table.setValueAt(price, lastRow, 2);
		table.setValueAt(category, lastRow, 3);
		table.setValueAt(date, lastRow, 4);
		table.setValueAt(qty * price, lastRow, 5);
		updateTotal();
	}

	private void updateTotal() {
		float total = 0.0f;
		for(int i=0; i<table.getRowCount(); i++)
			total += Float.parseFloat(table.getValueAt(i, 5).toString());
		label.setText(String.valueOf(total));
	}
	
	private boolean isValidToAddInTable() {
		
		String item = itemComboBox.getSelectedItem().toString();
		for(int i=0; i<table.getRowCount(); i++){
			if(item.equals(table.getValueAt(i, 0))) {
				JOptionPane.showMessageDialog(this, "Item already in billing table");
				return false;
			}
		}
		
		int billedQty = 0;
		try{
			billedQty = Integer.parseInt(txtQty.getText());
		}catch(NullPointerException | NumberFormatException exp){
			JOptionPane.showMessageDialog(this, "Invalid Input");
			return false;
		}
		
		if(billedQty <= 0) {
			JOptionPane.showMessageDialog(this, "Quantity Should Be Greater than ZERO : ");
			return false;
		}
		
		int availableQty = stockTable.getItemQty(item);
		if(billedQty <= availableQty) return true;
		else {
			JOptionPane.showMessageDialog(this, "Available Quantity : "+availableQty);
			return false;
		}
		
	}
}
