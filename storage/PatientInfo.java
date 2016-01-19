package storage;

import database.PatientTableProcessing;
import ds.Container;
import ds.NumberTrie;
import ds.TerenaryTrie;
import dto.Patient;

public class PatientInfo {

	private Container patients;
	private int size;
	private PatientTableProcessing table;
	private int thershould = 1000;
	private static PatientInfo PATIENT_INFO;
	
	private PatientInfo() {
		table = new PatientTableProcessing();
		size = table.getSize();
		if(size <= thershould) patients = new NumberTrie();
		else patients = new TerenaryTrie();
		table.addToContainer(patients);
		System.out.println("Container Size: "+patients.size());
	}
	
	public static PatientInfo getInstance(){
		if(PATIENT_INFO == null) PATIENT_INFO = new PatientInfo();
		return PATIENT_INFO;
	}
	
	public boolean add(Patient patient){
		patients.add(patient); //added to container
		return table.addToTable(patient); //added to database
	}
	
	public boolean contains(String contact){
		return patients.contains(contact);
	}
	
}
