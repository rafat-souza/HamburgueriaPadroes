package hamburgueria.Visitantes;

import hamburgueria.Acompanhamento;
import hamburgueria.Bebida;
import hamburgueria.Combo;
import hamburgueria.Hamburguer;
import hamburgueria.HamburgueriaVisitor;

public class ExportadorJsonVisitor implements HamburgueriaVisitor {

    @Override
    public String visitarHamburguer(Hamburguer hamburguer) {
        return String.format(
                "{\"tipo\":\"hamburguer\",\"descricao\":\"%s\",\"preco\":%.2f}",
                hamburguer.getDescricao(), hamburguer.getPreco());
    }

    @Override
    public String visitarAcompanhamento(Acompanhamento acompanhamento) {
        return String.format(
                "{\"tipo\":\"acompanhamento\",\"descricao\":\"%s\"}",
                acompanhamento.getDescricao());
    }

    @Override
    public String visitarBebida(Bebida bebida) {
        return String.format(
                "{\"tipo\":\"bebida\",\"descricao\":\"%s\"}",
                bebida.getDescricao());
    }

    @Override
    public String visitarCombo(Combo combo) {
        return String.format(
                "{" +
                        "\"hamburguer\":%s," +
                        "\"acompanhamento\":%s," +
                        "\"bebida\":%s," +
                        "\"desconto\":\"%s\"," +
                        "\"totalComDesconto\":%.2f" +
                        "}",
                combo.getHamburguer().aceitar(this),
                combo.getAcompanhamento().aceitar(this),
                combo.getBebida().aceitar(this),
                combo.getDescricaoDesconto(),
                combo.getPrecoFinal());
    }
}
