package hamburgueria.NotasFiscais;

import hamburgueria.Combo;
import hamburgueria.IngredientesInfo.DadosIngrediente;
import hamburgueria.IngredientesInfo.DadosIngredienteFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotaFiscal {

    private final int numeroPedido;
    private final List<ItemNotaFiscal> itens = new ArrayList<>();

    public NotaFiscal(int numeroPedido, Combo combo) {
        this.numeroPedido = numeroPedido;

        DadosIngrediente dadosLanche = DadosIngredienteFactory.getDados(
                combo.getHamburguer().getDescricao(),
                "Lanche",
                resolverInfoNutricionalLanche(combo.getHamburguer().getDescricao())
        );
        itens.add(new ItemNotaFiscal(dadosLanche, 1, combo.getHamburguer().getPreco()));

        DadosIngrediente dadosAcomp = DadosIngredienteFactory.getDados(
                combo.getAcompanhamento().getDescricao(),
                "Acompanhamento",
                resolverInfoNutricionalAcompanhamento(combo.getAcompanhamento().getDescricao())
        );
        itens.add(new ItemNotaFiscal(dadosAcomp, 1, 0.0));

        DadosIngrediente dadosBebida = DadosIngredienteFactory.getDados(
                combo.getBebida().getDescricao(),
                "Bebida",
                resolverInfoNutricionalBebida(combo.getBebida().getDescricao())
        );
        itens.add(new ItemNotaFiscal(dadosBebida, 1, 0.0));
    }

    public void adicionarItemAvulso(String nomeIngrediente, String categoria, String infoNutricional,
                                    int quantidade,
                                    double precoUnitario) {
        DadosIngrediente dados = DadosIngredienteFactory.getDados(nomeIngrediente, categoria, infoNutricional);
        itens.add(new ItemNotaFiscal(dados, quantidade, precoUnitario));
    }

    public double getTotalBruto() {
        return itens.stream().mapToDouble(ItemNotaFiscal::getSubtotal).sum();
    }

    public List<ItemNotaFiscal> getItens() {
        return Collections.unmodifiableList(itens);
    }

    public int getNumeroPedido() {
        return numeroPedido;
    }

    public String imprimir() {
        String sep = "=".repeat(90);
        StringBuilder sb = new StringBuilder();
        sb.append(sep).append("\n");
        sb.append(String.format("  NOTA FISCAL — Pedido #%d%n", numeroPedido));
        sb.append(sep).append("\n");
        sb.append(String.format("%-45s %3s  %8s   %10s   %s%n",
                "Ingrediente", "Qtd", "Unit.", "Subtotal", "Info Nutricional"));
        sb.append("-".repeat(90)).append("\n");
        for (ItemNotaFiscal item : itens) {
            sb.append(item.obterLinha()).append("\n");
        }
        sb.append(sep).append("\n");
        sb.append(String.format("  TOTAL BRUTO: R$ %.2f%n", getTotalBruto()));
        sb.append(sep).append("\n");
        return sb.toString();
    }

    private static String resolverInfoNutricionalLanche(String descricao) {
        if (descricao.contains("Gourmet")) return "520 kcal, 28g prot";
        if (descricao.contains("Smash")) return "410 kcal, 22g prot";
        if (descricao.contains("Clássico")) return "450 kcal, 24g prot";
        return "— kcal";
    }

    private static String resolverInfoNutricionalAcompanhamento(String descricao) {
        if (descricao.contains("Batata")) return "320 kcal, 4g prot";
        if (descricao.contains("Salada")) return "45 kcal, 2g prot";
        return "— kcal";
    }

    private static String resolverInfoNutricionalBebida(String descricao) {
        if (descricao.contains("Refrigerante")) return "150 kcal, 0g prot";
        if (descricao.contains("Suco")) return "90 kcal, 1g prot";
        return "— kcal";
    }

}
