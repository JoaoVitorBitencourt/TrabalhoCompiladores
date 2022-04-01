package Analisador;

import java.util.ArrayList;
import java.util.Stack;

import Gramatica.Token;

public class Linha {
    private String texto;
    private Integer linha;
    private Stack<Token> Tokens;

    public Linha(Integer linha, String texto){
        setLinha(linha);
        setTexto(texto);
    }

    public void setLinha(Integer linha) {
        this.linha = linha;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Integer getLinha() {
        return linha;
    }

    public String getTexto() {
        return texto;
    }

    public void setTokens(Stack<Token> tokens) {
        Tokens = tokens;
    }

    public Stack<Token> getTokens() {
        return Tokens;
    }
}
