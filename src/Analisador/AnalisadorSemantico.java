package Analisador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Analisador.Enumeradores.Categoria;
import Gramatica.Token;

public class AnalisadorSemantico {
    ArrayList<TabelaSemantico> tabelaSemantico = new ArrayList<TabelaSemantico>();
    
    public void analisar(List<Token> tokens) throws Exception {
        boolean escopoConst = false;
        boolean escopoVar = false;
        boolean escopoParametro = false;
        boolean escopoProcedure = false;
        boolean escopoDeclaracao = true;
        boolean declarandoNomeProcedure = true;
        boolean escopoCallProcedure = false;
        boolean escopoEnviaParam = false;
        boolean escopoLabel = false;
        boolean escopoFor = false;
        boolean escopoCase = false;

        int[] valoresParaAtribuirOuFinalizarConst = {25, 26, 47, 40};
        int[] valoresParaAtribuirOuFinalizarVar = {25, 26, 47, 40, 39, 46, 8, 9, 10, 51};
        int[] valoresParaAtribuirOuFinalizarLabel = {25, 48, 47, 40, 46};
        int[] operadores = {30, 31, 32, 33};

        ArrayList<Token> variaveis = new ArrayList<Token>();

        boolean atribuicaoDeValor = false;
        boolean atribuicaoDeTipo = false;

        String tipoVariavel = "";

        int nivel = 0;

        TabelaSemantico variavel = new TabelaSemantico();

        for(Token token : tokens) {
            if(escopoConst) {
                if(token.getCodigo() == 25) {
                    if(!atribuicaoDeValor) {
                        adicionaLinhaSemantico(tabelaSemantico, token, nivel, Categoria.CONSTANTE, "INTEIRO");
                    }
                } else if (token.getCodigo() == 40) {
                    atribuicaoDeValor = true;
                } else if (token.getCodigo() == 47) {
                    atribuicaoDeValor = false;
                } else if(atribuicaoDeValor) {
                    try {
                        Integer.parseInt(token.getPalavra());
                    } catch (NumberFormatException e) {
                        throw new NumberFormatException("Valor atribuido diferente da declaração da variavel!\nNome: " + token.getPalavra() + "\nLinha: " + token.getLinha());
                    }
                }

                if(!Arrays.stream(valoresParaAtribuirOuFinalizarConst).anyMatch(i -> i == token.getCodigo())){
                    escopoConst = false;
                }
            } else if(escopoVar) {
                if(token.getCodigo() == 25) {
                    if(!atribuicaoDeValor && escopoDeclaracao) {
                        variaveis.add(token);
                    }
                } else if (token.getCodigo() == 8) {
                    for(Token variavelParaDeclarar : variaveis) {
                        adicionaLinhaSemantico(tabelaSemantico, variavelParaDeclarar, nivel, Categoria.VARIAVEL, "INTEIRO");
                    } 
                    atribuicaoDeTipo = false;
                    variaveis = new ArrayList<Token>();
                } else if (token.getCodigo() == 9) {
                    for(Token variavelParaDeclarar : variaveis) {
                        adicionaLinhaSemantico(tabelaSemantico, variavelParaDeclarar, nivel, Categoria.VARIAVEL, "ARRAY");
                    } 
                    atribuicaoDeTipo = false;
                    variaveis = new ArrayList<Token>();
                } else if (token.getCodigo() == 39) {
                    atribuicaoDeTipo = true;
                } else if (token.getCodigo() == 47) {
                    atribuicaoDeTipo = false;
                } else if(atribuicaoDeTipo) {
                    if(token.getCodigo() != 8 || token.getCodigo() != 9) {
                        throw new Exception("Tipo de variavel não existe!\nNome: " + token.getPalavra() + "\nLinha: " + token.getLinha());
                    }
                }

                if(!Arrays.stream(valoresParaAtribuirOuFinalizarVar).anyMatch(i -> i == token.getCodigo())){
                    escopoVar = false;
                }
            } else if (escopoLabel){
                if(token.getCodigo() == 25) {
                    if(!atribuicaoDeValor) {
                        adicionaLinhaSemantico(tabelaSemantico, token, nivel, Categoria.VARIAVEL, "LITERAL");
                    }
                } else if (!Arrays.stream(valoresParaAtribuirOuFinalizarLabel).anyMatch(i -> i == token.getCodigo())) {
                    escopoLabel = false;
                }
            } else if (escopoParametro){

            } else if (escopoProcedure && escopoDeclaracao){
                if(token.getCodigo() == 25) {
                    if(!atribuicaoDeValor) {
                        if(declarandoNomeProcedure) {
                            adicionaLinhaSemantico(tabelaSemantico, token, 0, Categoria.PROCEDURE, "INTEIRO");
                            declarandoNomeProcedure = false;
                        } else {
                            adicionaLinhaSemantico(tabelaSemantico, token, nivel, Categoria.PARAMETRO, "INTEIRO");
                        }
                    }
                }
            } else if (escopoCallProcedure) {
                if(token.getCodigo() == 47){
                    escopoCallProcedure = false;
                    escopoEnviaParam = false;
                } else if(token.getCodigo() == 25) {
                    if(escopoEnviaParam) {
                        variavel = getLinhaSemantico(token, nivel);
                        if(!variavel.getTipo().equals("INTEIRO") && !variavel.getTipo().equals("ARRAY")) {
                            throw new Exception("Parametro inválido! \nNome: " + token.getPalavra() + " \nLinha: " + token.getLinha());
                        }
                    } else {
                        variavel = getLinhaSemantico(token, nivel);
                    }
                } else if(token.getCodigo() == 36) {
                    escopoEnviaParam = true;
                } else if (escopoEnviaParam) {
                    if(!Arrays.stream(operadores).anyMatch(i -> i == token.getCodigo()) && token.getCodigo() != 37) {
                        try {
                            Integer.parseInt(token.getPalavra());
                        } catch (NumberFormatException e) {
                            throw new NumberFormatException("Parametro inválido! \nNome: " + token.getPalavra() + "\nLinha: " + token.getLinha());
                        }
                    }
                }
            } else if(!escopoDeclaracao) {
                if(token.getCodigo() == 38){
                    atribuicaoDeValor = true;
                } else if (token.getCodigo() == 47) {
                    atribuicaoDeValor = false;
                } else if (!atribuicaoDeValor && token.getCodigo() == 25 && !escopoCase) {
                    variavel = getLinhaSemantico(token, nivel);
                    if(variavel.getCategoria() == Categoria.CONSTANTE){
                        throw new Exception("Não pode atribuir valor a uma constante! \nNome: " + token.getPalavra() + "\nLinha: " + token.getLinha());
                    } else {
                        tipoVariavel = variavel.getTipo();
                    }
                } else if(atribuicaoDeValor && token.getCodigo() == 25) {
                    variavel = getLinhaSemantico(token, nivel);
                    if(!variavel.getTipo().equals(tipoVariavel) ) {
                        throw new Exception("Variaveis com tipos diferentes \nNome: " + token.getPalavra() + "\nLinha: " + token.getLinha());
                    }
                } else if(tipoVariavel.equals("ARRAY") && token.getCodigo() == 34) {
                    tipoVariavel = "INTEIRO";
                }
            }

            switch(token.getCodigo()) {
                case 2:
                    escopoLabel = true;
                break;
                case 3:
                    escopoConst = true;
                break;
                case 4:
                    escopoVar = true;
                break;
                case 5:
                    escopoProcedure = true;
                    escopoDeclaracao = true;
                    declarandoNomeProcedure = true;
                    nivel = 1;
                break;
                case 6:
                    escopoDeclaracao = false;
                    if(escopoFor) {
                        atribuicaoDeValor = false;
                    }
                break;
                case 7:
                    if(escopoProcedure){
                        escopoProcedure = false;
                        nivel = 0;
                        deletaLinhasSemantico(1);
                    }
                break;
                case 11:
                    escopoCallProcedure = true;
                break;
                case 27:
                    escopoFor = true;
                break;
                case 29: 
                    escopoCase = true;
                break;
            }
        }
    }

