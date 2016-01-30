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
import dto.Item;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AvailableStockPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5032347759657468271L;
	private JTable table;

	public AvailableStockPanel() {
		setBounds(100, 100, 552, 634);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 526, 542);
		panel.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 540, 526, 53);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fillTalbe();
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
		fillTalbe();
		
	}

	private void deleteItem() {
		int option = JOptionPane.showConfirmDialog(null, "Are You Sure Want to Delete.");
		if(option == JOptionPane.NO_OPTION) return;
		int row = table.getSelectedRow();
		if(row == -1) {
			JOptionPane.showMessageDialog(this, "Select An Item");
			return;
		}
		String name = table.getValueAt(row, 0).toString();
		new StockTableProcessing().deleteItem(name);
		fillTalbe();
	}

	private void fillTalbe() {
		StockTableProcessing stockTable = new StockTableProcessing();
		table.setModel(new DefaultTableModel(new String[]{"Item Name", "Available Quantity", "Price"}, stockTable.getSize()));
		LinkedList tableList = stockTable.toLinkedList();
		Traveller listTraveller = tableList.traveller();
		for(int i=0; i<listTraveller.size(); i++){
			Item item = (Item) listTraveller.next();
			table.setValueAt(item.getName(), i, 0);
			table.setValueAt(item.getQuantity(), i, 1);
			table.setValueAt(item.getPrice(), i, 2);
		}
	}
}
