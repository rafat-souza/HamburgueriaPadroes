package hamburgueria.NotasFiscais;

import hamburgueria.IngredientesInfo.DadosIngrediente;

public class ItemNotaFiscal {

    private final DadosIngrediente dados;
    private final int quantidade;
    private final double precoUnitario;

    public ItemNotaFiscal(DadosIngrediente dados, int quantidade, double precoUnitario) {
        this.dados = dados;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    public double getSubtotal() {
        return quantidade * precoUnitario;
    }

    public String obterLinha() {
        return String.format("%-45s %dx R$ %6.2f  =  R$ %7.2f   | %s",
                dados.getNome(),
                quantidade,
                precoUnitario,
                getSubtotal(),
                dados.getInfoNutricional());
    }

    public DadosIngrediente getDados() {
        return dados;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

}
