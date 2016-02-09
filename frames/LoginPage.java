package frames;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import database.UserTableProcessing;
import dto.User;
import storage.UserInfo;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.KeyStroke;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame {

	private static final long serialVersionUID = -3415903890751829448L;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private UserTableProcessing userTable;
	
	public LoginPage() {
		userTable = new UserTableProcessing();
		setIconImage(new ImageIcon(System.getProperty("user.dir")+"\\logo.png").getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 609, 379);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(System.getProperty("user.dir")+"\\logo.png"));
		label.setBounds(119, 11, 280, 126);
		panel.add(label);
		
		JLabel lblUserName = new JLabel("User Name: ");
		lblUserName.setBounds(136, 184, 95, 14);
		panel.add(lblUserName);
		
		textField = new JTextField();
		textField.setBounds(261, 181, 163, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password: ");
		lblPassword.setBounds(136, 219, 95, 14);
		panel.add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(261, 216, 163, 20);
		panel.add(passwordField);
		passwordField.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "Enter");
		passwordField.getActionMap().put("Enter", new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -3199949738026602278L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				login();
			}
		});
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				login();
			}
		});
		btnLogin.setBounds(136, 275, 89, 23);
		panel.add(btnLogin);
		
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reset();
			}
		});
		btnReset.setBounds(336, 275, 89, 23);
		panel.add(btnReset);
	}

	private void login() {
		String userName = textField.getText();
		String password = new String(passwordField.getPassword());
		if(userName != null && password != null && userTable.isValidUser(userName, password)){
			User user = new User(userName, userTable.isAdmin(userName), password);
			UserInfo.setUSER(user);
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					new MainPage().setVisible(true);
				}
			});
			dispose();
		}
		else JOptionPane.showMessageDialog(this, "Incorrect User name or password");
	}

	protected void reset() {
		textField.setText("");
		passwordField.setText("");
	}
	
}
