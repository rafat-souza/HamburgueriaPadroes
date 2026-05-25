package hamburgueria;

import hamburgueria.Pedidos.FilaDePedidos;
import hamburgueria.Pedidos.RegistroDePedido;

import java.util.Iterator;

public class RelatorioDeCaixa {

    public static int contarPedidosPendentes(FilaDePedidos fila) {
        int quantidade = 0;
        for (RegistroDePedido pedido : fila) {
            if (!pedido.isEntregue()) {
                quantidade++;
            }
        }
        return quantidade;
    }

    public static int contarTotalPedidos(FilaDePedidos fila) {
        int quantidade = 0;
        for (Iterator<RegistroDePedido> it = fila.iterator(); it.hasNext(); ) {
            quantidade++;
            it.next();
        }
        return quantidade;
    }

    public static double calcularReceitaTotal(FilaDePedidos fila) {
        double total = 0.0;
        for (RegistroDePedido pedido : fila) {
            total += pedido.getCombo().getPrecoFinal();
        }
        return total;
    }

    public static double calcularReceitaPedidosEntregues(FilaDePedidos fila) {
        double total = 0.0;
        for (RegistroDePedido pedido : fila) {
            if (pedido.isEntregue()) {
                total += pedido.getCombo().getPrecoFinal();
            }
        }
        return total;
    }

}
