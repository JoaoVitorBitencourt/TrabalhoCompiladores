package Writer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Escritor {

	public void Escritor(File file, String Text) throws IOException {
		
		FileWriter arq = new FileWriter(file);
		PrintWriter gravarArq = new PrintWriter(arq);
		gravarArq.printf(Text);
		arq.close();
	}
	
}
