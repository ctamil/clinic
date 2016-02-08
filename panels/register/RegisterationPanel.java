package panels.register;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegisterationPanel extends JPanel {

	private static final long serialVersionUID = -9176854268630485226L;
	private RegisterationDetailsPanel registerationDetailsPanel;


	public RegisterationPanel() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel label = new JLabel("Patient Registration Form ");
		label.setFont(new Font("Tahoma", Font.BOLD, 14));
		label.setBounds(31, 11, 216, 29);
		panel.add(label);
		
		JButton button = new JButton("Register");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(registerationDetailsPanel.register()){
					registerationDetailsPanel.reset();
				}else JOptionPane.showMessageDialog(null, "Registration Not Completed");
			}
		});
		
		button.setBounds(32, 591, 138, 23);
		panel.add(button);
		
		JButton button_1 = new JButton("Reset");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registerationDetailsPanel.reset();
			}
		});
		button_1.setBounds(231, 591, 138, 23);
		panel.add(button_1);
		
		registerationDetailsPanel = new RegisterationDetailsPanel();
		registerationDetailsPanel.setBounds(31, 46, 564, 526);
		panel.add(registerationDetailsPanel);

	}
}
