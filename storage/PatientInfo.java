package storage;

import database.PatientTableProcessing;
import ds.Container;
import ds.NumberTrie;
import ds.TerenaryTrie;

public class PatientInfo {

	private Container patients;
	private int size;
	private PatientTableProcessing table;
	private int thershould = 1000;
	
	private PatientInfo() {
		table = new PatientTableProcessing();
		size = table.getSize();
		if(size <= thershould) patients = new NumberTrie();
		else patients = new TerenaryTrie();
		table.addToContainer(patients);
	}
	
	
}
