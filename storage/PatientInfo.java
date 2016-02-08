package storage;

import database.PatientDetailsTableProcessing;
import database.PatientTableProcessing;
import ds.Container;
import ds.PrefixSearch;
import ds.TerenaryTrie;
import ds.Traveller;
import ds.Trie;
import dto.Patient;
import dto.PatientDetails;

public class PatientInfo {

	private PrefixSearch patientsKeyAsId;
	private PrefixSearch patientsKeyAsPhoneNumber;
	private int size;
	private PatientTableProcessing patientTable;
	private PatientDetailsTableProcessing patientDetailsTable;
	private int thershould = 10000;
	private static PatientInfo PATIENT_INFO;
	
	private PatientInfo() {
		patientTable = new PatientTableProcessing();
		patientDetailsTable = new PatientDetailsTableProcessing();
		size = patientTable.getSize();
		if(size <= thershould) {
			patientsKeyAsId = new Trie();
			patientsKeyAsPhoneNumber = new Trie();
			
		}
		else {
			patientsKeyAsId = new TerenaryTrie();
			patientsKeyAsPhoneNumber = new TerenaryTrie();
		}
		
		Traveller traveller = patientTable.getList().traveller();
		while (traveller.hasNext()) {
			Patient pat = (Patient) traveller.next();
			patientsKeyAsId.add(pat, pat.getId());
			patientsKeyAsPhoneNumber.add(pat, pat.getPhoneNumber());
		}
	}
	
	/**
	 * s
	 * @return instance of this class.
	 * it returns the same instance in all the time which was created first.
	 * it maintains single instance of this class.
	 */
	public static PatientInfo getInstance(){
		if(PATIENT_INFO == null) PATIENT_INFO = new PatientInfo();
		return PATIENT_INFO;
	}
	
	/**
	 * @param key phone number or id. id length = 9
	 * minimum length of phone number = 10 and max = 15.
	 * @return if length of key greater than equal to 10, then searches in phone number container
	 * else it searches in id container and return the value.
	 */
	public Patient getPatient(String key){
		if(key.length() < 10) return (Patient) ((Container)patientsKeyAsId).get(key);
		else return (Patient) ((Container)patientsKeyAsPhoneNumber).get(key);
	}
	
	public PatientDetails getPatientDetails(Patient patient){
		return patientDetailsTable.getPatientDetailsById(patient.getId());
	}
	/**
	 * @param patient
	 * @return true if it was successfully added to database.
	 */
	public boolean add(Patient patient, PatientDetails patienDetails){
		if(patientTable.addToDB(patient) && patientDetailsTable.addToDB(patienDetails)){ //adds to database and confirms.
			patientsKeyAsId.add(patient, patient.getId()); 
			patientsKeyAsPhoneNumber.add(patient, patient.getPhoneNumber());
			return true;
		}else return false;
	}
	
	public void delete(Patient patient){
		((Container)patientsKeyAsId).remove( patient.getId());
		((Container)patientsKeyAsPhoneNumber).remove(patient.getPhoneNumber());
		patientTable.removeFromDB(patient);
		patientDetailsTable.removeFromDb(patient);
	}
	
	/**
	 * @param key should be phone number or id
	 * @return
	 */
	public boolean contains(String key){
		if(key.length() < 10) return  ((Container)patientsKeyAsId).contains(key);
		else return ((Container)patientsKeyAsPhoneNumber).contains(key);
	}
	
	public Traveller searchPatientById(String id){
		return patientsKeyAsId.prefix(id);
	}
	
	public Traveller searchPatientByPhoneNumber(String number){
		return patientsKeyAsPhoneNumber.prefix(number);
	}
	
	public Traveller searchPatientByName(String name){
		return patientTable.searchPatients(name).traveller();
	}

	public Traveller searchPatient(String key, String type){
		if(type.equals("registeration id")) {
			System.out.println("registeration id");
			return searchPatientById(key);
		}
		else if(type.equals("phone number")) return searchPatientByPhoneNumber(key);
		else return searchPatientByName(key);
	}
}
