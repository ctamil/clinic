package utils;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

public class FileRunner {
	
	public static boolean openWord(String filePath){
		Runtime run = Runtime.getRuntime();
		try {
			run.exec("C:\\Program Files (x86)\\Microsoft Office\\Office12\\Winword.exe /t "+filePath);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error in opening in word document: "+e.getMessage());
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static boolean openWord(File filePath){
		return openWord(filePath.getAbsolutePath());
	}
	
	public static void openExcel(String filePath){
		Runtime run = Runtime.getRuntime();
		try {
			run.exec("C:\\Program Files (x86)\\Microsoft Office\\Office12\\excel.exe "+filePath);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error in opening in word document: "+e.getMessage());
			e.printStackTrace();
		}
	}
}
