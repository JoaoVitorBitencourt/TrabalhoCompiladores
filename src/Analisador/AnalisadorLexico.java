package Analisador;

import java.util.Stack;

import Gramatica.Gramatica;
import Gramatica.Token;

public class AnalisadorLexico {
    
    public Stack<Token> gerarTokens(String programa){
        String[] tokens = programa.split(" ");
        System.out.println(tokens);
        Stack<Token> pilhaTokens = new Stack<>();
        Stack<Token> teste = getListaTokens(programa);
        
        for(int i = tokens.length -1; i >= 0; i--) {
            String token = tokens[i];
            if(token.trim().length() == 0) {
                continue;
            }
            Integer codigo = getCodigoToken(token);
            pilhaTokens.push(new Token(token, codigo));
        }
        
        
        return pilhaTokens;
    }

    private Stack<Token> getListaTokens(String programa) {
        String token = new String();
        Stack<Token> pilhaTokens = new Stack<>();

        for(int i = 0; i <= programa.length() -1; i++) {
            String charAtual = Character.toString(programa.charAt(i));

            if(i+1 >= programa.length()) { // trata fim do arquivo
                if(!charAtual.trim().equals("")){
                    pilhaTokens.push(new Token(charAtual, getCodigoToken(charAtual)));
                }
                break;
            }

            String validador = charAtual + Character.toString(programa.charAt(i+1));

            switch(validador){
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
                    pilhaTokens.push(new Token(validador, getCodigoToken(validador)));
                break;
                default:
                    switch(charAtual) {
                        case ";": 
                        case " ":
                        case ",":
                        case ".":
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
        
        Integer codigoToken = Gramatica.DICIONARIO.get(token);
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
}
