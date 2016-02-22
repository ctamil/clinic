package frames;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import database.UserTableProcessing;
import dto.User;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;

public class AddUser extends JFrame {

	private static final long serialVersionUID = 3300850507865999657L;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField txtPass;
	private JPasswordField txtConfirm;
	private UserTableProcessing userTable;
	private JRadioButton rdbtnAdmin;
	private ButtonGroup role;
	private JRadioButton rdbtnManger;

	/**
	 * Create the frame.
	 */
	public AddUser() {
		userTable = new UserTableProcessing();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 249);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblUserName = new JLabel("User Name: ");
		lblUserName.setBounds(10, 11, 106, 14);
		panel.add(lblUserName);
		
		textField = new JTextField();
		textField.setBounds(166, 11, 157, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password: ");
		lblPassword.setBounds(10, 48, 106, 14);
		panel.add(lblPassword);
		
		txtPass = new JPasswordField();
		txtPass.setBounds(166, 48, 157, 20);
		panel.add(txtPass);
		
		txtConfirm = new JPasswordField();
		txtConfirm.setBounds(166, 86, 157, 20);
		panel.add(txtConfirm);
		
		JLabel lblConformPassword = new JLabel("Confirm Password: ");
		lblConformPassword.setBounds(10, 89, 121, 14);
		panel.add(lblConformPassword);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		btnSave.setBounds(10, 158, 106, 23);
		panel.add(btnSave);
		
		JLabel lblUserRole = new JLabel("User Role: ");
		lblUserRole.setBounds(10, 125, 94, 14);
		panel.add(lblUserRole);
		
		rdbtnAdmin = new JRadioButton("Admin");
		rdbtnAdmin.setBounds(166, 121, 79, 23);
		panel.add(rdbtnAdmin);
		
		rdbtnManger = new JRadioButton("Manger");
		rdbtnManger.setBounds(256, 121, 109, 23);
		panel.add(rdbtnManger);
		role = new ButtonGroup();
		role.add(rdbtnAdmin);
		role.add(rdbtnManger);
	}
	
	private void save() {
		if(isValidUserDate()){
			String userName = textField.getText();
			String pass = new String(txtPass.getPassword());
			boolean isAdmin = rdbtnAdmin.isSelected();
			User user = new User(userName, isAdmin, pass);
			if(userTable.addToDB(user)) {
				JOptionPane.showMessageDialog(this, "User Saved");
				dispose();
			}
		}
	}
	
	private boolean isValidUserDate(){
		String userName = null, pass = null, cPass = null;
		if(txtPass != null && txtConfirm != null && txtPass.getPassword() != null && txtConfirm.getPassword() != null &&
				textField != null && textField.getText() != null && textField.getText().length() > 0){
			userName = textField.getText();
			pass = new String(txtPass.getPassword());
			cPass = new String(txtConfirm.getPassword());
			if(!pass.equals(cPass)){
				JOptionPane.showMessageDialog(this, "Password Doesn't Match");
				return false;	
			}
			if(userTable.contains(userName)){
				JOptionPane.showMessageDialog(this, "User Already Exits");
				return false;
			}
		}else {
			JOptionPane.showMessageDialog(this, "Invalid Input");
			return false;
		}
		return true;
	}
}
