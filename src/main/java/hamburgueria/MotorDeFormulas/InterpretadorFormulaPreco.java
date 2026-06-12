package hamburgueria.MotorDeFormulas;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class InterpretadorFormulaPreco implements ExpressaoPreco {

    private final ExpressaoPreco expressaoFinal;

    public InterpretadorFormulaPreco(String formula) {
        if (formula == null || formula.isBlank())
            throw new IllegalArgumentException("Fórmula de preço não pode ser vazia.");

        Stack<ExpressaoPreco> pilha = new Stack<>();
        List<String> tokens = Arrays.asList(formula.trim().split("\\s+"));
        Iterator<String> it  = tokens.iterator();

        while (it.hasNext()) {
            String token = it.next();

            if (token.equals("preco")) {
                pilha.push(new VariavelPreco());

            } else if (isNumero(token)) {
                pilha.push(new ValorConstante(Double.parseDouble(token)));

            } else if (token.equals("+") || token.equals("-")
                    || token.equals("*") || token.equals("/")) {

                if (pilha.isEmpty())
                    throw new IllegalArgumentException(
                            "Fórmula inválida: operador '" + token + "' sem operando à esquerda.");
                if (!it.hasNext())
                    throw new IllegalArgumentException(
                            "Fórmula inválida: operador '" + token + "' sem operando à direita.");

                ExpressaoPreco esquerda = pilha.pop();
                ExpressaoPreco direita  = parseTermo(it.next());

                pilha.push(criarOperacao(token, esquerda, direita));

            } else {
                throw new IllegalArgumentException("Token inválido na fórmula: '" + token + "'");
            }
        }

        if (pilha.isEmpty())
            throw new IllegalArgumentException("Fórmula vazia ou inválida.");

        expressaoFinal = pilha.pop();
    }

    @Override
    public double interpretar(double preco) {
        return expressaoFinal.interpretar(preco);
    }

    private static boolean isNumero(String token) {
        return token.matches("\\d+(\\.\\d*)?");
    }

    private static ExpressaoPreco parseTermo(String token) {
        if (token.equals("preco")) return new VariavelPreco();
        if (isNumero(token)) return new ValorConstante(Double.parseDouble(token));
        throw new IllegalArgumentException("Termo inválido na fórmula: '" + token + "'");
    }

    private static ExpressaoPreco criarOperacao(String op, ExpressaoPreco esq, ExpressaoPreco dir) {
        return switch (op) {
            case "+" -> new OperacaoAdicao(esq, dir);
            case "-" -> new OperacaoSubtracao(esq, dir);
            case "*" -> new OperacaoMultiplicacao(esq, dir);
            case "/" -> new OperacaoDivisao(esq, dir);
            default  -> throw new IllegalArgumentException("Operador desconhecido: " + op);
        };
    }

}
