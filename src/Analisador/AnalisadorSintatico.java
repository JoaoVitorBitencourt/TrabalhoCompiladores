package Analisador;

import java.util.Stack;

import Gramatica.Gramatica;
import Gramatica.Token;

public class AnalisadorSintatico {

    // -- Para próximas correções é necessário tratar determinadas situações em
    // erros, por exemplo a declaração de variáveis.

    public void analisar(Stack<Token> tokens) {

        Stack<Token> pilhaA = tokens;
        Stack<Integer> pilhaX = new Stack();
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

            if (X < 52 && A != null) { // - X sendo terminal

                if (codigo.equals(X)) {
                    System.out.println("Removido da pilha A: " + pilhaA.peek());
                    System.out.println("Removido da pilha X: " + pilhaX.peek());
                    A = pilhaA.pop();
                    X = pilhaX.pop();
                }
            } else { // - X não terminal

                String table = Gramatica.TABELAPARSING.get(X.toString() + "," + codigo.toString());
                pilhaX.pop();

                System.out.println(table);

                if (table == null) {

                    System.out.println("Erro no no código: " + A.getPalavra() + "");
                    break;

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
                System.out.println("Erro");
                // - Imprimir erro no console;
            }
        } while (!pilhaX.isEmpty() && !pilhaA.isEmpty());
        VerificaPilhas(pilhaA, pilhaX);

    }

    private void VerificaPilhas(Stack<Token> pilhaA, Stack<Integer> pilhaX) {
        if (pilhaX.isEmpty() && pilhaA.isEmpty()) {
            System.out.println("Programa compilado com sucesso!");
        } else {
            System.out.println("Erro");
        }
    }

}
