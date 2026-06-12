package hamburgueria.MotorDeFormulas;

public class OperacaoAdicao implements ExpressaoPreco {

    private final ExpressaoPreco esquerda;
    private final ExpressaoPreco direita;

    public OperacaoAdicao(ExpressaoPreco esquerda, ExpressaoPreco direita) {
        this.esquerda = esquerda;
        this.direita  = direita;
    }

    @Override
    public double interpretar(double preco) {
        return esquerda.interpretar(preco) + direita.interpretar(preco);
    }

}
