package Analisador;

public class Linha {
    private String texto;
    private Integer linha;

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
}
