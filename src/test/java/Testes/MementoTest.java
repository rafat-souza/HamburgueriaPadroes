package Testes;

import hamburgueria.Combo;
import hamburgueria.ComboEstado;
import hamburgueria.Combos.FabricaComboTradicional;
import hamburgueria.Combos.FabricaComboVegano;
import hamburgueria.Descontos.DescontoPorcentagem;
import hamburgueria.Extras.CebolaCaramelizada;
import hamburgueria.Extras.Queijo;
import hamburgueria.Hamburguer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MementoTest {

    private Combo combo;

    @BeforeEach
    public void setUp() {
        combo = new Combo(new FabricaComboTradicional());
    }

    @Test
    public void deveSalvarEstadoAdicionarMementoAoHistorico() {
        assertTrue(combo.getEstados().isEmpty());

        combo.salvarEstado();

        assertEquals(1, combo.getEstados().size());
    }

    @Test
    public void deveSalvarMultiplosEstadosAcumularNoHistorico() {
        combo.salvarEstado();

        Hamburguer comQueijo = new Queijo(combo.getHamburguer());
        combo.adicionarIngrediente(comQueijo);
        combo.salvarEstado();

        combo.setEstrategiaDeDesconto(new DescontoPorcentagem(10));
        combo.salvarEstado();

        assertEquals(3, combo.getEstados().size());
    }

    @Test
    public void deveMementoCapturarDescricaoDoHamburguerNoMomentoDoSalvamento() {
        combo.salvarEstado();

        String descricaoOriginal = combo.getHamburguer().getDescricao();
        ComboEstado estado = combo.getEstados().get(0);

        assertEquals(descricaoOriginal, estado.getHamburguer().getDescricao());
    }

    @Test
    public void deveMementoExibirDescricaoDoEstadoViaInterface() {
        combo.salvarEstado();

        String descricao = combo.getEstados().get(0).getDescricaoEstado();

        assertTrue(descricao.contains("Hambúrguer Clássico de Carne Bovina"));
        assertTrue(descricao.contains("Batata Frita Média"));
        assertTrue(descricao.contains("Refrigerante Cola"));
        assertTrue(descricao.contains("Sem desconto"));
    }

    @Test
    public void deveRestaurarEstadoInicialAposAdicionarExtras() {
        combo.salvarEstado();  

        Hamburguer decorado = new Queijo(new CebolaCaramelizada(combo.getHamburguer()));
        combo.adicionarIngrediente(decorado);
        combo.salvarEstado();

        combo.restaurarEstado(0);

        assertFalse(combo.getHamburguer().getDescricao().contains("Queijo"));
        assertFalse(combo.getHamburguer().getDescricao().contains("Cebola"));
        assertEquals(16.0, combo.getHamburguer().getPreco());
    }

    @Test
    public void deveRestaurarDescontoAnteriorAposAlteracao() {
        combo.salvarEstado();

        combo.setEstrategiaDeDesconto(new DescontoPorcentagem(30));
        combo.salvarEstado();

        combo.restaurarEstado(0);

        assertEquals("Sem desconto", combo.getDescricaoDesconto());
        assertEquals(16.0, combo.getPrecoFinal());
    }

    @Test
    public void deveRestaurarEstadoIntermediarioCorretamente() {
        combo.salvarEstado();

        combo.setEstrategiaDeDesconto(new DescontoPorcentagem(10));
        combo.salvarEstado();

        combo.setEstrategiaDeDesconto(new DescontoPorcentagem(50));
        combo.salvarEstado();

        combo.restaurarEstado(1);

        assertEquals(14.4, combo.getPrecoFinal(), 0.001);
    }

    @Test
    public void deveHistoricoNaoSerModificadoAposRestauracao() {
        combo.salvarEstado();
        combo.setEstrategiaDeDesconto(new DescontoPorcentagem(20));
        combo.salvarEstado();

        combo.restaurarEstado(0);

        assertEquals(2, combo.getEstados().size());
    }

    @Test
    public void deveLancarExcecaoAoRestaurarIndiceNegativo() {
        combo.salvarEstado();

        assertThrows(IllegalArgumentException.class,
                () -> combo.restaurarEstado(-1));
    }

    @Test
    public void deveLancarExcecaoAoRestaurarIndiceMaiorQueHistorico() {
        combo.salvarEstado();

        assertThrows(IllegalArgumentException.class,
                () -> combo.restaurarEstado(1));
    }

    @Test
    public void deveLancarExcecaoQuandoHistoricoEstaVazio() {
        assertThrows(IllegalArgumentException.class,
                () -> combo.restaurarEstado(0));
    }

    @Test
    public void deveGetEstadosRetornarListaNaoModificavel() {
        combo.salvarEstado();

        assertThrows(UnsupportedOperationException.class,
                () -> combo.getEstados().clear());
    }

    @Test
    public void deveTodosOsEstadosSalvosImplementaremAInterface() {
        combo.salvarEstado();
        combo.setEstrategiaDeDesconto(new DescontoPorcentagem(10));
        combo.salvarEstado();

        assertInstanceOf(ComboEstado.class, combo.getEstados().get(0));
        assertNotNull(combo.getEstados().get(0).getDescricaoEstado());

        assertInstanceOf(ComboEstado.class, combo.getEstados().get(1));
        assertNotNull(combo.getEstados().get(1).getDescricaoEstado());
    }

    @Test
    public void deveMementoFuncionarComComboVegano() {
        Combo vegano = new Combo(new FabricaComboVegano());
        vegano.salvarEstado();

        Hamburguer decorado = new Queijo(vegano.getHamburguer());
        vegano.adicionarIngrediente(decorado);
        vegano.salvarEstado();

        vegano.restaurarEstado(0);

        assertFalse(vegano.getHamburguer().getDescricao().contains("Queijo"));
        assertEquals("Smash Burger de Proteína de Ervilha",
                vegano.getHamburguer().getDescricao());
    }

}
