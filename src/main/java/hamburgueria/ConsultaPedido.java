package hamburgueria;

public class ConsultaPedido implements IConsultaPedido {

    private final int numeroPedido;
    private final Combo combo;

    public ConsultaPedido(int numeroPedido, Combo combo) {
        this.numeroPedido = numeroPedido;
        this.combo = combo;
    }

    @Override
    public String obterResumo() {
        return "=== Pedido #" + numeroPedido + " ===\n"
                + "Lanche        : " + combo.getHamburguer().getDescricao() + "\n"
                + "Acompanhamento: " + combo.getAcompanhamento().getDescricao() + "\n"
                + "Bebida        : " + combo.getBebida().getDescricao();
    }

    @Override
    public String obterDetalhesFinanceiros(FuncionarioHamburgueria funcionario) {
        return String.format(
                "=== Detalhes Financeiros — Pedido #%d (consultado por %s) ===\n"
                        + "Preço do lanche     : R$ %.2f\n"
                        + "Desconto            : %s\n"
                        + "Total com desconto  : R$ %.2f",
                numeroPedido,
                funcionario.getDescricaoCargo(),
                combo.getHamburguer().getPreco(),
                combo.getDescricaoDesconto(),
                combo.getPrecoFinal());
    }

}
