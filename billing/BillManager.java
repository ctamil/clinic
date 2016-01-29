package billing;

import java.io.File;
import java.util.Calendar;

import dto.Bill;

public class BillManager {

	private Bill bill;
	
	public BillManager(Bill bill) {
		this.bill = bill;
	}
	
	public File generateBill(){

		String dir = System.getProperty("user.dir");
		File inputFile = new File(dir+"\\bill_format.docx");
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;
		int day = cal.get(Calendar.DATE);
		
		String outPath = dir+"\\bills\\"+year+"\\"+month+"\\"+day;
		new File(outPath).mkdirs();
		
		return BillGenerator.write(bill, outPath, inputFile);
	}
}
