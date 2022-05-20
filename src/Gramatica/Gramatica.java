package Gramatica;


import java.util.HashMap;
import java.util.Map;


public class Gramatica {
    public static final Map<String, Integer> DICIONARIO = new HashMap<String, Integer>();

    static {
    	DICIONARIO.put("PROGRAM", 1);
    	DICIONARIO.put("LABEL", 2);
    	DICIONARIO.put("CONST", 3);
    	DICIONARIO.put("VAR", 4);
    	DICIONARIO.put("PROCEDURE", 5);
    	DICIONARIO.put("BEGIN", 6);
    	DICIONARIO.put("END", 7);
    	DICIONARIO.put("INTEGER", 8);
    	DICIONARIO.put("ARRAY", 9);
    	DICIONARIO.put("OF", 10);
    	DICIONARIO.put("CALL", 11);
    	DICIONARIO.put("GOTO", 12);
    	DICIONARIO.put("IF", 13);
    	DICIONARIO.put("THEN", 14);
    	DICIONARIO.put("ELSE", 15);
    	DICIONARIO.put("WHILE", 16);
    	DICIONARIO.put("DO", 17);
    	DICIONARIO.put("REPEAT", 18);
    	DICIONARIO.put("UNTIL", 19);
    	DICIONARIO.put("READLN", 20);
    	DICIONARIO.put("WRITELN", 21);
    	DICIONARIO.put("OR", 22);
    	DICIONARIO.put("AND", 23);
    	DICIONARIO.put("NOT", 24);
    	DICIONARIO.put("IDENTIFICADOR", 25);
    	DICIONARIO.put("INTEIRO", 26);
    	DICIONARIO.put("FOR", 27);
    	DICIONARIO.put("TO", 28);
    	DICIONARIO.put("CASE", 29);
    	DICIONARIO.put("+", 30);
    	DICIONARIO.put("-", 31);
    	DICIONARIO.put("*", 32);
    	DICIONARIO.put("/", 33);
    	DICIONARIO.put("[", 34);
    	DICIONARIO.put("]", 35);
    	DICIONARIO.put("(", 36);
    	DICIONARIO.put(")", 37);
    	DICIONARIO.put(":=", 38);
    	DICIONARIO.put(":", 39);
    	DICIONARIO.put("=", 40);
    	DICIONARIO.put(">", 41);
    	DICIONARIO.put(">=", 42);
    	DICIONARIO.put("<", 43);
    	DICIONARIO.put("<=", 44);
    	DICIONARIO.put("<>", 45);
    	DICIONARIO.put(",", 46);
    	DICIONARIO.put(";", 47);
    	DICIONARIO.put("LITERAL", 48);
    	DICIONARIO.put(".", 49);
    	DICIONARIO.put(".", 50);
    	DICIONARIO.put("..", 51);
    }

}
