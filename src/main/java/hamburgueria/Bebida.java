package hamburgueria;

public interface Bebida {
    String getDescricao();
    String aceitar(HamburgueriaVisitor visitor);
}
