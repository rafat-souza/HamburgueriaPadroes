package hamburgueria.MotorDeFormulas;

public class OperacaoMultiplicacao implements ExpressaoPreco {

    private final ExpressaoPreco esquerda;
    private final ExpressaoPreco direita;

    public OperacaoMultiplicacao(ExpressaoPreco esquerda, ExpressaoPreco direita) {
        this.esquerda = esquerda;
        this.direita  = direita;
    }

    @Override
    public double interpretar(double preco) {
        return esquerda.interpretar(preco) * direita.interpretar(preco);
    }

}
