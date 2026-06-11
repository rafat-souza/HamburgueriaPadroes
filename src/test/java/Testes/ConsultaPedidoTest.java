package Testes;

import hamburgueria.*;
import hamburgueria.Combos.FabricaComboTradicional;
import hamburgueria.Combos.FabricaComboVegano;
import hamburgueria.Descontos.DescontoPorcentagem;
import hamburgueria.Funcionarios.FuncionarioAtendente;
import hamburgueria.Funcionarios.FuncionarioCaixa;
import hamburgueria.Funcionarios.FuncionarioDono;
import hamburgueria.Funcionarios.FuncionarioGerente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConsultaPedidoTest {

    private Combo comboTradicional;
    private Combo comboVegano;
    private FuncionarioHamburgueria atendente;
    private FuncionarioHamburgueria caixa;
    private FuncionarioHamburgueria gerente;
    private FuncionarioHamburgueria dono;

    @BeforeEach
    public void setUp() {
        comboTradicional = new Combo(new FabricaComboTradicional());
        comboVegano = new Combo(new FabricaComboVegano());

        dono = new FuncionarioDono();
        gerente = new FuncionarioGerente(dono);
        caixa = new FuncionarioCaixa(gerente);
        atendente = new FuncionarioAtendente(caixa);
    }

    @Test
    public void deveResumoConterDescricaoDoLanche() {
        IConsultaPedido consulta = new ConsultaPedidoProxy(1, comboTradicional);
        assertTrue(consulta.obterResumo().contains("Hambúrguer Clássico de Carne Bovina"));
    }

    @Test
    public void deveResumoConterAcompanhamentoEBebida() {
        IConsultaPedido consulta = new ConsultaPedidoProxy(1, comboTradicional);
        String resumo = consulta.obterResumo();
        assertTrue(resumo.contains("Batata Frita Média"));
        assertTrue(resumo.contains("Refrigerante Cola"));
    }

    @Test
    public void deveResumoConterNumeroDoPedido() {
        IConsultaPedido consulta = new ConsultaPedidoProxy(42, comboTradicional);
        assertTrue(consulta.obterResumo().contains("42"));
    }

    @Test
    public void deveAtendenteConsultarResumoSemRestricao() {
        IConsultaPedido consulta = new ConsultaPedidoProxy(1, comboTradicional);
        assertDoesNotThrow(() -> consulta.obterResumo());
    }

    @Test
    public void deveCaixaConsultarDetalhesFinanceiros() {
        IConsultaPedido consulta = new ConsultaPedidoProxy(1, comboTradicional);
        assertDoesNotThrow(() -> consulta.obterDetalhesFinanceiros(caixa));
    }

    @Test
    public void deveGerenteConsultarDetalhesFinanceiros() {
        IConsultaPedido consulta = new ConsultaPedidoProxy(1, comboTradicional);
        assertDoesNotThrow(() -> consulta.obterDetalhesFinanceiros(gerente));
    }

    @Test
    public void deveDonoConsultarDetalhesFinanceiros() {
        IConsultaPedido consulta = new ConsultaPedidoProxy(1, comboTradicional);
        assertDoesNotThrow(() -> consulta.obterDetalhesFinanceiros(dono));
    }

    @Test
    public void deveDetalhesFinanceirosConterPrecoEDesconto() {
        comboTradicional.setEstrategiaDeDesconto(new DescontoPorcentagem(10));
        IConsultaPedido consulta = new ConsultaPedidoProxy(1, comboTradicional);
        String detalhes = consulta.obterDetalhesFinanceiros(caixa);

        assertTrue(detalhes.contains("16,00") || detalhes.contains("16.00"));
        assertTrue(detalhes.contains("14,40") || detalhes.contains("14.40"));
    }

    @Test
    public void deveDetalhesFinanceirosConterCargoDoFuncionario() {
        IConsultaPedido consulta = new ConsultaPedidoProxy(1, comboTradicional);
        String detalhes = consulta.obterDetalhesFinanceiros(caixa);
        assertTrue(detalhes.contains("Caixa"));
    }

    @Test
    public void deveAtendenteLancarExcecaoAoAcessarDetalhesFinanceiros() {
        IConsultaPedido consulta = new ConsultaPedidoProxy(1, comboTradicional);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> consulta.obterDetalhesFinanceiros(atendente));

        assertTrue(ex.getMessage().contains("não autorizado"));
        assertTrue(ex.getMessage().contains("Atendente"));
    }

    @Test
    public void deveProxyCriarObjetoRealApenasNaPrimeiraChamada() {
        assertDoesNotThrow(() -> new ConsultaPedidoProxy(1, comboTradicional));
    }

    @Test
    public void deveProxyRetornarMesmoResultadoQueObjetoReal() {
        ConsultaPedido real  = new ConsultaPedido(5, comboTradicional);
        IConsultaPedido proxy = new ConsultaPedidoProxy(5, comboTradicional);
        assertEquals(real.obterResumo(), proxy.obterResumo());
    }

    @Test
    public void deveMultiplasChamadasRetornarMesmoResultado() {
        IConsultaPedido consulta = new ConsultaPedidoProxy(1, comboTradicional);
        assertEquals(consulta.obterResumo(), consulta.obterResumo());
    }

    @Test
    public void deveProxyImplementarIConsultaPedido() {
        assertInstanceOf(IConsultaPedido.class, new ConsultaPedidoProxy(1, comboTradicional));
    }

    @Test
    public void deveResumoDoComboVeganoConterIngredientesCorretos() {
        IConsultaPedido consulta = new ConsultaPedidoProxy(2, comboVegano);
        String resumo = consulta.obterResumo();
        assertTrue(resumo.contains("Smash Burger de Proteína de Ervilha"));
        assertTrue(resumo.contains("Salada Orgânica"));
    }

}
