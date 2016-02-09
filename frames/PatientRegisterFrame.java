package frames;

import javax.swing.JFrame;
import javax.swing.JPanel;

import panels.register.RegisterationPanel;

public class PatientRegisterFrame extends JFrame {

	private static final long serialVersionUID = 4835582550600554444L;
	private JPanel contentPane;


	/**
	 * Create the frame.
	 */
	public PatientRegisterFrame() {
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0, 800, 719);
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		RegisterationPanel panel = new RegisterationPanel();
		contentPane.add(panel);
		panel.setBounds(0, 0, 800, 719);
		
	}

}
