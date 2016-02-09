package frames;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import database.UserTableProcessing;
import ds.LinkedList;
import ds.Traveller;
import dto.User;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewUserFrame extends JFrame {

	private static final long serialVersionUID = 1519491422052424093L;
	private JPanel contentPane;
	private UserTableProcessing userTable;
	private static String[] COLUMNS = {"User Name", "Role"};
	private JTable table;
	private JButton btnDelete;


	/**
	 * Create the frame.
	 */
	public ViewUserFrame() {
		userTable = new UserTableProcessing();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 424, 209);
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete();
			}
		});
		btnDelete.setBounds(0, 218, 89, 23);
		panel.add(btnDelete);
		fillValuesInTable();
	}

	protected void delete() {
		int row = table.getSelectedRow();
		String userName = table.getValueAt(row, 0).toString();
		if(userTable.contains(userName)) userTable.delete(userName);
		fillValuesInTable();
	}

	private void fillValuesInTable() {
		LinkedList list = userTable.userList();
		table.setModel(new DefaultTableModel(COLUMNS, list.size()));
		Traveller traveller = list.traveller();
		int row = 0;
		while(traveller.hasNext()){
			User next = (User) traveller.next();
			table.setValueAt(next.getName(), row, 0);
			table.setValueAt(next.isAdmin() ? "Admin" : "Manager", row, 1);
			row++;
		}
	}
}
