package panels;

import java.util.Calendar;

import javax.swing.JComponent;
import javax.swing.JPanel;

import dto.CustomDate;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.DefaultComboBoxModel;

public class DatePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static DatePanel INSTANCE;
	private JComboBox<String> comboBoxYear, comboBoxMonth, comboBoxDay;
	
	
	public static DatePanel getInstance(JComponent nextComponentToFocus){
		if(INSTANCE == null) INSTANCE = new DatePanel(nextComponentToFocus);;
		return INSTANCE;
	}
	
	public static DatePanel getInstance(){
		return getInstance(null);
	}
	
	private DatePanel(JComponent nextComponentToFocus) {
		setLayout(null);
		
		comboBoxYear = new JComboBox<>();
		comboBoxYear.setBounds(0, 11, 91, 20);
		fillYear();
		add(comboBoxYear);
		
		comboBoxMonth = new JComboBox<>();
		comboBoxMonth.setModel(new DefaultComboBoxModel<String>(new String[] {"1 - January", "2 - February", "3 - March", "4 - April", "5 - May", "6 - June", "7 - July", "8 - August", "9 - September", "10 - October", "11 - November", "12 - December"}));
		comboBoxMonth.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				updateComboBoxDay();
			}
		});
		comboBoxMonth.setBounds(101, 11, 113, 20);
		add(comboBoxMonth);
		
		comboBoxDay = new JComboBox<>();
		comboBoxDay.setBounds(224, 11, 65, 20);
		add(comboBoxDay);
	
	}
	
	private void fillYear() {
		int end = Calendar.getInstance().get(Calendar.YEAR);
		int start = end - 17;
		while(++start <= end) comboBoxYear.addItem(String.valueOf(start)); 
	}

	private void updateComboBoxDay() {
		if(comboBoxYear != null && comboBoxMonth != null)
			if(comboBoxYear.getSelectedIndex() > -1 && comboBoxMonth.getSelectedIndex() > -1)
				updateComboBoxDay(Integer.parseInt(comboBoxYear.getSelectedItem().toString()), comboBoxMonth.getSelectedIndex()+1);
	}

	private void updateComboBoxDay(int year, int month) {
		if(comboBoxDay.getItemCount() > 0)comboBoxDay.removeAllItems();
		int start = 0;
		int end = getDaysOfMonth(year, month);
		while(++start <= end) comboBoxDay.addItem(String.valueOf(start));
	}

	public void reset(){
		comboBoxYear.setSelectedIndex(-1);
		comboBoxMonth.setSelectedIndex(-1);
		if(comboBoxDay.getItemCount() > 0) comboBoxDay.setSelectedIndex(-1); //if it has items
	}

	public Calendar getCalender() {
		Calendar cal = Calendar.getInstance();
		int year = Integer.parseInt(comboBoxYear.getSelectedItem().toString());
		int month = comboBoxMonth.getSelectedIndex()+1;
		int day = comboBoxDay.getSelectedIndex()+1;
		cal.set(year, month, day);
		return cal;
	}
	
	public void setDate(CustomDate date){
		if(containsYear(date.getYear())) comboBoxYear.setSelectedItem(date.getYear());
		else {
			comboBoxYear.addItem(String.valueOf(date.getYear()));
			setDate(date);
		}
		comboBoxMonth.setSelectedIndex(date.getMonth()-1);
		comboBoxDay.setSelectedIndex(date.getDay()-1);
	}
	
	private boolean containsYear(int year) {
		for(int i=0; i<comboBoxYear.getItemCount(); i++) if(String.valueOf(year).equals(comboBoxYear.getItemAt(i))) return true;
		return false;
	}

	public int getDaysOfMonth(int year, int month){
		int days = -1;
		switch(month){
		case 1:  //Janaury
		case 3: 
		case 5:
		case 7:
		case 8:
		case 10:
		case 12: days = 31; break;
		case 4:
		case 6:
		case 9:
		case 11: days = 30; break;
		case 2: return getDaysOFMonthOfFeb(year); //check for leap year
		}
		return days;
	}

	/*
	 * Method source... 
	 * 	1. If the year is evenly divisible by 4, go to step 2. Otherwise, go to step 5.
	 * 	2. If the year is evenly divisible by 100, go to step 3. Otherwise, go to step 4.
	 *	3. If the year is evenly divisible by 400, go to step 4. Otherwise, go to step 5.
	 *	4. The year is a leap year (it has 366 days).
	 *	5. he year is not a leap year (it has 365 days).
	 */
	private int getDaysOFMonthOfFeb(int year) {
		if(year % 4 == 0) {
			if(year % 100 == 0 && year % 400 != 0) return 28;
			else return 29;
		}
		else return 28;
	}
}
