package Testes;

import hamburgueria.Combo;
import hamburgueria.Combos.FabricaComboTradicional;
import hamburgueria.Combos.FabricaComboVegano;
import hamburgueria.Descontos.DescontoPorcentagem;
import hamburgueria.IngredientesInfo.DadosIngrediente;
import hamburgueria.IngredientesInfo.DadosIngredienteFactory;
import hamburgueria.NotasFiscais.ItemNotaFiscal;
import hamburgueria.NotasFiscais.NotaFiscal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IngredientesCompartilhadosTest {

    @BeforeEach
    public void limparCacheEntreTesters() {
        DadosIngredienteFactory.limparCache();
    }

    @Test
    public void deveCriarNovoDadosIngredienteQuandoNaoExisteNocache() {
        DadosIngrediente dados = DadosIngredienteFactory.getDados(
                "Queijo Cheddar", "Extra", "80 kcal, 5g prot");

        assertNotNull(dados);
        assertEquals("Queijo Cheddar", dados.getNome());
        assertEquals(1, DadosIngredienteFactory.getTotalDadosCriados());
    }

    @Test
    public void deveReutilizarMesmaInstanciaParaMesmoNome() {
        DadosIngrediente d1 = DadosIngredienteFactory.getDados(
                "Batata Frita Média", "Acompanhamento", "320 kcal, 4g prot");
        DadosIngrediente d2 = DadosIngredienteFactory.getDados(
                "Batata Frita Média", "Acompanhamento", "320 kcal, 4g prot");

        assertSame(d1, d2);
    }

    @Test
    public void deveCriarInstanciasDiferentesParaNomesDiferentes() {
        DadosIngrediente d1 = DadosIngredienteFactory.getDados(
                "Refrigerante Cola", "Bebida", "150 kcal, 0g prot");
        DadosIngrediente d2 = DadosIngredienteFactory.getDados(
                "Suco de Laranja Natural", "Bebida", "90 kcal, 1g prot");

        assertNotSame(d1, d2);
        assertEquals(2, DadosIngredienteFactory.getTotalDadosCriados());
    }

    @Test
    public void deveCacheNaoCresCerQuandoMesmosIngredientesSaoSolicitadosVarizasVezes() {
        DadosIngredienteFactory.getDados("Carne Bovina", "Lanche", "450 kcal");
        DadosIngredienteFactory.getDados("Batata Frita Média", "Acompanhamento", "320 kcal");

        DadosIngredienteFactory.getDados("Carne Bovina", "Lanche", "450 kcal");
        DadosIngredienteFactory.getDados("Batata Frita Média", "Acompanhamento", "320 kcal");
        DadosIngredienteFactory.getDados("Carne Bovina", "Lanche", "450 kcal");
        DadosIngredienteFactory.getDados("Batata Frita Média", "Acompanhamento", "320 kcal");

        assertEquals(2, DadosIngredienteFactory.getTotalDadosCriados());
    }

    @Test
    public void deveCacheNaoCrescerComMuitasPedidosTradicionalEVegano() {
        new NotaFiscal(1, new Combo(new FabricaComboTradicional()));
        new NotaFiscal(2, new Combo(new FabricaComboTradicional()));
        new NotaFiscal(3, new Combo(new FabricaComboTradicional()));

        int flyweightsAposTradicional = DadosIngredienteFactory.getTotalDadosCriados();

        new NotaFiscal(4, new Combo(new FabricaComboVegano()));
        new NotaFiscal(5, new Combo(new FabricaComboVegano()));
        new NotaFiscal(6, new Combo(new FabricaComboVegano()));

        int flyweightsAposVegano = DadosIngredienteFactory.getTotalDadosCriados();

        assertTrue(flyweightsAposTradicional <= 3,
                "Tradicional usa no máximo 3 flyweights distintos");
        assertTrue(flyweightsAposVegano <= 6,
                "Vegano adiciona até 3 flyweights novos");
    }

    @Test
    public void deveDadosIngredientePreservarCamposIntrinsicos() {
        DadosIngrediente dados = DadosIngredienteFactory.getDados(
                "Salada Orgânica", "Acompanhamento", "45 kcal, 2g prot");

        assertEquals("Salada Orgânica", dados.getNome());
        assertEquals("Acompanhamento", dados.getCategoria());
        assertEquals("45 kcal, 2g prot", dados.getInfoNutricional());
    }

    @Test
    public void deveItemNotaFiscalCalcularSubtotalCorretamente() {
        DadosIngrediente dados = DadosIngredienteFactory.getDados(
                "BaconVegano", "Extra", "120 kcal, 8g prot");

        ItemNotaFiscal item = new ItemNotaFiscal(dados, 2, 4.0);

        assertEquals(8.0, item.getSubtotal(), 0.001);
    }

    @Test
    public void deveDoisItemsCompartilharemMesmoFlyweightMasTeremPrecosDistintos() {
        DadosIngrediente shared = DadosIngredienteFactory.getDados(
                "Queijo Cheddar", "Extra", "80 kcal, 5g prot");

        ItemNotaFiscal item1 = new ItemNotaFiscal(shared, 1, 3.0);
        ItemNotaFiscal item2 = new ItemNotaFiscal(shared, 3, 3.0);

        assertSame(item1.getDados(), item2.getDados());
        assertNotEquals(item1.getSubtotal(), item2.getSubtotal());
        assertEquals(3.0, item1.getSubtotal(), 0.001);
        assertEquals(9.0, item2.getSubtotal(), 0.001);
    }

    @Test
    public void deveLinhaDoItemConterNomeEInfoNutricional() {
        DadosIngrediente dados = DadosIngredienteFactory.getDados(
                "Hambúrguer Clássico de Carne Bovina", "Lanche", "450 kcal, 24g prot");

        ItemNotaFiscal item = new ItemNotaFiscal(dados, 1, 16.0);
        String linha = item.obterLinha();

        assertTrue(linha.contains("Hambúrguer Clássico de Carne Bovina"));
        assertTrue(linha.contains("450 kcal"));
        assertTrue(linha.contains("16,00") || linha.contains("16.00"));
    }

    @Test
    public void deveCriarNotaFiscalDoComboTradicionalComTresItens() {
        NotaFiscal nota = new NotaFiscal(1, new Combo(new FabricaComboTradicional()));

        assertEquals(3, nota.getItens().size());
    }

    @Test
    public void deveTotalBrutoNaNotaRefletirPrecoDoHamburguer() {
        Combo combo = new Combo(new FabricaComboTradicional());
        NotaFiscal nota = new NotaFiscal(1, combo);

        assertEquals(combo.getHamburguer().getPreco(), nota.getTotalBruto(), 0.001);
    }

    @Test
    public void deveNotaFiscalConterDescricaoDosIngredientes() {
        NotaFiscal nota = new NotaFiscal(42, new Combo(new FabricaComboVegano()));
        String impressao = nota.imprimir();

        assertTrue(impressao.contains("Smash Burger de Proteína de Ervilha"));
        assertTrue(impressao.contains("Salada Orgânica"));
        assertTrue(impressao.contains("Suco de Laranja Natural"));
        assertTrue(impressao.contains("Pedido #42"));
    }

    @Test
    public void deveAdicionarItemAvulsoAumentarTotalEContagemDeItens() {
        Combo combo = new Combo(new FabricaComboTradicional());
        NotaFiscal nota = new NotaFiscal(5, combo);

        nota.adicionarItemAvulso("Queijo Cheddar", "Extra", "80 kcal, 5g prot", 2, 3.0);

        assertEquals(4, nota.getItens().size());
        assertEquals(combo.getHamburguer().getPreco() + 6.0, nota.getTotalBruto(), 0.001);
    }

    @Test
    public void deveItemAvulsoReutilizarFlyweightJaExistente() {
        new NotaFiscal(1, new Combo(new FabricaComboTradicional()));
        int cacheAntes = DadosIngredienteFactory.getTotalDadosCriados();

        NotaFiscal nota2 = new NotaFiscal(2, new Combo(new FabricaComboTradicional()));
        nota2.adicionarItemAvulso("Batata Frita Média", "Acompanhamento", "320 kcal, 4g prot",
                1, 5.0);

        assertEquals(cacheAntes, DadosIngredienteFactory.getTotalDadosCriados());
    }

    @Test
    public void deveFlyweightFuncionarComComboDecorado() {
        Combo combo = new Combo(new FabricaComboTradicional());

        hamburgueria.Hamburguer decorado = new hamburgueria.Extras.Queijo(
                        new hamburgueria.Extras.CebolaCaramelizada(combo.getHamburguer()));
        combo.adicionarIngrediente(decorado);
        combo.setEstrategiaDeDesconto(new DescontoPorcentagem(10));

        NotaFiscal nota = new NotaFiscal(99, combo);
        String impressao = nota.imprimir();

        assertTrue(impressao.contains("Queijo Cheddar"));
        assertTrue(impressao.contains("Cebola Caramelizada"));
    }

}
