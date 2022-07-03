package Analisador;

import java.util.Stack;

import Gramatica.Gramatica;
import Gramatica.Token;

public class AnalisadorSintatico {

    public void analisar(Stack<Token> tokens) throws Exception {

        Stack<Token> pilhaA = tokens;
        Stack<Integer> pilhaX = new Stack<Integer>();
        pilhaA = tokens;
        pilhaX.add(52);

        do {
            System.out.println(pilhaX);
            Integer X = pilhaX.peek();
            Token A = pilhaA.peek();
            Integer codigo = A.getCodigo();

            if (codigo.equals(null)) {

                try {
                    codigo = 26;
                } catch (Exception e) {
                    codigo = 25;
                }
            }

            /*
             * if (X < 52 && A != null) {
             * 
             * 
             * throw new Exception("Erro no código: " + A.getPalavra() + "");
             * }
             */

            if (X < 52 && A != null) { // - X sendo terminal

                if (codigo.equals(X)) {
                    System.out.println("Removido da pilha A: " + pilhaA.peek());
                    System.out.println("Removido da pilha X: " + pilhaX.peek());
                    A = pilhaA.pop();
                    X = pilhaX.pop();
                } else {
                    System.out.println(A.getPalavra());
                    throw new Exception("Erro no código: " + A.getPalavra() + "");
                }
            } else { // - X não terminal

                String table = Gramatica.TABELAPARSING.get(X.toString() + "," + codigo.toString());
                pilhaX.pop();

                System.out.println(table);

                if (table == null) {
                    throw new Exception("Erro no código: " + A.getPalavra() + "");
                }

                if (!table.equals(null) && !table.isEmpty()) {

                    if (table != "NULL") {
                        Integer[] dados = Gramatica.dadosCruzados(table); // - Busca os não terminais
                        for (int i = dados.length - 1; i >= 0; i--) {
                            if (dados[i] == (null)) {
                                pilhaX.pop();
                            } else {
                                pilhaX.push(dados[i]);
                            }
                        }
                    }

                } else {
                    pilhaX.pop();
                }
            }
            if ((pilhaX.isEmpty() && !pilhaA.isEmpty()) || (!pilhaX.isEmpty() && pilhaA.isEmpty())) {
                throw new Exception("Erro na compilação do programa!");
                // - Imprimir erro no console;
            }
        } while (!pilhaX.isEmpty() && !pilhaA.isEmpty());
        VerificaPilhas(pilhaA, pilhaX);

    }

    private void VerificaPilhas(Stack<Token> pilhaA, Stack<Integer> pilhaX) throws Exception {
        if (!(pilhaX.isEmpty() && pilhaA.isEmpty())) {
            throw new Exception("Erro na compilação do programa!");
        }
    }

}
