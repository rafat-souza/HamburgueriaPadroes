package Testes;

import hamburgueria.Combo;
import hamburgueria.Combos.FabricaComboTradicional;
import hamburgueria.Combos.FabricaComboVegano;
import hamburgueria.Descontos.DescontoPorcentagem;
import hamburgueria.Descontos.SemDesconto;
import hamburgueria.EstilosDeBurger.BurgerClassico;
import hamburgueria.EstilosDeBurger.BurgerGourmet;
import hamburgueria.EstilosDeBurger.SmashBurger;
import hamburgueria.Extras.BaconVegano;
import hamburgueria.Extras.CebolaCaramelizada;
import hamburgueria.Extras.Queijo;
import hamburgueria.Hamburguer;
import hamburgueria.HamburgueriaVisitor;
import hamburgueria.Proteinas.Carne;
import hamburgueria.Proteinas.Planta;
import hamburgueria.Visitantes.ExportadorJsonVisitor;
import hamburgueria.Visitantes.ResumoVisitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class VisitorTest {

    private ResumoVisitor resumo;
    private ExportadorJsonVisitor json;

    @BeforeEach
    public void setUp() {
        resumo = new ResumoVisitor();
        json   = new ExportadorJsonVisitor();
    }

    @Test
    public void deveResumoVisitorExibirDescricaoDoHamburguer() {
        Hamburguer burger = new BurgerClassico(new Carne());
        String resultado = burger.aceitar(resumo);

        assertTrue(resultado.contains("Hambúrguer Clássico de Carne Bovina"));
    }

    @Test
    public void deveResumoVisitorExibirPrecoDoHamburguer() {
        Hamburguer burger = new BurgerClassico(new Carne());
        String resultado = burger.aceitar(resumo);

        assertTrue(resultado.contains("16,00") || resultado.contains("16.00"));
    }

    @Test
    public void deveResumoVisitorFuncionarComHamburguerDecorado() {
        Hamburguer burger = new Queijo(new CebolaCaramelizada(new SmashBurger(new Planta())));
        String resultado = burger.aceitar(resumo);

        assertTrue(resultado.contains("Queijo Cheddar"));
        assertTrue(resultado.contains("Cebola Caramelizada"));
    }

    @Test
    public void deveResumoVisitorExibirTodosOsElementosDoComboTradicional() {
        Combo combo = new Combo(new FabricaComboTradicional());
        String resultado = combo.aceitar(resumo);

        assertTrue(resultado.contains("Hambúrguer Clássico de Carne Bovina"));
        assertTrue(resultado.contains("Batata Frita Média"));
        assertTrue(resultado.contains("Refrigerante Cola"));
    }

    @Test
    public void deveResumoVisitorExibirTodosOsElementosDoComboVegano() {
        Combo combo = new Combo(new FabricaComboVegano());
        String resultado = combo.aceitar(resumo);

        assertTrue(resultado.contains("Smash Burger de Proteína de Ervilha"));
        assertTrue(resultado.contains("Salada Orgânica"));
        assertTrue(resultado.contains("Suco de Laranja Natural"));
    }

    @Test
    public void deveResumoVisitorExibirDescontoAplicado() {
        Combo combo = new Combo(new FabricaComboTradicional());
        combo.setEstrategiaDeDesconto(new DescontoPorcentagem(10));
        String resultado = combo.aceitar(resumo);

        assertTrue(resultado.contains("10%") || resultado.contains("Desconto de 10"));
        assertTrue(resultado.contains("14,40") || resultado.contains("14.40"));
    }

    @Test
    public void deveResumoVisitorExibirSemDescontoQuandoNaoAplicado() {
        Combo combo = new Combo(new FabricaComboTradicional());
        combo.setEstrategiaDeDesconto(new SemDesconto());
        String resultado = combo.aceitar(resumo);

        assertTrue(resultado.contains("Sem desconto"));
        assertTrue(resultado.contains("16,00") || resultado.contains("16.00"));
    }

    @Test
    public void deveJsonVisitorGerarChaveTipoHamburguer() {
        Hamburguer burger = new BurgerClassico(new Carne());
        String resultado = burger.aceitar(json);

        assertTrue(resultado.contains("\"tipo\":\"hamburguer\""));
    }

    @Test
    public void deveJsonVisitorGerarDescricaoEPrecoDoHamburguer() {
        Hamburguer burger = new SmashBurger(new Planta());
        String resultado = burger.aceitar(json);

        assertTrue(resultado.contains("Smash Burger de Proteína de Ervilha"));
        assertTrue(resultado.contains("17"));
    }

    @Test
    public void deveJsonVisitorFuncionarComDecoratorEncadeado() {
        Hamburguer burger = new BaconVegano(new Queijo(new BurgerGourmet(new Carne())));
        String resultado = burger.aceitar(json);

        assertTrue(resultado.contains("\"tipo\":\"hamburguer\""));
        assertTrue(resultado.contains("Bacon de Soja"));
        assertTrue(resultado.contains("Queijo Cheddar"));
    }

    @Test
    public void deveJsonVisitorGerarJsonComTodosOsCamposDoCombo() {
        Combo combo = new Combo(new FabricaComboTradicional());
        String resultado = combo.aceitar(json);

        assertTrue(resultado.contains("\"hamburguer\""));
        assertTrue(resultado.contains("\"acompanhamento\""));
        assertTrue(resultado.contains("\"bebida\""));
        assertTrue(resultado.contains("\"desconto\""));
        assertTrue(resultado.contains("\"totalComDesconto\""));
    }

    @Test
    public void deveJsonVisitorGerarJsonDoComboVegano() {
        Combo combo = new Combo(new FabricaComboVegano());
        String resultado = combo.aceitar(json);

        assertTrue(resultado.contains("Salada Orgânica"));
        assertTrue(resultado.contains("Suco de Laranja Natural"));
        assertTrue(resultado.contains("Proteína de Ervilha"));
    }

    @Test
    public void deveJsonVisitorRefletirDescontoNoTotalDoCombo() {
        Combo combo = new Combo(new FabricaComboTradicional());
        combo.setEstrategiaDeDesconto(new DescontoPorcentagem(50));
        String resultado = combo.aceitar(json);

        assertTrue(resultado.contains("8,00") || resultado.contains("8.00"));
    }

    @Test
    public void deveMesmoComboProduzirFormatsCompletamenteDiferentes() {
        Combo combo = new Combo(new FabricaComboTradicional());

        String resultadoResumo = combo.aceitar(resumo);
        String resultadoJson   = combo.aceitar(json);

        assertNotEquals(resultadoResumo, resultadoJson);
        assertTrue(resultadoResumo.contains("RESUMO"));
        assertTrue(resultadoJson.startsWith("{") && resultadoJson.endsWith("}"));
    }

    @Test
    public void deveVisitorInterfaceAceitarQualquerImplementacao() {
        Combo combo = new Combo(new FabricaComboVegano());

        String resultadoResumo = combo.aceitar(resumo);
        assertNotNull(resultadoResumo);
        assertFalse(resultadoResumo.isBlank());

        String resultadoJson = combo.aceitar(json);
        assertNotNull(resultadoJson);
        assertFalse(resultadoJson.isBlank());
    }

}
