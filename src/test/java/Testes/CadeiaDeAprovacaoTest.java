package Testes;

import hamburgueria.FuncionarioHamburgueria;
import hamburgueria.Funcionarios.FuncionarioAtendente;
import hamburgueria.Funcionarios.FuncionarioCaixa;
import hamburgueria.Funcionarios.FuncionarioDono;
import hamburgueria.Funcionarios.FuncionarioGerente;
import hamburgueria.Solicitacao;
import hamburgueria.Solicitacoes.TipoSolicitacaoCancelamento;
import hamburgueria.Solicitacoes.TipoSolicitacaoDesconto;
import hamburgueria.Solicitacoes.TipoSolicitacaoInformacao;
import hamburgueria.Solicitacoes.TipoSolicitacaoReembolso;
import hamburgueria.TipoSolicitacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class CadeiaDeAprovacaoTest {

    private FuncionarioHamburgueria atendente;

    @BeforeEach
    public void setUp() {
        FuncionarioDono   dono    = new FuncionarioDono();
        FuncionarioGerente gerente = new FuncionarioGerente(dono);
        FuncionarioCaixa   caixa   = new FuncionarioCaixa(gerente);
        atendente = new FuncionarioAtendente(caixa);
    }

    @Test
    public void deveAtendenteTratarSolicitacaoDeInformacao() {
        Solicitacao solicitacao = new Solicitacao(TipoSolicitacaoInformacao.getTipoSolicitacaoInformacao());

        assertEquals("Atendente", atendente.atenderSolicitacao(solicitacao));
    }

    @Test
    public void deveCaixaTratarSolicitacaoDeDesconto() {
        Solicitacao solicitacao = new Solicitacao(TipoSolicitacaoDesconto.getTipoSolicitacaoDesconto());

        assertEquals("Caixa", atendente.atenderSolicitacao(solicitacao));
    }

    @Test
    public void deveGerenteTratarSolicitacaoDeCancelamento() {
        Solicitacao solicitacao = new Solicitacao(TipoSolicitacaoCancelamento.getTipoSolicitacaoCancelamento());

        assertEquals("Gerente", atendente.atenderSolicitacao(solicitacao));
    }

    @Test
    public void deveDonoTratarSolicitacaoDeReembolso() {
        Solicitacao solicitacao = new Solicitacao(TipoSolicitacaoReembolso.getTipoSolicitacaoReembolso());

        assertEquals("Dono", atendente.atenderSolicitacao(solicitacao));
    }

    @Test
    public void deveCadeiaRetornarSemAtendimentoParaTipoDesconhecido() {
        Solicitacao solicitacao = new Solicitacao(new TipoSolicitacao() {});

        assertEquals("Sem atendimento", atendente.atenderSolicitacao(solicitacao));
    }

    @Test
    public void deveDonoSemSuperiorRetornarSemAtendimentoDireto() {
        FuncionarioDono dono = new FuncionarioDono();
        Solicitacao solicitacao = new Solicitacao(TipoSolicitacaoInformacao.getTipoSolicitacaoInformacao());

        assertEquals("Sem atendimento", dono.atenderSolicitacao(solicitacao));
    }

    @Test
    public void deveTipoSolicitacaoInformacaoSerSingleton() {
        assertSame(
                TipoSolicitacaoInformacao.getTipoSolicitacaoInformacao(),
                TipoSolicitacaoInformacao.getTipoSolicitacaoInformacao()
        );
    }

    @Test
    public void deveTipoSolicitacaoDescontoSerSingleton() {
        assertSame(
                TipoSolicitacaoDesconto.getTipoSolicitacaoDesconto(),
                TipoSolicitacaoDesconto.getTipoSolicitacaoDesconto()
        );
    }

    @Test
    public void deveTipoSolicitacaoCancelamentoSerSingleton() {
        assertSame(
                TipoSolicitacaoCancelamento.getTipoSolicitacaoCancelamento(),
                TipoSolicitacaoCancelamento.getTipoSolicitacaoCancelamento()
        );
    }

    @Test
    public void deveTipoSolicitacaoReembolsoSerSingleton() {
        assertSame(
                TipoSolicitacaoReembolso.getTipoSolicitacaoReembolso(),
                TipoSolicitacaoReembolso.getTipoSolicitacaoReembolso()
        );
    }

    @Test
    public void deveCadeiaIniciarPeloAtendente() {
        assertEquals("Atendente", atendente.getDescricaoCargo());
    }

    @Test
    public void deveCaixaSerSuperiorDoAtendente() {
        assertEquals("Caixa", atendente.getFuncionarioSuperior().getDescricaoCargo());
    }

    @Test
    public void deveGerenteSerSuperiorDoCaixa() {
        assertEquals("Gerente",
                atendente.getFuncionarioSuperior()
                        .getFuncionarioSuperior()
                        .getDescricaoCargo());
    }

    @Test
    public void deveDonoSerTopoSemSuperior() {
        FuncionarioHamburgueria topo =
                atendente.getFuncionarioSuperior()
                        .getFuncionarioSuperior()
                        .getFuncionarioSuperior();

        assertEquals("Dono", topo.getDescricaoCargo());
        assertEquals(null, topo.getFuncionarioSuperior());
    }

    @Test
    public void deveCaixaIniciarCadeiaEDelegarAoGerente() {
        FuncionarioDono dono = new FuncionarioDono();
        FuncionarioGerente gerente = new FuncionarioGerente(dono);
        FuncionarioCaixa caixa = new FuncionarioCaixa(gerente);

        Solicitacao solicitacao = new Solicitacao(TipoSolicitacaoCancelamento.getTipoSolicitacaoCancelamento());

        assertEquals("Gerente", caixa.atenderSolicitacao(solicitacao));
    }

    @Test
    public void deveSolicitacaoDeInformacaoNaoChegarAoDono() {
        Solicitacao solicitacao = new Solicitacao(TipoSolicitacaoInformacao.getTipoSolicitacaoInformacao());

        String responsavel = atendente.atenderSolicitacao(solicitacao);
        assertEquals("Atendente", responsavel);
    }

}
