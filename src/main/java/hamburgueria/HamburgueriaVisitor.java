package hamburgueria;

public interface HamburgueriaVisitor {

    String visitarHamburguer(Hamburguer hamburguer);
    String visitarAcompanhamento(Acompanhamento acompanhamento);
    String visitarBebida(Bebida bebida);
    String visitarCombo(Combo combo);

}
