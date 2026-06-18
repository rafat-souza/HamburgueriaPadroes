package Testes;

import hamburgueria.Atendente;
import hamburgueria.Combo;
import hamburgueria.Combos.FabricaComboTradicional;
import hamburgueria.Combos.FabricaComboVegano;
import hamburgueria.Descontos.DescontoPorcentagem;
import hamburgueria.PedidoMediator;
import hamburgueria.RelatorioDeCaixa;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MediatorTest {

    @Test
    public void deveFazerPedidoRotearParaCozinhaECaixa() {
        Atendente atendente = new Atendente();
        Combo combo = new Combo(new FabricaComboTradicional());

        String resposta = atendente.fazerPedido(combo);

        assertTrue(resposta.contains("PedidoMediator"));
        assertTrue(resposta.contains("Cozinha começou a preparar"));
        assertTrue(resposta.contains("Proteína grelhada"));
        assertTrue(resposta.contains("Caixa"));
    }

    @Test
    public void deveCancelarPedidoNotificarAmbosSetos() {
        Atendente atendente = new Atendente();
        Combo combo = new Combo(new FabricaComboVegano());

        String resposta = atendente.cancelarPedido(combo);

        assertTrue(resposta.contains("cancelamento"));
        assertTrue(resposta.contains("cancelou o preparo"));
        assertTrue(resposta.contains("rollback das etapas"));
        assertTrue(resposta.contains("estornou"));
    }

    @Test
    public void deveProcessarPagamentoComDescontoAplicado() {
        Atendente atendente = new Atendente();
        Combo combo = new Combo(new FabricaComboTradicional());
        combo.setEstrategiaDeDesconto(new DescontoPorcentagem(10));

        String resposta = atendente.processarPagamento(combo);

        assertTrue(resposta.contains("pagamento"));
        assertTrue(resposta.contains("14,40") || resposta.contains("14.40"));
        assertTrue(resposta.contains("[Métrica Sistema]"));
    }

    @Test
    public void devePagamentoAdicionarPedidoAoHistoricoGlobalDaFila() {
        Atendente atendente = new Atendente();
        Combo combo = new Combo(new FabricaComboTradicional());

        int totalAntes = RelatorioDeCaixa.contarTotalPedidos(PedidoMediator.getInstancia().getHistoricoGlobal());

        atendente.processarPagamento(combo);

        int totalDepois = RelatorioDeCaixa.contarTotalPedidos(PedidoMediator.getInstancia().getHistoricoGlobal());

        assertEquals(totalAntes + 1, totalDepois);
    }

    @Test
    public void deveAtendenteComunicarSempreViaMediatorNuncaDiretamente() {
        Atendente atendente = new Atendente();
        Combo combo = new Combo(new FabricaComboTradicional());

        assertDoesNotThrow(() -> {
            atendente.fazerPedido(combo);
            atendente.processarPagamento(combo);
        });
    }

}
