package Analisador;

public class Enumeradores {
    public enum Categoria {

        VARIAVEL("variável"),
        CONSTANTE("constante"),
        ROTULO("rótulo"),
        PARAMETRO("parâmetro"),
        PROCEDURE("procedure");
    
        private String descricao;
    
        Categoria(String descricao) {
            this.descricao = descricao;
        }
    
        public String getDescricao() {
            return descricao;
        }
    }
}
