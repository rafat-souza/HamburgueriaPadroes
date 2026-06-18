package hamburgueria.Setores;

import hamburgueria.Combo;
import hamburgueria.PedidoObserver;
import hamburgueria.SetorDaHamburgueria;

public class Caixa implements PedidoObserver, SetorDaHamburgueria {

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
        return "O Caixa processou o pagamento de R$ "
                + String.format("%.2f", combo.getPrecoFinal()) + " com sucesso.";
    }

}
