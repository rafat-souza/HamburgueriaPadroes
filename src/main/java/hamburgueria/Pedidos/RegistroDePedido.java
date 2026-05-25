package hamburgueria.Pedidos;

import hamburgueria.Combo;

public class RegistroDePedido {

    private final int numeroPedido;
    private final Combo combo;
    private boolean entregue;

    public RegistroDePedido(int numeroPedido, Combo combo) {
        this.numeroPedido = numeroPedido;
        this.combo = combo;
        this.entregue = false;
    }

    public int getNumeroPedido() {
        return numeroPedido;
    }

    public Combo getCombo() {
        return combo;
    }

    public boolean isEntregue() {
        return entregue;
    }

    public void marcarComoEntregue() {
        this.entregue = true;
    }

}
