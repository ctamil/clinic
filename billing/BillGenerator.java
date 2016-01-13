package billing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import javax.swing.JOptionPane;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import ds.Traveller;
import dto.Bill;
import dto.Item;

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
	public static File write(Bill bill, String outputPath, File inFile){
		init(bill, outputPath, inFile);
		addPatientDetails();
		addItemsToBill();
		addTotal();
		try {
			doc.write(new FileOutputStream(outFile));
			doc.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error In Finalizing Document", JOptionPane.ERROR_MESSAGE);
		}
		return outFile;
	}
	
	private static void addTotal() {
		XWPFTable table = doc.getTables().get(2);
		addToCell("Amount: "+bill.getTotal(), table.getRow(0).getCell(0), 10);
	}

	/**
	 * Initialize method variables to static variables.
	 * Creating the XWPFDocument Object to write the bill.
	 * @param bill
	 * @param outputPath
	 * @param inputPath
	 */
	private static void init(Bill bill, String outputPath, File inFile) {
		BillGenerator.bill = bill;
		outFile = new File(outputPath+"\\"+BillGenerator.bill.getId()+".docx");
		try {
			doc = new XWPFDocument(new FileInputStream(inFile));
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
		Traveller itemTraveller = bill.getItems().traveller();
		XWPFTable table = doc.getTables().get(1);  
		int index = 1;
		while(itemTraveller.hasNext()){
			table.createRow();
			Item item = (Item) itemTraveller.next();
			addItem(index, table.getRow(index), item);
			index++;
		}
	}

	private static void addItem(int index, XWPFTableRow row, Item item){
		addToCell(index + "" , row.getCell(0), 10);
		addToCell(item.getName(), row.getCell(1), 10);
		addToCell(item.getQuantity() + "" , row.getCell(2), 10);
		addToCell(item.getPrice() + "", row.getCell(3), 10);
		addToCell(item.getTotal() + "", row.getCell(4), 10);
	}
	/*
	 * Adding patient details to  bill.
	 */
	private static void addPatientDetails() {
		XWPFTable table = doc.getTables().get(0);
		XWPFTableRow firstRow = table.getRow(0);
		XWPFTableRow secondRow = table.getRow(1);
		
		Calendar currCal = Calendar.getInstance();
		int date = currCal.get(Calendar.DATE);
		int month = currCal.get(Calendar.MONTH);
		int year = currCal.get(Calendar.YEAR);
		
		addToCell(bill.getId() + "", firstRow.getCell(0), 10);
		addToCell(date + "." + month + "." + year, firstRow.getCell(1), 10);
		addToCell(bill.getPatient().getName(), secondRow.getCell(0), 10);
		addToCell(bill.getPatient().getAge() + "" , secondRow.getCell(1), 10);
	}
	
	private static void addToCell(String text, XWPFTableCell cell, int fontSize){
		XWPFParagraph para = cell.addParagraph();
		XWPFRun run = para.createRun();
		run.setText(text);
		run.setFontSize(fontSize);
	}

}
