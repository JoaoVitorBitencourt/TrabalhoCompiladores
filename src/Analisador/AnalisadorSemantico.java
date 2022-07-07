package Analisador;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import Analisador.Enumeradores.Categoria;
import Gramatica.Token;

public class AnalisadorSemantico {
    ArrayList<TabelaSemantico> tabelaSemantico = new ArrayList<TabelaSemantico>();
    
    public void analisar(List<Token> tokens) throws Exception {
        boolean escopoConst = false;
        boolean escopoVar = false;
        boolean escopoRotulo = false;
        boolean escopoParametro = false;
        boolean escopoProcedure = false;

        int[] valoresParaAtribuirOuFinalizarConst = {25, 26, 47, 40};
        int[] valoresParaAtribuirOuFinalizarVar = {25, 26, 47, 40, 39, 46, 8};

        boolean atribuicaoDeValor = false;
        int nivel = 0;

        for(Token token : tokens) {
            if(escopoConst) {
                if(token.getCodigo() == 25) {
                    if(!atribuicaoDeValor) {
                        adicionaLinhaSemantico(tabelaSemantico, token, nivel, Categoria.CONSTANTE);
                    }
                }

                if(!Arrays.stream(valoresParaAtribuirOuFinalizarConst).anyMatch(i -> i == token.getCodigo())){
                    escopoConst = false;
                }
            } else if(escopoVar) {
                if(token.getCodigo() == 25) {
                    if(!atribuicaoDeValor) {
                        adicionaLinhaSemantico(tabelaSemantico, token, nivel, Categoria.VARIAVEL);
                    }
                }

                if(!Arrays.stream(valoresParaAtribuirOuFinalizarVar).anyMatch(i -> i == token.getCodigo())){
                    escopoVar = false;
                }
            } else if (escopoRotulo){

            } else if (escopoParametro){

            } else if (escopoProcedure){

            }

            if(token.getPalavra().toUpperCase().equals("CONST")) {
                escopoConst = true;
            }

            if(token.getPalavra().toUpperCase().equals("VAR")) {
                escopoVar = true;
            }
        }
    }

    private void adicionaLinhaSemantico(ArrayList<TabelaSemantico> tabelaSemantico, Token token, int nivel, Categoria categoria) throws Exception {
        for(TabelaSemantico linha : tabelaSemantico) {
            if(linha.getNome().equals(token.getPalavra()) && linha.getNivel() == nivel) {
                throw new Exception("Identificador já declarado" + "\nLinha: " + token.getLinha()); 
            }
        }

        TabelaSemantico linhaSemantico = new TabelaSemantico();
        linhaSemantico.setNome(token.getPalavra());
        linhaSemantico.setNivel(nivel);
        linhaSemantico.setCategoria(categoria);
        linhaSemantico.setTipo("INTEIRO");
        tabelaSemantico.add(linhaSemantico);
    }

    private TabelaSemantico getLinhaSemantico(String nome, int nivel) throws Exception {
        for(TabelaSemantico linha : tabelaSemantico) {
            if(nivel == 1) {
                if(linha.getNome().equals(nome) && linha.getNivel() == nivel || linha.getNivel() == 0) {
                    return linha;
                }
            }
            else {
                if(linha.getNome().equals(nome) && linha.getNivel() == nivel) {
                    return linha;
                } 
            }
        }

        throw new Exception("identificador não declarado"); 
    }

    private void deletaLinhaSemantico(String nome, int nivel) throws Exception{
        for(TabelaSemantico linha : tabelaSemantico) {
            if(linha.getNome().equals(nome) && linha.getNivel() == nivel) {
                if(!tabelaSemantico.remove(linha)) {
                    throw new Exception("Erro ao deletar identificador!"); 
                }
            }
        }

        throw new Exception("Erro ao deletar identificador!");
    }
}