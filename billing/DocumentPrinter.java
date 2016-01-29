package billing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;

public class DocumentPrinter {

	public static void print(File file)  {
		try{
			PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();
			InputStream stream = new FileInputStream(file);
			DocPrintJob printJob = defaultPrintService.createPrintJob();
			SimpleDoc doc = new SimpleDoc(stream, DocFlavor.INPUT_STREAM.AUTOSENSE, null);
			printJob.print(doc, null);
		}catch(FileNotFoundException exp){
			exp.printStackTrace();
		}catch(PrintException exp){
			exp.printStackTrace();
		}
	}
}
