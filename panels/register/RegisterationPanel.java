package panels.register;

import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegisterationPanel extends JPanel {

	private static final long serialVersionUID = -9176854268630485226L;
	private RegisterationDetailsPanel registerationDetailsPanel;
	private JButton btnReset;
	private JButton btnRegister;

	public RegisterationPanel(final JFrame parentFrame) {
		
		setLayout(new BorderLayout(0, 0));
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel label = new JLabel("Patient Registration Form ");
		label.setFont(new Font("Tahoma", Font.BOLD, 14));
		label.setBounds(31, 11, 216, 29);
		panel.add(label);
		
		btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(registerationDetailsPanel.register()){
					registerationDetailsPanel.reset();
					parentFrame.dispose();
				}else JOptionPane.showMessageDialog(null, "Registration Not Completed");
			}
		});
		
		btnRegister.setBounds(32, 591, 138, 23);
		panel.add(btnRegister);
		
		btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registerationDetailsPanel.reset();
			}
		});
		btnReset.setBounds(231, 591, 138, 23);
		panel.add(btnReset);
		
		registerationDetailsPanel = new RegisterationDetailsPanel();
		registerationDetailsPanel.setBounds(31, 46, 564, 526);
		panel.add(registerationDetailsPanel);

	}
}
