package test;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class NetworkFiles {

	public static void main(String[] args) throws URISyntaxException {
		File file = new File(new URI("file:////TAMIL-PC/ClincManagement"));
		for(File f : file.listFiles()) System.out.println(f.getAbsolutePath());
	}

}
