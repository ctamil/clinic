package panels;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.AbstractAction;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.KeyStroke;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;

import database.CategoryTableProcessing;
import database.StockTableProcessing;
import dto.Item;

import java.awt.Font;
import javax.swing.JComboBox;
import java.awt.Color;

public class AddStockPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7001738797735489013L;
	private JTextField txtItem;
	private JTextField txtPrice;
	private JTextField txtQuantity;
	private JLabel lblTotal;
	private DatePanel datePanel;
	private JComboBox<String> comboBox;
	private CategoryTableProcessing categoryTable;
	private JLabel lblTotal_1;
	
	/**
	 * Create the frame.
	 */
	public AddStockPanel() {
		categoryTable = new CategoryTableProcessing();
		setBounds(100, 100, 567, 432);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblItemName = new JLabel("Item Name: ");
		lblItemName.setBounds(10, 117, 104, 14);
		panel.add(lblItemName);
		
		txtItem = new JTextField();
		txtItem.setBounds(157, 114, 305, 20);
		panel.add(txtItem);
		txtItem.setColumns(10);
		
		JLabel lblPrice = new JLabel("Price per item: ");
		lblPrice.setBounds(10, 233, 104, 14);
		panel.add(lblPrice);
		
		JLabel lblQuantity = new JLabel("Quantity: ");
		lblQuantity.setBounds(10, 205, 104, 14);
		panel.add(lblQuantity);
		
		JLabel lblTotal = new JLabel("Total: ");
		lblTotal.setBounds(10, 272, 46, 14);
		panel.add(lblTotal);
		
		txtQuantity = new JTextField();
		txtQuantity.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				updateTotal();
			}
		});
		txtQuantity.setBounds(157, 202, 305, 20);
		panel.add(txtQuantity);
		txtQuantity.setColumns(10);
		
		txtPrice = new JTextField();
		txtPrice.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				updateTotal();
			}
		});
		txtPrice.setBounds(157, 230, 305, 20);
		panel.add(txtPrice);
		txtPrice.setColumns(10);
		txtPrice.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "ENTER");
		txtPrice.getActionMap().put("ENTER", new AbstractAction() {
			private static final long serialVersionUID = 5537910194302150408L;
			@Override
			public void actionPerformed(ActionEvent e) {
				add();
			}
		});
		
		lblTotal_1 = new JLabel();
		lblTotal_1.setForeground(Color.RED);
		lblTotal_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTotal_1.setText("0.0");
		lblTotal_1.setBounds(157, 269, 305, 33);
		panel.add(lblTotal_1);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(this);
		btnAdd.setBounds(10, 328, 89, 23);
		panel.add(btnAdd);
		
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(this);
		btnReset.setBounds(157, 328, 89, 23);
		panel.add(btnReset);
		
		JLabel lblAddItemsTo = new JLabel("Add Items To Stock");
		lblAddItemsTo.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblAddItemsTo.setBounds(186, 11, 202, 14);
		panel.add(lblAddItemsTo);
		
		JLabel lblCategory = new JLabel("Category: ");
		lblCategory.setBounds(10, 77, 104, 14);
		panel.add(lblCategory);
		
		comboBox = new JComboBox<>();
		comboBox.setBounds(157, 74, 202, 20);
		panel.add(comboBox);
		categoryTable.updateCategory(comboBox);
		
		datePanel = DatePanel.getInstance(txtPrice);
		datePanel.setBounds(157, 145, 327, 47);
		panel.add(datePanel);
		
		JLabel lblExpireDate = new JLabel("Expire Date:");
		lblExpireDate.setBounds(10, 159, 104, 14);
		panel.add(lblExpireDate);
		
		JButton btnNew = new JButton("New");
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				categoryTable.addCategory();
				categoryTable.updateCategory(comboBox);
			}
		});
		btnNew.setBounds(369, 73, 93, 23);
		panel.add(btnNew);
	}

	public void reset(){
		txtItem.setText(null);
		txtPrice.setText(null);
		txtQuantity.setText(null);
		lblTotal.setText(String.valueOf(0.0));
		datePanel.reset();
		categoryTable.updateCategory(comboBox);
	}
	
	private void add() {
		if(isValidUserData()){
			try{
				String category = comboBox.getSelectedItem().toString();
				String name = txtItem.getText().trim().toLowerCase();
				float price = Float.parseFloat(txtPrice.getText().trim());
				int qty = Integer.parseInt(txtQuantity.getText().trim());
				java.sql.Date date = datePanel.getDate().getSQLDate();
				if(new StockTableProcessing().addToDB(new Item(name, price, qty, category, date))) { //if completed adding to database;
					JOptionPane.showMessageDialog(this, "Stock Added.");
					reset(); 
				}
			}catch(NumberFormatException | NullPointerException exp){
				exp.printStackTrace();
				JOptionPane.showMessageDialog(this, "Invalid Input");
			}
		}else {
			JOptionPane.showMessageDialog(this, "Invalid Input");
		}
	}
	
	public boolean isValidUserData(){
		if(txtItem.getText() == null || txtItem.getText().trim().length()<1) return false;
		if(txtPrice.getText() == null || txtPrice.getText().trim().length()<1) return false;
		if(txtQuantity.getText() == null || txtQuantity.getText().trim().length()<1) return false;
		if(lblTotal.getText() == null || lblTotal.getText().trim().length()<1) return false;
		if(comboBox.getSelectedItem() == null) return false;
		return true;
	}
	
	private void updateTotal() {
		if(isValidUserData()){
			try{
				float price = Float.parseFloat(txtPrice.getText().trim());
				float qty = Integer.parseInt(txtQuantity.getText().trim());
				float total = price * qty;
				lblTotal.setText(String.valueOf(total));
			}catch(NumberFormatException | NullPointerException exp){
				System.err.println(exp.getMessage());
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source instanceof JButton){
			JButton btnSource = (JButton) source;
			switch(btnSource.getText()){
				case "Add":
					add();
					break;
				
				case "Reset":
					reset();
					break;
			}
		}
	}
}
