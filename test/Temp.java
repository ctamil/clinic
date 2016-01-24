package test;

import java.util.Random;

import storage.PatientInfo;
import dto.Patient;

public class Temp {

	public static void main(String[] args) {
		Random r = new Random();
		System.out.println("Started");
		for(int i=0; i<10000; i++){
			Patient p = new Patient("logesh", 0, "641006", "ganapthy", "", "", "", 6, 6, 1994, true, "");
			long val = Math.abs(r.nextLong());
			if(val > 9999999999l) val %= 10e9;
			while(val < 10e9) val*= 10;
			p.setContactNo(val);
			if(!PatientInfo.getInstance().contains(String.valueOf(val))) 
					PatientInfo.getInstance().add(p);
			else System.out.println("Number Already Exists: "+val);
		}
		System.out.println("Completed");
	}
}
