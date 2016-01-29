package frames;

import java.awt.BorderLayout;

import javax.swing.JFrame;
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

public class AvailableStockFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5032347759657468271L;
	private JPanel contentPane;
	private JTable table;


	/**
	 * Create the frame.
	 */
	public AvailableStockFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 552, 634);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
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
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new NavigationFrame().setVisible(true);
				dispose();
			}
		});
		btnBack.setBounds(414, 11, 112, 23);
		panel_1.add(btnBack);
		
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
