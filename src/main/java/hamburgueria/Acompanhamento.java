package hamburgueria;

public interface Acompanhamento {
    String getDescricao();
    String aceitar(HamburgueriaVisitor visitor);
}