    private void adicionaLinhaSemantico(ArrayList<TabelaSemantico> tabelaSemantico, Token token, int nivel, Categoria categoria, String tipo) throws Exception {
        for(TabelaSemantico linha : tabelaSemantico) {
            if(linha.getNome().toUpperCase().equals(token.getPalavra().toUpperCase()) && linha.getNivel() == nivel) {
                throw new Exception("Identificador já declarado\nNome: " + token.getPalavra() + "\nLinha: " + token.getLinha()); 
            }
        }

        TabelaSemantico linhaSemantico = new TabelaSemantico();
        linhaSemantico.setNome(token.getPalavra());
        linhaSemantico.setNivel(nivel);
        linhaSemantico.setCategoria(categoria);
        linhaSemantico.setTipo(tipo);
        tabelaSemantico.add(linhaSemantico);
    }

    private TabelaSemantico getLinhaSemantico(Token token, int nivel) throws Exception {
        for(TabelaSemantico linha : tabelaSemantico) {
            if(nivel == 1) {
                if(linha.getNome().toUpperCase().equals(token.getPalavra().toUpperCase()) && (linha.getNivel() == nivel || linha.getNivel() == 0)) {
                    return linha;
                }
            }
            else {
                if(linha.getNome().toUpperCase().equals(token.getPalavra().toUpperCase()) && linha.getNivel() == nivel) {
                    return linha;
                } 
            }
        }

        throw new Exception("identificador não declarado!\nNome: " + token.getPalavra() + "\nLinha: " + token.getLinha()); 
    }

    private void deletaLinhasSemantico(int nivel) throws Exception{
        ArrayList<TabelaSemantico> linhasParaDeletar = new ArrayList<TabelaSemantico>();

        for(TabelaSemantico linha : tabelaSemantico) {
            if(linha.getNivel() == nivel) {
                linhasParaDeletar.add(linha);
            }
        }

        for(TabelaSemantico linha : linhasParaDeletar) {
            if(!tabelaSemantico.remove(linha)) {
                throw new Exception("Erro ao deletar identificador!\nNome: " + linha.getNome()); 
            }
        }
    }
}