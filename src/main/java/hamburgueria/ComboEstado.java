package hamburgueria;

public interface ComboEstado {

    Hamburguer getHamburguer();
    Acompanhamento getAcompanhamento();
    Bebida getBebida();
    EstrategiaDeDesconto getEstrategiaDeDesconto();
    String getDescricaoEstado();

}
