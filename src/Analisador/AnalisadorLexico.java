package Analisador;

import java.util.Stack;

public class AnalisadorLexico {
    
    public Stack<Token> gerarTokens(String programa){
        String[] tokens = programa.split(" ");
        System.out.println(tokens);
        Stack<Token> pilhaTokens = new Stack<>();
        
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
    
    private Integer getCodigoToken(String token){
        
        Integer codigoToken = Gramatica.DICIONARIO.get(token);
        if(codigoToken == null) {
            //ou é um INTEGER ou pe um IDENTIFICADOR
            return getIdentificadorOuInteiro(token);
        }
        return codigoToken;
    }
    
    private Integer getIdentificadorOuInteiro(String token) {
        //verifica se é um identificador ou inteiro
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
