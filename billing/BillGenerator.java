package billing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import dto.Bill;

public class BillGenerator {

	private static Bill bill;
	private static File outFile;
	private static XWPFDocument doc;
	
	/**
	 * Adding bill details to file.
	 * @param bill
	 * @param outputPath
	 * @param inputPath
	 * @return
	 */
	public static File write(Bill bill, String outputPath, String inputPath){
		init(bill, outputPath, inputPath);
		addPatientDetails();
		addItemsToBill();
		try {
			doc.write(new FileOutputStream(outFile));
			doc.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error In Finalizing Document", JOptionPane.ERROR_MESSAGE);
		}
		filnalizeTheDocument();
		return outFile;
	}
	
	/**
	 * Initialize method variables to static variables.
	 * Creating the XWPFDocument Object to write the bill.
	 * @param bill
	 * @param outputPath
	 * @param inputPath
	 */
	private static void init(Bill bill, String outputPath, String inputPath) {
		BillGenerator.bill = bill;
		outFile = new File(outputPath+"\\"+BillGenerator.bill.getId()+".docx");
		try {
			doc = new XWPFDocument(new FileInputStream(new File(inputPath)));
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "File Not Found", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error In Accessing File", JOptionPane.ERROR_MESSAGE);
		}
	}


	

	/*
	 * Adding all items from bill object to document
	 */
	private static void addItemsToBill() {
		
	}

	/*
	 * Adding patient details to  bill.
	 */
	private static void addPatientDetails() {
		
	}

	private static void filnalizeTheDocument() {
		bill = null;
		doc = null;
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		XWPFDocument doc = new XWPFDocument(new FileInputStream("C:\\workspace\\ClincManagement\\bill_format.docx"));
		XWPFTable table = doc.getTables().get(0);
		System.out.println(table.getNumberOfRows());
		XWPFTableRow row = table.getRow(0);
		for(int i=0; i<15; i++) table.addRow(row, 1);
		XWPFTableCell cell = row.getCell(0);
		cell.setText("Item1");
		doc.write(new FileOutputStream("C:\\workspace\\ClincManagement\\bill_format1.docx"));
		doc.close();
	}

}
