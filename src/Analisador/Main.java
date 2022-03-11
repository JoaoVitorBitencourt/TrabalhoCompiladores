package Analisador;

import java.util.Stack;

import Analisador.AnalisadorLexico;
import Analisador.Token;

public class Main {

	
	public static void main(String args[]) {
        new Main().executar();
    }
    
    public void executar(){
        String programa = 
                "PROGRAM NOMEPROGRAMA ; "
                + "VAR "
                + "   X , Y , Z : INTEGER ; "
                + "BEGIN "
                + "   X := 10 ; "
                + "   Y := 20 ; "
                + "   Z := 30 ; "
                + "  REPEAT "
                + "    BEGIN "
                + "      WRITELN ( X , Y , Z ) ; "
                + "    END "
                + "  UNTIL X > 10 ; "
                + "TESTE . "
                ;
        
        Stack<Token> tokens = new AnalisadorLexico().gerarTokens(programa);
        
        System.out.println(tokens.elementAt(1));
    }
    
    
}
	
