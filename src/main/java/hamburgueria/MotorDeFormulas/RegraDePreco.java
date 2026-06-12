package hamburgueria.MotorDeFormulas;

public class RegraDePreco {

    private String formula;

    public RegraDePreco(String formula) {
        this.formula = formula;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public double aplicar(double preco) {
        InterpretadorFormulaPreco interpretador = new InterpretadorFormulaPreco(formula);
        return Math.max(0.0, interpretador.interpretar(preco));
    }

}
