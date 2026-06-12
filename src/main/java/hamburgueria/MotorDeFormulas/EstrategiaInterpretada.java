package hamburgueria.MotorDeFormulas;

import hamburgueria.EstrategiaDeDesconto;

public class EstrategiaInterpretada implements EstrategiaDeDesconto {

    private final RegraDePreco regra;

    public EstrategiaInterpretada(RegraDePreco regra) {
        this.regra = regra;
    }

    @Override
    public double aplicar(double preco) {
        return regra.aplicar(preco);
    }

    @Override
    public String getDescricao() {
        return "Regra interpretada: [" + regra.getFormula() + "]";
    }

}
