package hamburgueria.MotorDeFormulas;

public class ValorConstante implements ExpressaoPreco {

    private final double valor;

    public ValorConstante(double valor) {
        this.valor = valor;
    }

    public double getValor() {
        return valor;
    }

    @Override
    public double interpretar(double preco) {
        return valor;
    }

}
