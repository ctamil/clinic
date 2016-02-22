package immunization;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import storage.PatientInfo;
import utils.FileRunner;

public class ImmunizationManager {

	private String regId;
	private File inFile;
	private File outFile;
	
	public ImmunizationManager(String regId) {
		inFile = new File(System.getProperty("user.dir")+"\\immunization_table_format.xlsx");
		this.regId = regId;
		outFile = initilizeOutFile();
	}

	private void writeOnFile(File outFile) {
		try {
			String patientName = PatientInfo.getInstance().getPatient(regId).getName();
			
			XSSFWorkbook workBook = new XSSFWorkbook(inFile);
			XSSFCreationHelper helper = new XSSFCreationHelper(workBook);
			XSSFSheet sheet = workBook.getSheetAt(0);
			XSSFRow patientDetailsRow = sheet.getRow(2);
			patientDetailsRow.getCell(0).setCellValue(helper.createRichTextString("Patient ID: "+regId));
			patientDetailsRow.getCell(2).setCellValue(helper.createRichTextString("Name: "+patientName));
			workBook.write(new FileOutputStream(outFile));
			workBook.close();
		
		} catch (InvalidFormatException | IOException e) {
			System.out.println("Error ikn writing to excel file: "+e.getMessage());
			e.printStackTrace();
		}
		
	}

	private File initilizeOutFile() {
		String currentDir = System.getProperty("user.dir");
		StringBuilder buildPath = new StringBuilder(currentDir+"\\hkc\\");
		for(int index = 3; index<regId.length(); index++) {
			buildPath.append(regId.charAt(index)+"\\");
		}
		File file = new File(buildPath.toString());
		File outFile = new File(buildPath+regId+".xlsx");
		if(!file.exists()){
			if(file.mkdirs()) writeOnFile(outFile); //if it was a new file then write on it.
		}
		return outFile;
	}
	
	public void openOutFile(){
		FileRunner.openExcel(outFile.getAbsolutePath());
	}
}
