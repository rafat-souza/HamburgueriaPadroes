package hamburgueria;

public abstract class EstiloHamburguer implements Hamburguer {
    protected Proteina proteina;

    public EstiloHamburguer(Proteina proteina) {
        this.proteina = proteina;
    }

    @Override
    public final String getDescricao() {
        return getEstilo() + " de " + proteina.getNome() + montarExtras();
    }

    protected abstract String getEstilo();

    protected String montarExtras() {
        return "";
    }

    @Override
    public abstract double getPreco();

    @Override
    public String aceitar(HamburgueriaVisitor visitor) {
        return visitor.visitarHamburguer(this);
    }
}
