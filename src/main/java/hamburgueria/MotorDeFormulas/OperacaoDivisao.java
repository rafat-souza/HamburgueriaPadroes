package hamburgueria.MotorDeFormulas;

public class OperacaoDivisao implements ExpressaoPreco {

    private final ExpressaoPreco esquerda;
    private final ExpressaoPreco direita;

    public OperacaoDivisao(ExpressaoPreco esquerda, ExpressaoPreco direita) {
        this.esquerda = esquerda;
        this.direita  = direita;
    }

    @Override
    public double interpretar(double preco) {
        double denominador = direita.interpretar(preco);
        if (denominador == 0)
            throw new ArithmeticException("Divisão por zero na fórmula de preço.");
        return esquerda.interpretar(preco) / denominador;
    }

}
