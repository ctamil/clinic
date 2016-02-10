package panels;

import java.time.LocalDate;
import java.util.Calendar;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import dto.CustomDate;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;

public class DatePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private DatePicker picker;
	private JFXPanel fxPanel;
	private static DatePanel INSTANCE;
	
	public static DatePanel getInstance(JComponent nextComponentToFocus){
		if(INSTANCE == null) INSTANCE = new DatePanel(nextComponentToFocus);;
		return INSTANCE;
	}
	
	public static DatePanel getInstance(){
		return getInstance(null);
	}
	
	private DatePanel(JComponent nextComponentToFocus) {
		setLayout(null);
		
		textField = new JTextField();
		textField.setToolTipText("Day");
		textField.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				if(textField.getText().length() == 2) textField_1.requestFocusInWindow();
			}
		});
		textField.setBounds(0, 11, 32, 20);
		add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setToolTipText("Month");
		textField_1.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				if(textField_1.getText().length() == 2) textField_2.requestFocusInWindow();
			}
		});
		textField_1.setColumns(10);
		textField_1.setBounds(52, 11, 42, 20);
		add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setToolTipText("Year");
		textField_2.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				if(textField_2.getText().length() == 4) {
						int day = Integer.parseInt(textField.getText());
						int month = Integer.parseInt(textField_1.getText());
						int year = Integer.parseInt(textField_2.getText());
						picker.setValue(null);
						picker.setValue(LocalDate.of(year, month, day));
						if(nextComponentToFocus != null ) nextComponentToFocus.requestFocusInWindow();
				}
			}
		});
		textField_2.setColumns(10);
		textField_2.setBounds(114, 11, 42, 20);
		add(textField_2);

		JLabel label = new JLabel("/");
		label.setBounds(42, 14, 13, 14);
		add(label);

		JLabel label_1 = new JLabel("/");
		label_1.setBounds(104, 14, 13, 14);
		add(label_1);
		
		JLabel label_2 = new JLabel(":");
		label_2.setBounds(166, 14, 20, 14);
		add(label_2);
	
		setDatePicker();
		picker.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				LocalDate date = picker.getValue();
				if(date == null) return;
				String day = String.valueOf(date.getDayOfMonth());
				String month = String.valueOf(date.getMonthValue());
				String year = String.valueOf(date.getYear());
				if(!textField.getText().equals(day)) textField.setText(day);
				if(!textField_1.getText().equals(month)) textField_1.setText(month);
				if(!textField_2.getText().equals(year)) textField_2.setText(year);
			}
		});
	}

	@Override
	public void setVisible(boolean aFlag) {
		setDatePicker();
		System.out.println("Set visible called");
		super.setVisible(aFlag);
	}
	
	private void setDatePicker() {
		fxPanel = new JFXPanel();
		fxPanel.setBounds(176, 11, 133, 20);
		add(fxPanel);

		picker = new DatePicker();
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				fxPanel.setScene(new Scene(picker));
			}
		});
	}

	public void reset(){
		textField.setText("");
		textField_1.setText("");
		textField_2.setText("");
	}

	public Calendar getCalender() {
		LocalDate date = picker.getValue();
		if(date == null) return null;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, date.getDayOfMonth());
		cal.set(Calendar.MONTH, date.getMonthValue()-1);
		cal.set(Calendar.YEAR, date.getYear());
		
		return cal;
	}
	
	public void setDate(CustomDate date){
		if(date != null) picker.setValue(LocalDate.of(date.getYear(), 
				date.getMonth(), date.getDay()));
	}
}
