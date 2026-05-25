package Testes;

import hamburgueria.Combo;
import hamburgueria.Combos.FabricaComboTradicional;
import hamburgueria.Combos.FabricaComboVegano;
import hamburgueria.Descontos.DescontoPorcentagem;
import hamburgueria.Pedidos.FilaDePedidos;
import hamburgueria.Pedidos.RegistroDePedido;
import hamburgueria.RelatorioDeCaixa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FilaDePedidosTest {

    private FilaDePedidos fila;
    private RegistroDePedido pedido1;
    private RegistroDePedido pedido2;
    private RegistroDePedido pedido3;

    @BeforeEach
    public void setUp() {
        Combo comboTradicional = new Combo(new FabricaComboTradicional());
        Combo comboVegano = new Combo(new FabricaComboVegano());
        Combo comboComDesconto = new Combo(new FabricaComboTradicional());
        comboComDesconto.setEstrategiaDeDesconto(new DescontoPorcentagem(10));

        pedido1 = new RegistroDePedido(1, comboTradicional);
        pedido2 = new RegistroDePedido(2, comboVegano);
        pedido3 = new RegistroDePedido(3, comboComDesconto);

        fila = new FilaDePedidos(pedido1, pedido2, pedido3);
    }

    @Test
    public void deveContarTotalDePedidos() {
        assertEquals(3, RelatorioDeCaixa.contarTotalPedidos(fila));
    }

    @Test
    public void deveFilaVaziaRetornarZeroEmContagem() {
        FilaDePedidos filaVazia = new FilaDePedidos();
        assertEquals(0, RelatorioDeCaixa.contarTotalPedidos(filaVazia));
    }

    @Test
    public void deveAdicionarPedidoRefletirNaContagem() {
        fila.adicionarPedido(new RegistroDePedido(4, new Combo(new FabricaComboTradicional())));
        assertEquals(4, RelatorioDeCaixa.contarTotalPedidos(fila));
    }

    @Test
    public void deveTodosIniciaremComoPendentes() {
        assertEquals(3, RelatorioDeCaixa.contarPedidosPendentes(fila));
    }

    @Test
    public void deveContarPendentesAposUmaEntrega() {
        pedido1.marcarComoEntregue();
        assertEquals(2, RelatorioDeCaixa.contarPedidosPendentes(fila));
    }

    @Test
    public void deveZerarPendentesQuandoTodosEntregues() {
        pedido1.marcarComoEntregue();
        pedido2.marcarComoEntregue();
        pedido3.marcarComoEntregue();
        assertEquals(0, RelatorioDeCaixa.contarPedidosPendentes(fila));
    }

    @Test
    public void deveFilaVaziaRetornarZeroPendentes() {
        assertEquals(0, RelatorioDeCaixa.contarPedidosPendentes(new FilaDePedidos()));
    }

    @Test
    public void deveCalcularReceitaTotalCorretamente() {
        assertEquals(47.4, RelatorioDeCaixa.calcularReceitaTotal(fila), 0.001);
    }

    @Test
    public void deveReceitaTotalSerZeroEmFilaVazia() {
        assertEquals(0.0, RelatorioDeCaixa.calcularReceitaTotal(new FilaDePedidos()), 0.001);
    }

    @Test
    public void deveCalcularReceitaApenasEntregues() {
        pedido1.marcarComoEntregue();
        pedido3.marcarComoEntregue();
        assertEquals(30.4, RelatorioDeCaixa.calcularReceitaPedidosEntregues(fila), 0.001);
    }

    @Test
    public void deveReceitaEntreguesSerZeroSemNenhumaEntrega() {
        assertEquals(0.0, RelatorioDeCaixa.calcularReceitaPedidosEntregues(fila), 0.001);
    }

    @Test
    public void devePedidoIniciarComoNaoEntregue() {
        assertFalse(pedido1.isEntregue());
    }

    @Test
    public void deveMarcarPedidoComoEntregue() {
        pedido1.marcarComoEntregue();
        assertTrue(pedido1.isEntregue());
    }

    @Test
    public void deveNumeroDoPedidoSerPreservado() {
        assertEquals(2, pedido2.getNumeroPedido());
    }

}
