package Testes;

import hamburgueria.Combos.FabricaComboTradicional;
import hamburgueria.Combos.FabricaComboVegano;
import hamburgueria.EstilosDeBurger.BurgerGourmet;
import hamburgueria.Extras.Queijo;
import hamburgueria.Hamburguer;
import hamburgueria.Preparo.*;
import hamburgueria.Proteinas.Carne;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PreparacaoCommandTest {

    private PreparacaoLanche preparacao;
    private FilaPreparacao fila;

    @BeforeEach
    public void setUp() {
        Hamburguer hamburguer = new FabricaComboTradicional().criarHamburguer();
        preparacao = new PreparacaoLanche(hamburguer);
        fila = new FilaPreparacao();
    }

    @Test
    public void devePreparacaoIniciarComTodasEtapasPendentes() {
        assertFalse(preparacao.isProteinaGrelhada());
        assertFalse(preparacao.isLancheMontado());
        assertFalse(preparacao.isPedidoEmbalado());
    }

    @Test
    public void deveGrelharProteinaAlterarEstadoDoReceiver() {
        fila.executarEtapa(new ComandoGrelharProteina(preparacao));

        assertTrue(preparacao.isProteinaGrelhada());
    }

    @Test
    public void deveGrelharProteinaRetornarMensagemComDescricaoDoLanche() {
        String resposta = fila.executarEtapa(new ComandoGrelharProteina(preparacao));

        assertTrue(resposta.contains("[Cozinha]"));
        assertTrue(resposta.contains("Hambúrguer Clássico de Carne Bovina"));
    }

    @Test
    public void deveCancelarGrelharDescartarProteinaERevertirEstado() {
        fila.executarEtapa(new ComandoGrelharProteina(preparacao));
        fila.desfazerUltimaEtapa();

        assertFalse(preparacao.isProteinaGrelhada());
    }

    @Test
    public void deveCancelarGrelharRetornarMensagemDeDescarte() {
        fila.executarEtapa(new ComandoGrelharProteina(preparacao));
        String resposta = fila.desfazerUltimaEtapa();

        assertTrue(resposta.contains("descartada"));
    }

    @Test
    public void deveMontarLancheAlterarEstadoDoReceiver() {
        fila.executarEtapa(new ComandoMontarLanche(preparacao));

        assertTrue(preparacao.isLancheMontado());
    }

    @Test
    public void deveCancelarMontarLancheDesmontarERevertirEstado() {
        fila.executarEtapa(new ComandoMontarLanche(preparacao));
        fila.desfazerUltimaEtapa();

        assertFalse(preparacao.isLancheMontado());
    }

    @Test
    public void deveCancelarMontarLancheRetornarMensagemDeDesmontagem() {
        fila.executarEtapa(new ComandoMontarLanche(preparacao));
        String resposta = fila.desfazerUltimaEtapa();

        assertTrue(resposta.contains("desmontado"));
    }

    @Test
    public void deveEmbalarPedidoAlterarEstadoDoReceiver() {
        fila.executarEtapa(new ComandoEmbalarPedido(preparacao));

        assertTrue(preparacao.isPedidoEmbalado());
    }

    @Test
    public void deveCancelarEmbalarDesembalarERevertirEstado() {
        fila.executarEtapa(new ComandoEmbalarPedido(preparacao));
        fila.desfazerUltimaEtapa();

        assertFalse(preparacao.isPedidoEmbalado());
    }

    @Test
    public void deveCancelarEmbalarRetornarMensagemDeDesembalagem() {
        fila.executarEtapa(new ComandoEmbalarPedido(preparacao));
        String resposta = fila.desfazerUltimaEtapa();

        assertTrue(resposta.contains("desembalado"));
    }

    @Test
    public void deveFilaAcumularEtapasNoHistorico() {
        fila.executarEtapa(new ComandoGrelharProteina(preparacao));
        fila.executarEtapa(new ComandoMontarLanche(preparacao));
        fila.executarEtapa(new ComandoEmbalarPedido(preparacao));

        assertEquals(3, fila.getHistorico().size());
    }

    @Test
    public void deveDesfazerRemoverUltimaEtapaDoHistorico() {
        fila.executarEtapa(new ComandoGrelharProteina(preparacao));
        fila.executarEtapa(new ComandoMontarLanche(preparacao));

        fila.desfazerUltimaEtapa();

        assertEquals(1, fila.getHistorico().size());
    }

    @Test
    public void deveDesfazerRevertirApenasUltimaEtapa() {
        fila.executarEtapa(new ComandoGrelharProteina(preparacao));
        fila.executarEtapa(new ComandoMontarLanche(preparacao));

        fila.desfazerUltimaEtapa(); // desfaz montagem

        assertTrue(preparacao.isProteinaGrelhada());  // grelhado permanece
        assertFalse(preparacao.isLancheMontado());    // montagem desfeita
    }

    @Test
    public void deveDesfazerRetornarMensagemQuandoHistoricoVazio() {
        String resposta = fila.desfazerUltimaEtapa();

        assertEquals("[Cozinha] Nenhuma etapa para desfazer.", resposta);
    }

    @Test
    public void deveFluxoCompletoPassarPorTodasAsEtapas() {
        fila.executarEtapa(new ComandoGrelharProteina(preparacao));
        fila.executarEtapa(new ComandoMontarLanche(preparacao));
        fila.executarEtapa(new ComandoEmbalarPedido(preparacao));

        assertTrue(preparacao.isProteinaGrelhada());
        assertTrue(preparacao.isLancheMontado());
        assertTrue(preparacao.isPedidoEmbalado());
    }

    @Test
    public void deveCancelarTodoOPreparoPorDesfazerEmCascata() {
        fila.executarEtapa(new ComandoGrelharProteina(preparacao));
        fila.executarEtapa(new ComandoMontarLanche(preparacao));
        fila.executarEtapa(new ComandoEmbalarPedido(preparacao));

        fila.desfazerUltimaEtapa();
        fila.desfazerUltimaEtapa();
        fila.desfazerUltimaEtapa();

        assertFalse(preparacao.isPedidoEmbalado());
        assertFalse(preparacao.isLancheMontado());
        assertFalse(preparacao.isProteinaGrelhada());
        assertTrue(fila.getHistorico().isEmpty());
    }

    @Test
    public void devePreparacaoFuncionarComLancheDecorado() {
        Hamburguer decorado = new Queijo(new BurgerGourmet(new Carne()));
        PreparacaoLanche prepDecorado = new PreparacaoLanche(decorado);
        FilaPreparacao filaDecorado = new FilaPreparacao();

        filaDecorado.executarEtapa(new ComandoGrelharProteina(prepDecorado));
        filaDecorado.executarEtapa(new ComandoMontarLanche(prepDecorado));

        assertTrue(prepDecorado.isProteinaGrelhada());
        assertTrue(prepDecorado.isLancheMontado());
    }

    @Test
    public void devePreparacaoFuncionarComComboVegano() {
        Hamburguer vegano = new FabricaComboVegano().criarHamburguer();
        PreparacaoLanche prepVegano = new PreparacaoLanche(vegano);
        FilaPreparacao filaVegano = new FilaPreparacao();

        String resposta = filaVegano.executarEtapa(new ComandoGrelharProteina(prepVegano));

        assertTrue(resposta.contains("Proteína de Ervilha"));
        assertTrue(prepVegano.isProteinaGrelhada());
    }

}
