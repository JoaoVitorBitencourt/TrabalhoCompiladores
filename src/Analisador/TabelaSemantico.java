package Analisador;

import Analisador.Enumeradores.Categoria;

public class TabelaSemantico {
    private String Nome;
    private Categoria Categoria;
    private String Tipo;
    private int nivel;

    public String getNome() {
        return Nome;
    }

    public Categoria getCategoria() {
        return Categoria;
    }

    public String getTipo() {
        return Tipo;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public void setCategoria(Categoria categoria) {
        this.Categoria = categoria;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
}
