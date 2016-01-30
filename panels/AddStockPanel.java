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

import database.StockTableProcessing;
import dto.Item;

import java.awt.Font;

public class AddStockPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7001738797735489013L;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Create the frame.
	 */
	public AddStockPanel() {
		setBounds(100, 100, 375, 330);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblItemName = new JLabel("Item Name: ");
		lblItemName.setBounds(10, 90, 104, 14);
		panel.add(lblItemName);
		
		textField = new JTextField();
		textField.setBounds(157, 87, 170, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblPrice = new JLabel("Price: ");
		lblPrice.setBounds(10, 120, 104, 14);
		panel.add(lblPrice);
		
		textField_1 = new JTextField();
		textField_1.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				updateTotal();
			}
		});
		textField_1.setBounds(157, 117, 170, 20);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblQuantity = new JLabel("Quantity: ");
		lblQuantity.setBounds(10, 150, 104, 14);
		panel.add(lblQuantity);
		
		JLabel lblTotal = new JLabel("Total: ");
		lblTotal.setBounds(10, 180, 46, 14);
		panel.add(lblTotal);
		
		textField_2 = new JTextField();
		textField_2.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				updateTotal();
			}
		});
		textField_2.setBounds(157, 147, 170, 20);
		panel.add(textField_2);
		textField_2.setColumns(10);
		textField_2.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "ENTER");
		textField_2.getActionMap().put("ENTER", new AbstractAction() {
			private static final long serialVersionUID = 5537910194302150408L;
			@Override
			public void actionPerformed(ActionEvent e) {
				add();
			}
		});
		
		textField_3 = new JTextField();
		textField_3.setText("0.0");
		textField_3.setBounds(157, 177, 170, 20);
		panel.add(textField_3);
		textField_3.setColumns(10);
		textField_3.setEnabled(false);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(this);
		btnAdd.setBounds(10, 224, 89, 23);
		panel.add(btnAdd);
		
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(this);
		btnReset.setBounds(117, 224, 89, 23);
		panel.add(btnReset);
		
		JLabel lblAddItemsTo = new JLabel("Add Items To Stock");
		lblAddItemsTo.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblAddItemsTo.setBounds(84, 21, 202, 14);
		panel.add(lblAddItemsTo);
	}
	
	public void reset(){
		textField.setText(null);
		textField_1.setText(null);
		textField_2.setText(null);
		textField_3.setText(String.valueOf(0.0));
	}
	
	private void add() {
		if(isValidUserData()){
			try{
				String name = textField.getText().trim().toLowerCase();
				float price = Float.parseFloat(textField_1.getText().trim());
				int qty = Integer.parseInt(textField_2.getText().trim());
				if(new StockTableProcessing().addToTable(new Item(name, price, qty))) {
					JOptionPane.showMessageDialog(this, "Stock Added.");
					reset(); //completed adding to database;
				}
				else JOptionPane.showMessageDialog(this, "Invalid Information, Not Added to database");
			}catch(NumberFormatException | NullPointerException exp){
				exp.printStackTrace();
				JOptionPane.showMessageDialog(this, "Invalid Input");
			}
		}else {
			JOptionPane.showMessageDialog(this, "Invalid Input");
		}
	}
	
	public boolean isValidUserData(){
		if(textField.getText() == null || textField.getText().trim().length()<1) return false;
		if(textField_1.getText() == null || textField_1.getText().trim().length()<1) return false;
		if(textField_2.getText() == null || textField_2.getText().trim().length()<1) return false;
		if(textField_3.getText() == null || textField_3.getText().trim().length()<1) return false;
		return true;
	}
	
	private void updateTotal() {
		if(isValidUserData()){
			try{
				float price = Float.parseFloat(textField_1.getText().trim());
				float qty = Integer.parseInt(textField_2.getText().trim());
				float total = price * qty;
				textField_3.setText(String.valueOf(total));
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
