package hamburgueria;

public interface Hamburguer {
    String getDescricao();
    double getPreco();
    String aceitar(HamburgueriaVisitor visitor);
}