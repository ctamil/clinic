package utils;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;


public class LocalNetworkFileSearch {

	private static String NETWORKS[] = {"TAMIL-PC", "SYSTEM-1", "SYSTEM-2", "HKC-1", "HKC-2"};
	
	public static void find(String file) {
		for(String network : NETWORKS){
			String filePath = "file:////"+network+"/"+file;
			URI uri = null;
			try {
				filePath = filePath.replace('\\', '/'); //updating file path to uri format
				uri = new URI(filePath); 
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			File checkFile = new File(uri);
			if(checkFile.exists()) {
				FileRunner.openWord(checkFile); 
				return;
			}
		}
	}

}
