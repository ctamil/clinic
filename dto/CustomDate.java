package dto;

import java.util.Calendar;

public class CustomDate {

	private int year, month/*0-11*/, day;

	private CustomDate() {
		super();
	}
	
	public CustomDate(int year, int month, int day) {
		this();
		setYear(year);
		setMonth(month);
		setDay(day);
	}
	
	public CustomDate(String date){
		this();
		String split[] = date.split("[.]");
		setDay(Integer.parseInt(split[0]));
		setMonth(Integer.parseInt(split[1])-1);
		setYear(Integer.parseInt(split[2]));
	}
	
	public CustomDate(java.sql.Date date){
		this();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		setDay(cal.get(Calendar.DAY_OF_MONTH));
		setMonth(cal.get(Calendar.MONTH));
		setYear(cal.get(Calendar.YEAR));
	}

	public java.sql.Date getSQLDate(){
		Calendar cal = Calendar.getInstance();
		cal.set(getYear(), getMonth(), getDay());
		return new java.sql.Date(cal.getTimeInMillis());
	}
	
	public Calendar getCalenderDate(){
		Calendar cal = Calendar.getInstance();
		cal.set(getYear(), getMonth(), getDay());
		return cal;
	}

	public int getYear() {
		return year;
	}
	private void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}
	private void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}
	private void setDay(int day) {
		this.day = day;
	}
	
	@Override
	public String toString() {
		String sDay = String.valueOf(day);
		sDay = sDay.length() == 1 ? 0+sDay : sDay;  //insert zero in starting if it has only single value.
		String sMonth = String.valueOf(month+1); /* (0 - 11) -->> (1 - 12) */ 
		sMonth = sMonth.length() == 1 ? "0"+sMonth : sMonth;
		return sDay+"."+sMonth+"."+year;
	}	
}
