package frames;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import panels.AddStockPanel;
import panels.AvailableStockPanel;
import panels.BillingPanel;
import panels.PatientSearchPanel;
import panels.register.RegisterationPanel;

public class MainPage extends JFrame {


	private static final long serialVersionUID = 7484169762847527457L;
	private JPanel contentPane;

	public MainPage() {
		setIconImage(new ImageIcon(System.getProperty("user.dir")+"\\logo.png").getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 800, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		panel.add(tabbedPane, BorderLayout.CENTER);
		tabbedPane.add("Register", new RegisterationPanel());
		tabbedPane.add("Patient Search", new PatientSearchPanel());
		tabbedPane.add("Billing", new BillingPanel());
		tabbedPane.add("Availbale Stock", new AvailableStockPanel());
		tabbedPane.add("Add Stock", new AddStockPanel());
	}

	public static void main(String []args){
		new MainPage().setVisible(true);
	}
}
