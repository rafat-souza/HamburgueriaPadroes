package hamburgueria.Setores;

import hamburgueria.Combo;
import hamburgueria.NotasFiscais.NotaFiscal;
import hamburgueria.PedidoObserver;
import hamburgueria.SetorDaHamburgueria;

public class Caixa implements PedidoObserver, SetorDaHamburgueria {

    private static int numeroDaNota = 1;

    @Override
    public String onPedidoFinalizado(Combo combo) {
        return "[Caixa] Cobrança: R$ " + combo.getHamburguer().getPreco();
    }

    @Override
    public String receberNovoPedido(Combo combo) {
        return "O Caixa registrou o novo pedido no valor de R$ "
                + String.format("%.2f", combo.getPrecoFinal());
    }

    @Override
    public String receberCancelamento(Combo combo) {
        return "O Caixa estornou o pedido de: " + combo.getHamburguer().getDescricao();
    }

    @Override
    public String receberPagamento(Combo combo) {
        NotaFiscal nota = new NotaFiscal(numeroDaNota++, combo);

        return "O Caixa processou o pagamento de R$ "
                + String.format("%.2f", combo.getPrecoFinal()) + " com sucesso.\n"
                + ">> Emitindo Comprovante:\n"
                + nota.imprimir();
    }

}
