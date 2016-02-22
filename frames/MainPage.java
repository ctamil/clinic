package frames;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import frames.patients.PatientRegisterFrame;
import panels.AvailableStockPanel;
import panels.BillingPanel;
import panels.PatientSearchPanel;
import storage.UserInfo;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import database.CategoryTableProcessing;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainPage extends JFrame {


	private static final long serialVersionUID = 7484169762847527457L;
	private JPanel contentPane;
	private JMenuBar menuBar;
	private JTabbedPane tabbedPane;
	
	public MainPage() {
		setIconImage(new ImageIcon(System.getProperty("user.dir")+"\\logo.png").getImage());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0, 881, 719);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		panel.add(tabbedPane);
		tabbedPane.add("Patient Search", new PatientSearchPanel());
		tabbedPane.add("Billing", new BillingPanel());
		tabbedPane.setBounds(0, 32, 807, 628);
		
		menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 855, 21);
		panel.add(menuBar);
		
		JMenu mnFile = new JMenu("Menu");
		menuBar.add(mnFile);
		
		JMenuItem mntmPatientRegister = new JMenuItem("Patient Register");
		mntmPatientRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						new PatientRegisterFrame().setVisible(true);
					}
				});
			}
		});
		mnFile.add(mntmPatientRegister);
		
		JMenuItem mntmAddStock = new JMenuItem("Add Stock");
		mntmAddStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						new AddStockFrame().setVisible(true);
					}
				});
			}
		});
		mnFile.add(mntmAddStock);
		
		JMenuItem mntmLogout = new JMenuItem("Logout");
		mntmLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new LoginPage().setVisible(true);
				dispose();
			}
		});
		mnFile.add(mntmLogout);
		
		JMenu mnCategory = new JMenu("Category");
		menuBar.add(mnCategory);
		
		JMenuItem mntmAdd = new JMenuItem("Add");
		mntmAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CategoryTableProcessing().addCategory();
			}
		});
		mnCategory.add(mntmAdd);
		
		JMenuItem mntmDelete = new JMenuItem("Delete");
		mntmDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CategoryTableProcessing().deleteCategory();
			}
		});
		mnCategory.add(mntmDelete);
		if(UserInfo.getUser().isAdmin()) addAdminContents();
	}

	private void addAdminContents() {
		tabbedPane.add("Availbale Stock", new AvailableStockPanel());
		addUserMenu();
	}

	private void addUserMenu() {
		JMenu mnUser = new JMenu("User");
		menuBar.add(mnUser);
		
		JMenuItem mntmAddUset = new JMenuItem("Add User");
		mntmAddUset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddUser().setVisible(true);
			}
		});
		mnUser.add(mntmAddUset);
		
		JMenuItem mntmViewUsers = new JMenuItem("View Users");
		mntmViewUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ViewUserFrame().setVisible(true);
			}
		});
		mnUser.add(mntmViewUsers);
	}
}
