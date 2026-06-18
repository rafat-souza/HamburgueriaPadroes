package Testes;

import hamburgueria.*;
import hamburgueria.Combos.FabricaComboTradicional;
import hamburgueria.Combos.FabricaComboVegano;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DescontoVIPTest {

    private ClienteVIP cliente;

    @BeforeEach
    public void setUp() {
        cliente = new ClienteVIP();
    }

    @Test
    public void deveCategoriaPREMIUMGerarFator030() {
        cliente.setCategoria("PREMIUM");
        assertEquals(0.30f, cliente.getFatorDesconto(), 0.001f);
    }

    @Test
    public void deveCategoriaOUROGerarFator020() {
        cliente.setCategoria("OURO");
        assertEquals(0.20f, cliente.getFatorDesconto(), 0.001f);
    }

    @Test
    public void deveCategoriaPRATAGerarFator010() {
        cliente.setCategoria("PRATA");
        assertEquals(0.10f, cliente.getFatorDesconto(), 0.001f);
    }

    @Test
    public void deveCategoriaBRONZEGerarFator005() {
        cliente.setCategoria("BRONZE");
        assertEquals(0.05f, cliente.getFatorDesconto(), 0.001f);
    }

    @Test
    public void deveCategoriaDesconhecidaGerarFator005() {
        cliente.setCategoria("OUTRO");
        assertEquals(0.05f, cliente.getFatorDesconto(), 0.001f);
    }

    @Test
    public void deveFator030RecuperarCategoriaPREMIUM() {
        ICategoriaCliente cat = new CategoriaClienteVIP();
        DescontoLegadoAdapter adapter = new DescontoLegadoAdapter(cat);
        adapter.setFatorDesconto(0.30f);

        assertEquals("PREMIUM", adapter.recuperarCategoria());
    }

    @Test
    public void deveFator020RecuperarCategoriaOURO() {
        ICategoriaCliente cat = new CategoriaClienteVIP();
        DescontoLegadoAdapter adapter = new DescontoLegadoAdapter(cat);
        adapter.setFatorDesconto(0.20f);

        assertEquals("OURO", adapter.recuperarCategoria());
    }

    @Test
    public void deveFator010RecuperarCategoriaPRATA() {
        ICategoriaCliente cat = new CategoriaClienteVIP();
        DescontoLegadoAdapter adapter = new DescontoLegadoAdapter(cat);
        adapter.setFatorDesconto(0.10f);

        assertEquals("PRATA", adapter.recuperarCategoria());
    }

    @Test
    public void deveFator004RecuperarCategoriaBRONZE() {
        ICategoriaCliente cat = new CategoriaClienteVIP();
        DescontoLegadoAdapter adapter = new DescontoLegadoAdapter(cat);
        adapter.setFatorDesconto(0.04f);

        assertEquals("BRONZE", adapter.recuperarCategoria());
    }

    @Test
    public void deveCategoriaIdaEVoltaPREMIUM() {
        cliente.setCategoria("PREMIUM");
        assertEquals("PREMIUM", cliente.getCategoria());
    }

    @Test
    public void deveCategoriaIdaEVoltaOURO() {
        cliente.setCategoria("OURO");
        assertEquals("OURO", cliente.getCategoria());
    }

    @Test
    public void deveCategoriaIdaEVoltaPRATA() {
        cliente.setCategoria("PRATA");
        assertEquals("PRATA", cliente.getCategoria());
    }

    @Test
    public void deveEstrategiaVIPPREMIUMAplicar30PorCentoNoComboTradicional() {
        cliente.setCategoria("PREMIUM");
        EstrategiaDeDesconto estrategia = cliente.getEstrategiaDeDesconto();

        Combo combo = new Combo(new FabricaComboTradicional());
        combo.setEstrategiaDeDesconto(estrategia);

        assertEquals(11.2, combo.getPrecoFinal(), 0.001);
    }

    @Test
    public void deveEstrategiaVIPOUROAplicar20PorCentoNoComboVegano() {
        cliente.setCategoria("OURO");
        EstrategiaDeDesconto estrategia = cliente.getEstrategiaDeDesconto();

        Combo combo = new Combo(new FabricaComboVegano());
        combo.setEstrategiaDeDesconto(estrategia);

        assertEquals(13.6, combo.getPrecoFinal(), 0.001);
    }

    @Test
    public void deveEstrategiaVIPPRATAAplicar10PorCentoNoCombo() {
        cliente.setCategoria("PRATA");
        EstrategiaDeDesconto estrategia = cliente.getEstrategiaDeDesconto();

        Combo combo = new Combo(new FabricaComboTradicional());
        combo.setEstrategiaDeDesconto(estrategia);

        assertEquals(14.4, combo.getPrecoFinal(), 0.001);
    }

    @Test
    public void deveDescricaoDaEstrategiaConterCategoriaEPercentual() {
        cliente.setCategoria("OURO");
        String descricao = cliente.getEstrategiaDeDesconto().getDescricao();

        assertTrue(descricao.contains("OURO"));
        assertTrue(descricao.contains("20"));
    }

    @Test
    public void deveAdapterSerInstanceOfEstrategiaDeDesconto() {
        ICategoriaCliente cat = new CategoriaClienteVIP();
        DescontoLegadoAdapter adapter = new DescontoLegadoAdapter(cat);

        assertInstanceOf(EstrategiaDeDesconto.class, adapter);
    }

    @Test
    public void deveAdapterSubstituirQualquerOutraEstrategiaDeDesconto() {
        cliente.setCategoria("PREMIUM");
        Combo combo = new Combo(new FabricaComboTradicional());

        combo.setEstrategiaDeDesconto(cliente.getEstrategiaDeDesconto());
        double precoComVIP = combo.getPrecoFinal();

        combo.setEstrategiaDeDesconto(new hamburgueria.Descontos.SemDesconto());
        double precoSemDesconto = combo.getPrecoFinal();

        assertTrue(precoComVIP < precoSemDesconto);
    }

}
