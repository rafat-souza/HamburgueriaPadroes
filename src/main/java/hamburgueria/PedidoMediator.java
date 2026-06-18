package hamburgueria;

import hamburgueria.Pedidos.FilaDePedidos;
import hamburgueria.Pedidos.RegistroDePedido;
import hamburgueria.Setores.Caixa;
import hamburgueria.Setores.Cozinha;

public class PedidoMediator {

    private static PedidoMediator instancia = new PedidoMediator();

    private final FilaDePedidos historicoGlobal = new FilaDePedidos();
    private int sequencialPedidos = 1;

    private PedidoMediator() {}

    public static PedidoMediator getInstancia() {
        return instancia;
    }

    public String receberNovoPedidoAtendente(Combo combo) {
        return "O PedidoMediator registrou o pedido e notificou os setores.\n" +
                ">>" + new Cozinha().receberNovoPedido(combo) + "\n" +
                ">>" + new Caixa().receberNovoPedido(combo);
    }

    public String receberCancelamentoAtendente(Combo combo) {
        return "O PedidoMediator registrou o cancelamento e notificou os setores.\n" +
                ">>" + new Cozinha().receberCancelamento(combo) + "\n" +
                ">>" + new Caixa().receberCancelamento(combo);
    }

    public String receberPagamentoAtendente(Combo combo) {
        RegistroDePedido registro = new RegistroDePedido(sequencialPedidos++, combo);
        registro.marcarComoEntregue();
        historicoGlobal.adicionarPedido(registro);

        return "O PedidoMediator registrou o pagamento e notificou os setores.\n" +
                ">>" + new Caixa().receberPagamento(combo) + "\n" +
                ">>" + new Cozinha().receberPagamento(combo) + "\n" +
                "[Métrica Sistema] Total de receita acumulada no Caixa: R$ " +
                String.format("%.2f", RelatorioDeCaixa.calcularReceitaTotal(historicoGlobal));
    }

}
