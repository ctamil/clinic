package immunization;

import java.io.File;

public class ImmunizationManager {

	String regId;
	
	public ImmunizationManager(String regId) {
		this.regId = regId;
		initilizeFile();
	}

	private void initilizeFile() {
		String currentDir = System.getProperty("user.dir");
		StringBuilder buildPath = new StringBuilder(currentDir+"\\hkc\\");
		for(int index = 3; index<regId.length(); index++) {
			buildPath.append(regId.charAt(index)+"\\");
		}
		File file = new File(buildPath.toString());
		if(!file.exists()){
			System.out.println("File doesn't exists");
			if(file.mkdirs())System.out.println("Diersctories are created");
		}
		if(file.exists()) System.out.println("file exists");
		System.out.println(buildPath);
	}
	
	public static void main(String []args){
		new ImmunizationManager("HKC000616");
	}
	
}
