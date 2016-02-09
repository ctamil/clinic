package frames;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import panels.AddStockPanel;

public class AddStockFrame extends JFrame {

	private static final long serialVersionUID = 6103801594798716773L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public AddStockFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 543, 426);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		AddStockPanel panel = new AddStockPanel();
		panel.setBounds(10, 11, 507, 366);
		contentPane.add(panel);
	}
}
