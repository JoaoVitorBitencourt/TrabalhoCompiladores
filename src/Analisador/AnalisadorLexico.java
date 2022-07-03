package Analisador;

import java.util.ArrayList;
import java.util.Stack;

import Gramatica.Gramatica;
import Gramatica.Token;

public class AnalisadorLexico {

    private boolean continuaComentarioProxLinha;

    public Stack<Token> gerarTokens(ArrayList<Linha> programa) throws Exception {
        Stack<Token> teste = geraListaToken(programa);
        return teste;
    }

    private Stack<Token> getListaTokens(String programa) throws Exception {
        String token = new String();
        Stack<Token> pilhaTokens = new Stack<>();

        for (int i = 0; i <= programa.length() - 1; i++) {
            String charAtual = Character.toString(programa.charAt(i));

            if (i + 1 >= programa.length()) { // trata fim do arquivo
                if (!token.trim().equals("")) { // verifica se há algum token para armazenar antes de finalizar
                    if (isLimitador(charAtual)) {
                        pilhaTokens.push(new Token(token, getCodigoToken(token)));
                    } else {
                        token += charAtual;
                        charAtual = "";
                        pilhaTokens.push(new Token(token, getCodigoToken(token)));
                    }
                }
                if (!charAtual.trim().equals("")) {// grava o caracter final do programa se não for nullo ou espaço
                    pilhaTokens.push(new Token(charAtual, getCodigoToken(charAtual)));
                }
                break;
            }

            String validador = charAtual + Character.toString(programa.charAt(i + 1));

            switch (validador.toUpperCase()) {
                case ":=":
                case "<=":
                case ">=":
                case "<>":
                case "..":
                    if (!token.trim().equals("")) {
                        pilhaTokens.push(new Token(token, getCodigoToken(token)));
                        token = "";
                    }
                    pilhaTokens.push(new Token(validador, getCodigoToken(validador)));
                    i++;
                    break;
                default:
                    switch (charAtual) {
                        case "'":
                            String stringCod = new String();
                            stringCod = charAtual; // grava char atual e começa o for a partir do proximo

                            for (int j = i + 1; j <= programa.length() - 1; j++) {
                                String charStringAtual = Character.toString(programa.charAt(j));

                                if (!(charStringAtual.equals("'"))) {
                                    stringCod += charStringAtual;
                                } else {
                                    stringCod += charStringAtual;
                                    if (stringCod.length() > 255) {
                                        throw new Exception("O tamanho máximo para um literal é 255");
                                    } else {
                                        pilhaTokens.push(new Token(stringCod, getCodigoToken("LITERAL")));
                                        stringCod = "";
                                        i = j;
                                    }
                                    break;
                                }
                            }

                            if (!stringCod.equals("")) {
                                throw new Exception("O literal precisa finalizar na mesma linha");
                            }
                            break;
                        case "-":
                            if (!token.trim().equals("")) {
                                pilhaTokens.push(new Token(token, getCodigoToken(token))); // grava token
                                token = "";
                            }

                            token = charAtual;
                            break;
                        case " ":
                        case ";":
                        case ",":
                        case ".":
                        case ":":
                        case "+":
                        case "*":
                        case "/":
                        case "[":
                        case "]":
                        case "(":
                        case ")":
                        case "=":
                        case ">":
                        case "<":
                            if (!token.trim().equals("")) {
                                pilhaTokens.push(new Token(token, getCodigoToken(token))); // grava token
                                token = "";
                            }

                            if (!charAtual.trim().equals("")) { // ver se o limitador não é um espaço
                                pilhaTokens.push(new Token(charAtual, getCodigoToken(charAtual))); // grava
                                                                                                   // limitador
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

    private boolean isLimitador(String charAtual) {
        switch (charAtual) {
            case "'":
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
                return true;
            default:
                return false;
        }
    }

    private Integer getCodigoToken(String token) throws Exception {
        Integer codigoToken = Gramatica.DICIONARIO.get(token.toUpperCase());
        if (codigoToken == null) {
            // INTEGER ou é um IDENTIFICADOR
            return getIdentificadorOuInteiro(token);
        }
        return codigoToken;
    }

    private Integer getIdentificadorOuInteiro(String token) throws Exception {
        // verifica se é um identificador ou inteiro

        char[] cList = token.toCharArray();
        boolean identificador = false;
        for (char c : cList) {
            char uppercase = Character.toUpperCase(c);
            if (Character.getType(uppercase) == Character.UPPERCASE_LETTER) {
                identificador = true;
            }
        }

        if (identificador) {
            if (token.length() > 30) {
                throw new Exception("O tamanho maximo para um identificador é 30");
            }
            return Gramatica.DICIONARIO.get("IDENTIFICADOR");
        } else {
            if (token.matches("-?[0-9]*")) {
                if (Integer.parseInt(token) > 32767 || Integer.parseInt(token) < -32767) {
                    throw new Exception("O valor do inteiro precisa estar entre -32767 e 32767");
                }
                return Gramatica.DICIONARIO.get("INTEIRO");
            } else {
                return Gramatica.DICIONARIO.get("IDENTIFICADOR");
            }
        }
    }

    private ArrayList<Linha> geraToken(ArrayList<Linha> Linhas) throws Exception {
        for (Linha linha : Linhas) {
            linha.setTokens(getListaTokens(linha.getTexto()));
        }

        return Linhas;
    }

    private ArrayList<Linha> RemoveComentarios(ArrayList<Linha> programa) {
        String programaSemComentario = new String();

        for (Linha linha : programa) {
            String LinhaAtual = linha.getTexto();

            for (int i = 0; i <= LinhaAtual.length() - 1; i++) {
                String charAtual = Character.toString(LinhaAtual.charAt(i));

                if (i + 1 >= LinhaAtual.length()) { // trata fim do arquivo
                    if (!charAtual.trim().equals("") || charAtual.equals("\n")) {// grava o caracter final do programa
                                                                                 // se não for nullo ou espaço
                        programaSemComentario += charAtual;
                    }
                    break;
                }

                String validador = charAtual + Character.toString(LinhaAtual.charAt(i + 1));

                if (validador.toUpperCase().equals("(*") || continuaComentarioProxLinha) {
                    continuaComentarioProxLinha = true;
                    for (int j = i; j <= LinhaAtual.length() - 1; j++) {
                        String charAtualComentario = Character.toString(LinhaAtual.charAt(j));

                        if (charAtualComentario.equals("\n")) {
                            programaSemComentario += charAtualComentario;
                        }

                        if (j + 1 >= LinhaAtual.length()) { // trata fim do arquivo
                            i = j;
                            break;
                        }

                        String charComentarioAnt = charAtualComentario + LinhaAtual.charAt(j + 1);

                        if (charComentarioAnt.equals("*)")) {
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

    public Stack<Token> geraListaToken(ArrayList<Linha> programa) throws Exception {
        ArrayList<Linha> stringSemComentario = RemoveComentarios(programa);
        stringSemComentario = geraToken(stringSemComentario);
        Stack<Token> tokenParaMostrarEmTela = new Stack<Token>();

        for (Linha l : stringSemComentario) {
            for (Token t : l.getTokens()) {
                tokenParaMostrarEmTela.add(t);
            }
        }

        return tokenParaMostrarEmTela;
    }
}
