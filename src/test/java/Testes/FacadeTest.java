package Testes;

import hamburgueria.Cardapio.Cardapio;
import hamburgueria.Cardapio.ItemHamburguer;
import hamburgueria.Cardapio.SecaoCardapio;
import hamburgueria.Combo;
import hamburgueria.Combos.FabricaComboTradicional;
import hamburgueria.Combos.FabricaComboVegano;
import hamburgueria.Descontos.DescontoFidelidade;
import hamburgueria.Descontos.DescontoPorcentagem;
import hamburgueria.HamburgueriaPedidoFacade;
import hamburgueria.Promocao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FacadeTest {

    private HamburgueriaPedidoFacade facade;

    @BeforeEach
    public void setUp() {
        Cardapio cardapio = new Cardapio();
        SecaoCardapio secaoLanches = new SecaoCardapio("Hambúrgueres Artesanais");

        FabricaComboTradicional tradicional = new FabricaComboTradicional();
        FabricaComboVegano vegano = new FabricaComboVegano();

        secaoLanches.addItem(new ItemHamburguer(tradicional.criarHamburguer()));
        secaoLanches.addItem(new ItemHamburguer(vegano.criarHamburguer()));

        SecaoCardapio cardapioCompleto = new SecaoCardapio("Menu Principal Hamburgueria");
        cardapioCompleto.addItem(secaoLanches);
        cardapio.setRaiz(cardapioCompleto);

        Promocao promocao = new Promocao("PROMO INAUGURAÇÃO", "Desconto de abertura", 15.0, "31/12/2026");

        facade = new HamburgueriaPedidoFacade(cardapio, promocao);
    }

    @Test
    public void deveExibirCardapioSincronizadoComposto() {
        String cardapio = facade.exibirCardapioSincronizado();

        assertNotNull(cardapio);
        assertTrue(cardapio.contains("=== Menu Principal Hamburgueria ==="));
        assertTrue(cardapio.contains("Hambúrgueres Artesanais"));
        assertTrue(cardapio.contains("Hambúrguer Clássico"));
    }

    @Test
    public void deveAplicarDescontoPromocionalPrototype() {
        Combo combo = facade.criarPedidoTradicional();
        String resposta = facade.aplicarDescontoPromocionalPrototype(combo);

        assertTrue(resposta.contains("pagamento"));
        assertEquals(13.60, combo.getPrecoFinal(), 0.001);
        assertTrue(combo.getDescricaoDesconto().contains("PROMO INAUGURAÇÃO"));
    }

    @Test
    public void deveCriarPedidoTradicionalComDescricaoCorretaDaFabrica() {
        Combo combo = facade.criarPedidoTradicional();
        assertEquals("Hambúrguer Clássico de Carne Bovina", combo.getHamburguer().getDescricao());
    }

    @Test
    public void deveCriarPedidoTradicionalComPrecoCorretoDaFabrica() {
        Combo combo = facade.criarPedidoTradicional();
        assertEquals(16.0, combo.getPrecoFinal());
    }

    @Test
    public void deveCriarPedidoVeganoComDescricaoCorretaDaFabrica() {
        Combo combo = facade.criarPedidoVegano();
        assertEquals("Smash Burger de Proteína de Ervilha", combo.getHamburguer().getDescricao());
    }

    @Test
    public void deveCriarPedidoVeganoComPrecoCorretoDaFabrica() {
        Combo combo = facade.criarPedidoVegano();
        assertEquals(17.0, combo.getPrecoFinal());
    }

    @Test
    public void deveEnviarPedidoRetornarMensagensDaCozinhaEDoCaixa() {
        Combo combo = facade.criarPedidoTradicional();
        String resposta = facade.enviarPedido(combo);

        assertTrue(resposta.contains("PedidoMediator"));
        assertTrue(resposta.contains("Cozinha"));
        assertTrue(resposta.contains("Caixa"));
    }

    @Test
    public void deveCancelarPedidoRetornarMensagensDeCancelamento() {
        Combo combo = facade.criarPedidoTradicional();
        String resposta = facade.cancelarPedido(combo);

        assertTrue(resposta.contains("cancelou"));
        assertTrue(resposta.contains("estornou"));
    }

    @Test
    public void deveAplicarDescontoEProcessarPagamento() {
        Combo combo = facade.criarPedidoTradicional();
        String resposta = facade.aplicarDescontoEProcessarPagamento(combo, new DescontoPorcentagem(10));

        assertTrue(resposta.contains("pagamento"));
        assertEquals(14.4, combo.getPrecoFinal(), 0.001);

        assertTrue(resposta.contains("Emitindo Comprovante"));
        assertTrue(resposta.contains("Hambúrguer Clássico de Carne Bovina"));
    }

    @Test
    public void deveAplicarDescontoFidelidadeEProcessarPagamento() {
        Combo combo = facade.criarPedidoVegano();
        String resposta = facade.aplicarDescontoEProcessarPagamento(combo, new DescontoFidelidade(5.0));

        assertTrue(resposta.contains("pagamento"));
        assertEquals(12.0, combo.getPrecoFinal());

        assertTrue(resposta.contains("Emitindo Comprovante"));
        assertTrue(resposta.contains("Smash Burger de Proteína de Ervilha"));
    }

}
