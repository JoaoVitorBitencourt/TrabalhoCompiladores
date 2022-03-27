package Reader;

import java.io.BufferedReader;
import java.io.File;
//import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;

public class Reader {
	
	
	   public String Leitura(File file) throws IOException {
		   
		   String linha = "", texto = "";
		   FileReader arq = new FileReader(file);
		   BufferedReader lerArq = new BufferedReader(arq);
		   linha = lerArq.readLine();
		   while (linha != null) {
		        texto = texto + linha + "\n" ;

		        linha = lerArq.readLine(); 
		      }
		      arq.close();
		      
		      return texto;
	   	}
}
