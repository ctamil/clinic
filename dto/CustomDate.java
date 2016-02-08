package dto;

import java.util.Calendar;

public class CustomDate {

	private int year, month, day;
	private Calendar calendar;

	private CustomDate() {
		super();
		calendar = Calendar.getInstance();
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
		setMonth(Integer.parseInt(split[1]));
		setYear(Integer.parseInt(split[2]));
	}
	
	public CustomDate(java.sql.Date date){
		this();
		calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date.getTime());
		sync(calendar);
	}
	
	private void sync(Calendar calendar) {
		setYear(calendar.get(Calendar.YEAR));
		setMonth(calendar.get(Calendar.MONTH)+1); //months in calendar starts with zero.
		setDay(calendar.get(Calendar.DAY_OF_MONTH));
	}

	public CustomDate(Calendar calendar){
		this();
		sync(calendar);
	}
	
	public Calendar getCalender(){
		return calendar;
	}
	
	public java.sql.Date getSQLDate(){
		return new java.sql.Date(calendar.getTimeInMillis());
	}

	public int getYear() {
		return year;
	}

	private void setYear(int year) {
		calendar.set(Calendar.YEAR, year);
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	private void setMonth(int month) {
		calendar.set(Calendar.MONTH, month-1);
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	private void setDay(int day) {
		calendar.set(Calendar.DAY_OF_MONTH, day);
		this.day = day;
	}
	
	@Override
	public String toString() {
		String sDay = String.valueOf(day);
		sDay = sDay.length() == 1 ? 0+sDay : sDay;  //insert zero in starting if it has only single value.
		String sMonth = String.valueOf(month);
		sMonth = sMonth.length() == 1 ? "0"+sMonth : sMonth;
		return sDay+"."+sMonth+"."+year;
	}	
}
