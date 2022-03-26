package Analisador;

import java.util.Stack;

import javax.print.DocFlavor.STRING;

import Gramatica.Gramatica;
import Gramatica.Token;

public class AnalisadorLexico {
    
    public Stack<Token> gerarTokens(String programa){
        // Stack<Token> pilhaInversa = new Stack<>();
        // Stack<Token> pilhaTokens = getListaTokens(programa);
        Stack<Token> teste = geraListaToken(programa);
        return teste;

        // while(!pilhaTokens.empty()){
        //     pilhaInversa.push(pilhaTokens.pop());
        // }
        
        // return pilhaInversa;
    }

    private Stack<Token> getListaTokens(String programa) {
        String token = new String();
        Stack<Token> pilhaTokens = new Stack<>();

        for(int i = 0; i <= programa.length() -1; i++) {
            String charAtual = Character.toString(programa.charAt(i));

            if(i+1 >= programa.length()) { // trata fim do arquivo
                if(!token.trim().equals("")) { // verifica se há algum token para armazenar antes de finalizar
                    pilhaTokens.push(new Token(token, getCodigoToken(token)));
                }
                if(!charAtual.trim().equals("")){//grava o caracter final do programa se não for nullo ou espaço
                    pilhaTokens.push(new Token(charAtual, getCodigoToken(charAtual)));
                }
                break;
            }

            String validador = charAtual + Character.toString(programa.charAt(i+1));

            switch(validador.toUpperCase()){
                case "(*": 
                    for(int j = i; j <= programa.length() -1 ; j++) {
                        String charComentarioAnt = Character.toString(programa.charAt(j)) + programa.charAt(j+1);
                        if(charComentarioAnt.equals("*)")) {
                            i = j + 1;
                            break;
                        }
                    }
                break;
                case ":=":
                case "<=":
                case ">=":
                case "<>":
                case "..":
                    if(!token.trim().equals("")) {
                        pilhaTokens.push(new Token(token, getCodigoToken(token)));
                        token = "";
                    }   
                    pilhaTokens.push(new Token(validador, getCodigoToken(validador)));
                    i++;
                break;
                default:
                    switch(charAtual) {
                        case "'": 
                        case "`":
                        case "´":
                        case "‘":
                            String stringCod = new String();
                            stringCod = charAtual; // grava char atual e começa o for a partir do proximo

                            for(int j = i + 1; j <= programa.length() -1 ; j++) {
                                String charStringAtual = Character.toString(programa.charAt(j));

                                if(!(charStringAtual.equals("'") || charStringAtual.equals("`") || charStringAtual.equals("´") || charStringAtual.equals("‘"))) {
                                    stringCod += charStringAtual;
                                } else {
                                    stringCod += charStringAtual;
                                    pilhaTokens.push(new Token(stringCod, getCodigoToken(stringCod)));
                                    stringCod = "";
                                    i = j;
                                    break;
                                }
                            }
                        break;
                        case ";": 
                        case " ":
                        case ",":
                        case ".":
                        case ":":
                        case "+":
                        case "-":
                        case "*":
                        case "/":
                        case "[":
                        case "]":
                        case "(":
                        case ")":
                        case "=":
                            if(!token.trim().equals("")) { 
                                pilhaTokens.push(new Token(token, getCodigoToken(token))); // grava token
                                token = "";
                            }

                            if(!charAtual.trim().equals("")) { // ver se o limitador não é um espaço
                                pilhaTokens.push(new Token(charAtual, getCodigoToken(charAtual))); // grava limitador
                            }
                        break;
                        default: 
                            token += charAtual; // vai gravando o char ate encontrar o limitador
                            token.trim();
                    }
            }
        }

        return pilhaTokens;
    }
    
    private Integer getCodigoToken(String token){
        
        Integer codigoToken = Gramatica.DICIONARIO.get(token.toUpperCase());
        if(codigoToken == null) {
            //ou � um INTEGER ou pe um IDENTIFICADOR
            return getIdentificadorOuInteiro(token);
        }
        return codigoToken;
    }
    
    private Integer getIdentificadorOuInteiro(String token) {
        //verifica se � um identificador ou inteiro
        char[] cList = token.toCharArray();
        boolean identificador = true;
        for(char c : cList) {
            if(Character.getType(c) != Character.UPPERCASE_LETTER) {
                identificador = true;
            }
        }
        
        

        if(identificador) {
            return Gramatica.DICIONARIO.get("IDENTIFICADOR");
        } 
        return Gramatica.DICIONARIO.get("INTEIRO");
    }

    private String[] quebrarEmLinhas(String programa) {
        String[] teste = programa.split("\n");
        return teste;
    }

    private String geraToken(String token){
        return "";
    }

    private String separaToken(String[] programa){

        return new String();
    }

    private String removecomentarios(String programa) {
        String token = new String();
        Stack<Token> pilhaTokens = new Stack<>();
        String programaSemComentario = new String();

        for(int i = 0; i <= programa.length() -1; i++) {
            String charAtual = Character.toString(programa.charAt(i));

            if(i+1 >= programa.length()) { // trata fim do arquivo
                if(!charAtual.trim().equals("") || charAtual.equals("\n")){//grava o caracter final do programa se não for nullo ou espaço
                    programaSemComentario += charAtual;
                }
                break;
            }

            String validador = charAtual + Character.toString(programa.charAt(i+1));

            if(validador.toUpperCase().equals("(*")){
                for(int j = i; j <= programa.length() -1 ; j++) {
                    if(j+1 >= programa.length()) { // trata fim do arquivo
                        if(!Character.toString(programa.charAt(j)).trim().equals("") || Character.toString(programa.charAt(j)).equals("\n")){//grava o caracter final do programa se não for nullo ou espaço
                            programaSemComentario += Character.toString(programa.charAt(j));
                            i = j + 1;
                        }
                        break;
                    }

                    String charComentarioAnt = Character.toString(programa.charAt(j)) + programa.charAt(j+1);

                    if(charComentarioAnt.equals("*)")) {
                        i = j + 1;
                        break;
                    }
                }
            } else {
                programaSemComentario += charAtual;
            }
        }

        return programaSemComentario;
    }

    private String leString (String programa) {
        return "";
    }

    public Stack<Token> geraListaToken(String programa) {
        String stringSemComentario = removecomentarios(programa);
        String[] linhasQuebradas = quebrarEmLinhas(programa);

        return new Stack<Token>();
    }
}
