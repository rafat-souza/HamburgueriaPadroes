package hamburgueria.Visitantes;

import hamburgueria.Acompanhamento;
import hamburgueria.Bebida;
import hamburgueria.Combo;
import hamburgueria.Hamburguer;
import hamburgueria.HamburgueriaVisitor;

public class ResumoVisitor implements HamburgueriaVisitor {

    @Override
    public String visitarHamburguer(Hamburguer hamburguer) {
        return String.format("Lanche     : %s  —  R$ %.2f",
                hamburguer.getDescricao(), hamburguer.getPreco());
    }

    @Override
    public String visitarAcompanhamento(Acompanhamento acompanhamento) {
        return "Acompanhamento: " + acompanhamento.getDescricao();
    }

    @Override
    public String visitarBebida(Bebida bebida) {
        return "Bebida     : " + bebida.getDescricao();
    }

    @Override
    public String visitarCombo(Combo combo) {
        String separador = "=".repeat(48);
        return separador + "\n" +
                "           RESUMO DO PEDIDO\n" +
                separador + "\n" +
                combo.getHamburguer().aceitar(this)      + "\n" +
                combo.getAcompanhamento().aceitar(this)  + "\n" +
                combo.getBebida().aceitar(this)          + "\n" +
                separador + "\n" +
                "Desconto   : " + combo.getDescricaoDesconto()   + "\n" +
                String.format("TOTAL      : R$ %.2f%n", combo.getPrecoFinal()) +
                separador;
    }
}
