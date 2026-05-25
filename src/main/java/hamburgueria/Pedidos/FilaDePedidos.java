package hamburgueria.Pedidos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class FilaDePedidos implements Iterable<RegistroDePedido> {

    private List<RegistroDePedido> pedidos = new ArrayList<>();

    public FilaDePedidos(RegistroDePedido... pedidos) {
        this.pedidos = new ArrayList<>(Arrays.asList(pedidos));
    }

    public void adicionarPedido(RegistroDePedido pedido) {
        this.pedidos.add(pedido);
    }

    @Override
    public Iterator<RegistroDePedido> iterator() {
        return pedidos.iterator();
    }

}
