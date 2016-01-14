package panel;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JLabel;

public class DesignedJFrame extends JFrame {


	/**
	 * 
	 */
	private static final long serialVersionUID = 6983886182891891989L;

	/**
	 * Create the panel.
	 */
	public DesignedJFrame() {
		getContentPane().setLayout(new BorderLayout(0, 0));
		JLabel lblNewLabel = new JLabel("");
		getContentPane().add(lblNewLabel, BorderLayout.CENTER);
		//Image img = getToolkit().getImage(System.getProperty("user.dir")+"\\bg.png");
		
	}
}
