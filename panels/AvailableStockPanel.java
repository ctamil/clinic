package panels;

import java.awt.BorderLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import database.StockTableProcessing;
import ds.LinkedList;
import ds.Traveller;
import dto.CustomDate;
import dto.Item;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.util.Calendar;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;

public class AvailableStockPanel extends JPanel {

	private static final long serialVersionUID = -5032347759657468271L;
	private JTable table;
	private String[] COLUMNS = {"Item Name", "Category", "Expire Date", "Available Quantity", "Price"};
	private JTextField txtSearch;
	private JComboBox<String> orderByComboBox;
	private JComboBox<String> searchTypeComboBox;
	private JComboBox<String> orderTypecomboBox;

	public AvailableStockPanel() {
		setBounds(100, 100, 798, 573);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				fillTable();
			}
		});
		add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 90, 778, 412);
		panel.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 500, 723, 53);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fillTable();
			}
		});
		btnRefresh.setBounds(0, 11, 137, 23);
		panel_1.add(btnRefresh);
		
		JButton btnDelete = new JButton("Delete Item");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteItem();
			}
		});
		btnDelete.setBounds(154, 11, 137, 23);
		panel_1.add(btnDelete);
		
		JLabel lblSearchBy = new JLabel("Search By: ");
		lblSearchBy.setBounds(10, 14, 108, 14);
		panel.add(lblSearchBy);
		
		searchTypeComboBox = new JComboBox<>();
		searchTypeComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {null, "Item Name", "Category"}));
		searchTypeComboBox.setBounds(113, 11, 218, 20);
		panel.add(searchTypeComboBox);
		
		txtSearch = new JTextField();
		txtSearch.setBounds(341, 11, 256, 20);
		panel.add(txtSearch);
		txtSearch.setColumns(10);
		
		JLabel lblOrderBy = new JLabel("Order By: ");
		lblOrderBy.setBounds(10, 51, 108, 14);
		panel.add(lblOrderBy);
		
		orderByComboBox = new JComboBox<>();
		orderByComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Item Name", "Category", "Quantity", "Price"}));
		orderByComboBox.setBounds(113, 48, 218, 20);
		panel.add(orderByComboBox);
		
		JButton btnNewButton = new JButton("Filter");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fillTable();
			}
		});
		btnNewButton.setBounds(482, 48, 115, 23);
		panel.add(btnNewButton);
		
		orderTypecomboBox = new JComboBox<String>();
		orderTypecomboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"ASC", "DESC"}));
		orderTypecomboBox.setBounds(341, 48, 131, 20);
		panel.add(orderTypecomboBox);
		
	}

	private void deleteItem() {
		int option = JOptionPane.showConfirmDialog(null, "Are You Sure Want to Delete.");
		if(option == JOptionPane.NO_OPTION) return;
		int row = table.getSelectedRow();
		if(row == -1) {
			JOptionPane.showMessageDialog(this, "Select An Item");
			return;
		}
		String name = table.getValueAt(row, 0).toString(); //first column.
		Calendar expireDate = new CustomDate(table.getValueAt(row, 2).toString()).getCalender(); //third column contains expire date.
		Item item = new Item(name, expireDate);
		new StockTableProcessing().deleteItemFromDb(item);
		fillTable();
	}

	private void fillTable() {
		LinkedList tableList = getDatatsForTable();
		table.setModel(new DefaultTableModel(COLUMNS, tableList.size()));
		Traveller listTraveller = tableList.traveller();
		for(int i=0; i<listTraveller.size(); i++){
			Item item = (Item) listTraveller.next();
			table.setValueAt(item.getName(), i, 0);
			table.setValueAt(item.getCategory(), i, 1);
			table.setValueAt(new CustomDate(item.getExpireDate()).toString(), i, 2);
			table.setValueAt(item.getQuantity(), i, 3);
			table.setValueAt(item.getPrice(), i, 4);
		}
	}
	
	private LinkedList getDatatsForTable(){
		StockTableProcessing stockTable = new StockTableProcessing();
		String searchType = null, searchKey = null, orderByColumn = null, orderType = null;
		if(searchTypeComboBox != null && searchTypeComboBox.getSelectedItem() != null) searchType = searchTypeComboBox.getSelectedItem().toString();
		if(txtSearch != null && txtSearch.getText() != null) searchKey = txtSearch.getText();
		if(orderByComboBox != null && orderByComboBox.getSelectedItem() != null) orderByColumn = orderByComboBox.getSelectedItem().toString();
		if(orderTypecomboBox!= null && orderTypecomboBox.getSelectedItem() != null) orderType = orderTypecomboBox.getSelectedItem().toString();
		return stockTable.itemList(searchType, searchKey, orderByColumn, orderType);
	}
}
