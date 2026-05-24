package hamburgueria;

public abstract class IngredienteDecorator implements Hamburguer {
    protected Hamburguer hamburguer;

    public IngredienteDecorator(Hamburguer hamburguer) {
        this.hamburguer = hamburguer;
    }

    @Override
    public String aceitar(HamburgueriaVisitor visitor) {
        return visitor.visitarHamburguer(this);
    }
}
