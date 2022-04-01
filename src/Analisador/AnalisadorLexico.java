package Analisador;

import java.util.ArrayList;
import java.util.Stack;

import javax.print.DocFlavor.STRING;

import Gramatica.Gramatica;
import Gramatica.Token;

public class AnalisadorLexico {

    private boolean continuaComentarioProxLinha;
    
    public Stack<Token> gerarTokens(ArrayList<Linha> programa){
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
                    if(isLimitador(charAtual)) {
                        pilhaTokens.push(new Token(token, getCodigoToken(token)));
                    } else {
                        token += charAtual;
                        charAtual = "";
                        pilhaTokens.push(new Token(token, getCodigoToken(token)));
                    }
                }
                if(!charAtual.trim().equals("")){//grava o caracter final do programa se não for nullo ou espaço
                    pilhaTokens.push(new Token(charAtual, getCodigoToken(charAtual)));
                }
                break;
            }

            String validador = charAtual + Character.toString(programa.charAt(i+1));

            switch(validador.toUpperCase()){
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

    private boolean isLimitador(String charAtual){
        switch(charAtual) {
            case "'": 
            case "`":
            case "´":
            case "‘":
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
            case "=": return true;
            default: return false;
        }
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

    private ArrayList<Linha> geraToken(ArrayList<Linha> Linhas){
        for(Linha linha : Linhas) {
            linha.setTokens(getListaTokens(linha.getTexto()));
        }

        return Linhas;
    }

    private ArrayList<Linha> RemoveComentarios(ArrayList<Linha> programa) {
        String programaSemComentario = new String();

        for(Linha linha : programa) {
            String LinhaAtual = linha.getTexto();
           
            for(int i = 0; i <= LinhaAtual.length() -1; i++){
                String charAtual = Character.toString(LinhaAtual.charAt(i));

                if(i+1 >= LinhaAtual.length()) { // trata fim do arquivo
                    if(!charAtual.trim().equals("") || charAtual.equals("\n")){//grava o caracter final do programa se não for nullo ou espaço
                        programaSemComentario += charAtual;
                    }
                    break;
                }
    
                String validador = charAtual + Character.toString(LinhaAtual.charAt(i+1));
    
                if(validador.toUpperCase().equals("(*") || continuaComentarioProxLinha){
                    continuaComentarioProxLinha = true;
                    for(int j = i; j <= LinhaAtual.length() -1 ; j++) {
                        String charAtualComentario = Character.toString(LinhaAtual.charAt(j));
    
                        if(charAtualComentario.equals("\n")) {
                            programaSemComentario += charAtualComentario;
                        }
    
                        if(j+1 >= LinhaAtual.length()) { // trata fim do arquivo
                            i = j;
                            break;
                        }
    
                        String charComentarioAnt = charAtualComentario + LinhaAtual.charAt(j+1);
    
                        if(charComentarioAnt.equals("*)")) {
                            continuaComentarioProxLinha = false;
                            i = j + 1;
                            break;
                        }
                    }
                } else {
                    programaSemComentario += charAtual;
                }
            }

            linha.setTexto(programaSemComentario);
            programaSemComentario = "";
        }

        System.out.println(programaSemComentario);

        return programa;
    }

    private String leString (String programa) {
        return "";
    }

    public Stack<Token> geraListaToken(ArrayList<Linha> programa) {
        ArrayList<Linha> stringSemComentario = RemoveComentarios(programa);
        stringSemComentario = geraToken(stringSemComentario);
        Stack<Token> tokenParaMostrarEmTela = new Stack<Token>();

        for(Linha l : stringSemComentario) {
            for(Token t : l.getTokens()){
                tokenParaMostrarEmTela.add(t);
            }
        }

        return tokenParaMostrarEmTela;
    }
}
