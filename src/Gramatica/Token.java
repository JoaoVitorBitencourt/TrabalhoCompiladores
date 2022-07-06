package Gramatica;

public class Token {

    private String palavra;
    private Integer codigo;
    private Integer linha;

    public Token(String palavra, Integer codigo, Integer linha) {
        this.palavra = palavra;
        this.codigo = codigo;
        this.linha = linha;
    }
    
    public String getPalavra() {
        return palavra;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public Integer getLinha() {
        return linha;
    }

    @Override
    public String toString() {
        return "Token{" + "palavra=" + palavra + ", codigo=" + codigo + '}';
    }
}
