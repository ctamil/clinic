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
import java.awt.Font;
import java.awt.Color;

import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

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
	private Bill bill;
	private JComboBox<String> priceComboBox;

	/**
	 * Creates the frame.
	 */
	public BillingPanel() {
		bill = new Bill(UserInfo.getUser());
		categoryTable = new CategoryTableProcessing();
		stockTable = new StockTableProcessing();
		setBounds(100, 100, 784, 605);
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
		lblItemName.setBounds(10, 114, 90, 14);
		panel.add(lblItemName);
		
		JLabel lblQuantity = new JLabel("Quantity: ");
		lblQuantity.setBounds(10, 212, 74, 14);
		panel.add(lblQuantity);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 249, 754, 283);
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		setTableModel();
		
		txtQty = new JTextField("1");
		txtQty.setBounds(194, 209, 221, 20);
		panel.add(txtQty);
		txtQty.setColumns(10);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addToTable();
			}
		});
		btnAdd.setBounds(448, 208, 99, 23);
		panel.add(btnAdd);
		
		categoryComboBox = new JComboBox<String>();
		categoryComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateItems();
			}
		});
		categoryComboBox.setBounds(194, 80, 221, 20);
		panel.add(categoryComboBox);
		categoryTable.updateCategory(categoryComboBox);
		
		itemComboBox = new JComboBox<>();
		itemComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateExpireDate();
			}
		});
		itemComboBox.setBounds(194, 111, 221, 20);
		panel.add(itemComboBox);
		updateItems();
		
		JLabel lblTotalAmount = new JLabel("Total Amount:");
		lblTotalAmount.setBounds(459, 562, 152, 14);
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
		btnGenerateBill.setBounds(10, 558, 90, 23);
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
		btnGeneratePrint.setBounds(109, 558, 126, 23);
		panel.add(btnGeneratePrint);
		
		label = new JLabel("0.0");
		label.setForeground(Color.BLUE);
		label.setFont(new Font("Times New Roman", Font.BOLD, 18));
		label.setBounds(621, 553, 143, 29);
		panel.add(label);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				categoryTable.updateCategory(categoryComboBox);
				updateItems();
			}
		});
		btnRefresh.setBounds(448, 79, 99, 23);
		panel.add(btnRefresh);
		
		JButton btnNewButton = new JButton("Reset");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reset();
			}
		});
		btnNewButton.setBounds(245, 558, 89, 23);
		panel.add(btnNewButton);
		
		JLabel lblCategory = new JLabel("Category");
		lblCategory.setBounds(10, 83, 90, 14);
		panel.add(lblCategory);
		
		JLabel lblNumberOrId = new JLabel("Number or ID: ");
		lblNumberOrId.setBounds(10, 24, 90, 14);
		panel.add(lblNumberOrId);
		
		JLabel lblExpiresOn = new JLabel("Expires On:");
		lblExpiresOn.setBounds(10, 149, 90, 14);
		panel.add(lblExpiresOn);
		
		expiresComboBox = new JComboBox<String>();
		expiresComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				updatePrice();
			}
		});
		expiresComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updatePrice();
			}
		});
		expiresComboBox.setBounds(194, 146, 221, 20);
		panel.add(expiresComboBox);
		
		JLabel lblDoctersFee = new JLabel("Docter's Fee: ");
		lblDoctersFee.setBounds(10, 55, 152, 14);
		panel.add(lblDoctersFee);
		
		txtFee = new JTextField();
		txtFee.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				updateDocterFee();
			}
		});
		txtFee.setColumns(10);
		txtFee.setBounds(194, 52, 221, 20);
		panel.add(txtFee);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow() > -1) deleteRow(table.getSelectedRow());
			}
		});
		btnDelete.setBounds(342, 558, 90, 23);
		panel.add(btnDelete);
		
		priceComboBox = new JComboBox<String>();
		priceComboBox.setBounds(194, 177, 221, 20);
		panel.add(priceComboBox);
		
		JLabel lblPrice = new JLabel("Price: ");
		lblPrice.setBounds(10, 180, 90, 14);
		panel.add(lblPrice);
	}
	
	private void updatePrice() {
		if(categoryComboBox != null && categoryComboBox.getSelectedIndex() > -1 && 
				itemComboBox != null && itemComboBox.getSelectedIndex() > -1 && 
				expiresComboBox != null && expiresComboBox.getSelectedIndex() > -1 ){
			String item = itemComboBox.getSelectedItem().toString();
			String category = categoryComboBox.getSelectedItem().toString();
			String date = expiresComboBox.getSelectedItem().toString();
			Traveller prices = stockTable.getItemPrices(item, category, new CustomDate(date).getSQLDate()).traveller();
			priceComboBox.removeAllItems();
			while(prices.hasNext()) priceComboBox.addItem(prices.next()+"");
		}
		
		
	}

	private void deleteRow(int selectedRow) {
		DefaultTableModel tableModel = new DefaultTableModel(COLUMNS, table.getRowCount()-1);
		int row = 0;
		for(int i=0; i<table.getRowCount(); i++){
			if(i == selectedRow) continue;
			tableModel.setValueAt(table.getValueAt(i, 0), row, 0);
			tableModel.setValueAt(table.getValueAt(i, 1), row, 1);
			tableModel.setValueAt(table.getValueAt(i, 2), row, 2);
			tableModel.setValueAt(table.getValueAt(i, 3), row, 3);
			tableModel.setValueAt(table.getValueAt(i, 4), row, 4);
			tableModel.setValueAt(table.getValueAt(i, 5), row, 5);
			row++;
		}
		table.setModel(tableModel);
		updateTotal();
	}

	private void updateDocterFee() {
		if(txtFee.getText() != null && txtFee.getText().length() > 0){
			try{
				Integer docterFee = Integer.parseInt(txtFee.getText());
				bill.setDocterFee(docterFee);
				updateTotal();
			}catch(NumberFormatException exe){
				JOptionPane.showMessageDialog(this, "Invalid Docter Fee");
			}
		}
	}

	private void updateExpireDate() {
		if(expiresComboBox == null || itemComboBox == null || itemComboBox.getItemCount() <=0) return;
		if(expiresComboBox.getItemCount() > 0) expiresComboBox.removeAllItems(); //removing all previous items.
		String category = categoryComboBox.getSelectedItem().toString();
		String itemName = itemComboBox.getSelectedItem().toString();
		Traveller traveller = stockTable.getexpireDatesList(itemName, category).traveller();
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
		bill = new Bill(UserInfo.getUser());
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

		bill.setPatient(patient);
		bill.setBillNo(new BillTableProcessing().nextBillNo());
		
		for(int i=0; i<table.getRowCount(); i++){

			String itemName = table.getValueAt(i, 0).toString();
			int qty = Integer.parseInt(table.getValueAt(i, 1).toString());
			float price = Float.parseFloat(table.getValueAt(i, 2).toString());
			String category = table.getValueAt(i, 3).toString();
			java.sql.Date date = new CustomDate(table.getValueAt(i, 4).toString()).getSQLDate();
			
			Item item = new Item(itemName, price, qty, category, date);
			bill.addItem(item);
		}
		if(stockTable.updateBill(bill)) {
			File file = new BillManager(bill).generateBill();
			bill.setFile(file.getAbsolutePath());
			new BillTableProcessing().addToDB(bill);
			return file;
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
		
		String item = itemComboBox.getSelectedItem().toString();
		String category = categoryComboBox.getSelectedItem().toString();
		String date = expiresComboBox.getSelectedItem().toString();
		int qty = Integer.parseInt(txtQty.getText());
		float price = Float.parseFloat(priceComboBox.getSelectedItem().toString());
		
		if(isItemAlreadyExitsInTable(item, category, date, price)){
			JOptionPane.showMessageDialog(this, "Item Already Exits in table");
			return;
		}
		
		int lastRow = table.getRowCount();
		TableModel model = new DefaultTableModel(COLUMNS, lastRow+1);
		for(int i=0; i<table.getRowCount(); i++){
			model.setValueAt(table.getValueAt(i, 0), i, 0);
			model.setValueAt(table.getValueAt(i, 1), i, 1);
			model.setValueAt(table.getValueAt(i, 2), i, 2);
			model.setValueAt(table.getValueAt(i, 3), i, 3);
			model.setValueAt(table.getValueAt(i, 4), i, 4);
			model.setValueAt(table.getValueAt(i, 5), i, 5);
			
		}
		table.setModel(model);
		
		table.setValueAt(item, lastRow, 0);
		table.setValueAt(qty, lastRow, 1);
		table.setValueAt(price, lastRow, 2);
		table.setValueAt(category, lastRow, 3);
		table.setValueAt(date, lastRow, 4);
		table.setValueAt(qty * price, lastRow, 5);
		updateTotal();
	}

	private boolean isItemAlreadyExitsInTable(String item, String category,
			String date, float price) {
		for(int i=0; i<table.getRowCount(); i++){
			if(table.getValueAt(i, 0).toString().equals(item) && 
					Float.parseFloat(table.getValueAt(i, 2).toString()) == price &&
					table.getValueAt(i, 3).toString().equals(category) && 
					table.getValueAt(i, 4).toString().equals(date)) return true; 
		}
		return false;
	}

	private void updateTotal() {
		float total = bill.getDocterFee();
		for(int i=0; i<table.getRowCount(); i++)
			total += Float.parseFloat(table.getValueAt(i, 5).toString());
		label.setText(String.valueOf(total));
	}
	
	private boolean isValidToAddInTable() {
		
		if(priceComboBox.getSelectedItem() == null){
			JOptionPane.showMessageDialog(this, "Invalid Input");
			return false;
		}
		
		String item = itemComboBox.getSelectedItem().toString();
		
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
		
		String category = categoryComboBox.getSelectedItem().toString();
		String date = expiresComboBox.getSelectedItem().toString();
		float price = Float.parseFloat(priceComboBox.getSelectedItem().toString());
		int availableQty = stockTable.getItemQty(item, category, new CustomDate(date).getSQLDate(), price);
		if(billedQty <= availableQty) return true;
		else {
			JOptionPane.showMessageDialog(this, "Available Quantity : "+availableQty);
			return false;
		}
		
	}
}
