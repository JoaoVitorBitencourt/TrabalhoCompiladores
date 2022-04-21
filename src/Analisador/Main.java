package Analisador;

import java.util.Stack;

import Analisador.AnalisadorLexico;
import Gramatica.Token;

public class Main {

    Stack<Token> pilha;

    public static void main(String args[]) {
        new Main().executar();
    }

    public void executar() {
        // String programa =
        // "PROGRAM NOMEPROGRAMA ; "
        // + "VAR "
        // + " X, Y, Z : INTEGER ; "
        // + "BEGIN "
        // + " X := 10 ; "
        // + " Y := 20 ; "
        // + " Z := 30 ; "
        // + " REPEAT "
        // + " BEGIN "
        // + " WRITELN ( X , Y , Z ) ; "
        // + " END "
        // + " UNTIL X > 10 ; "
        // + "TESTE . (* testando *) "
        // ;

        /*
         * String programa =
         * "Program testeproc1; "
         * + "    Var"
         * + "    X, y, z :integer;"
         * + "Procedure P;"
         * + "Var"
         * + "    A :integer;"
         * + "Begin"
         * + "    Readln(a);"
         * + "    If a=x then"
         * + "    z:=z+x"
         * + "    Else begin"
         * + "    Z:=z-x;"
         * + "    Call p;"
         * + "    End;"
         * + "End;"
         * + "Begin"
         * + "    Z:=0;"
         * + "    Readln(x,y);"
         * + "    If x>y then"
         * + "    Call p"
         * + "    Else"
         * + "    Z:=z+x+y;"
         * + "    Writeln(z);"
         * + "End.";
         */

        String programa =
                // "Program testeproc2; \n"
                // + " Const a=2; \n"
                // + " Var x,y:integer; \n"
                // + " Procedure p;\n"
                // + " Var z: integer;\n"
                // + " Procedure q;\n"
                // + " Var t: integer;\n"
                "    Begin (* inicio da q\n";
        // + " z:= z – 100 ; t:= z*a;\n"
        // + " if t > 100 then call q else writeln(t)\n"
        // + " end; (* fim de q*)\n"
        // + " begin (* inicio da P*)\n"
        // + " z:= x+y*a; if z> 100 then call q else writeln(z);\n"
        // + " end; (* fim da p*)\n"
        // + " begin (* programa principal*)\n"
        // + " readln(x,y);\n"
        // + " if x>1000 then x:= 1100\n"
        // + " else x::= y+100;\n"
        // + " while x>y do begin call p; readln(x,y) end;\n"
        // + " writeln(‘ tudo ok – boas férias ‘);\n"
        // + " end. \n";

        // Stack<Token> tokens = new AnalisadorLexico().gerarTokens(programa);

        // pilha = tokens;

        System.out.println("");
    }

    public Stack<Token> getTokens() {

        return pilha;
    }

}
