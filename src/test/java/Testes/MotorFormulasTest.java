package Testes;

import hamburgueria.Combo;
import hamburgueria.Combos.FabricaComboTradicional;
import hamburgueria.Combos.FabricaComboVegano;
import hamburgueria.EstrategiaDeDesconto;
import hamburgueria.MotorDeFormulas.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MotorFormulasTest {

    @Test
    public void deveVariavelPrecoRetornarOProprioPreco() {
        VariavelPreco var = new VariavelPreco();
        assertEquals(16.0, var.interpretar(16.0), 0.001);
    }

    @Test
    public void deveValorConstanteIgnorarPrecoDoContexto() {
        ValorConstante cinco = new ValorConstante(5.0);
        assertEquals(5.0, cinco.interpretar(999.0), 0.001);
        assertEquals(5.0, cinco.interpretar(0.0),   0.001);
    }

    @Test
    public void deveOperacaoMultiplicacaoAplicarPercentual() {
        OperacaoMultiplicacao expr = new OperacaoMultiplicacao(new VariavelPreco(), new ValorConstante(0.8));
        assertEquals(12.8, expr.interpretar(16.0), 0.001);
    }

    @Test
    public void deveOperacaoSubtracaoAplicarDescontoFixo() {
        OperacaoSubtracao expr = new OperacaoSubtracao(new VariavelPreco(), new ValorConstante(5.0));
        assertEquals(11.0, expr.interpretar(16.0), 0.001);
    }

    @Test
    public void deveExpressaoCompostaCombinarOperacoes() {
        OperacaoMultiplicacao descPct = new OperacaoMultiplicacao(new VariavelPreco(), new ValorConstante(0.9));
        OperacaoSubtracao expr = new OperacaoSubtracao(descPct, new ValorConstante(2.0));
        assertEquals(12.4, expr.interpretar(16.0), 0.001);
    }

    @Test
    public void deveInterpretarVariavelPrecoIsolada() {
        InterpretadorFormulaPreco interp = new InterpretadorFormulaPreco("preco");
        assertEquals(17.0, interp.interpretar(17.0), 0.001);
    }

    @Test
    public void deveInterpretarNumeroConstanteIsolado() {
        InterpretadorFormulaPreco interp = new InterpretadorFormulaPreco("10.0");
        assertEquals(10.0, interp.interpretar(999.0), 0.001);
    }

    @Test
    public void deveInterpretarDescontoPorcentagem20PorCento() {
        InterpretadorFormulaPreco interp = new InterpretadorFormulaPreco("preco * 0.8");
        assertEquals(12.8, interp.interpretar(16.0), 0.001);
    }

    @Test
    public void deveInterpretarDescontoFixoEmReais() {
        InterpretadorFormulaPreco interp = new InterpretadorFormulaPreco("preco - 3.0");
        assertEquals(13.0, interp.interpretar(16.0), 0.001);
    }

    @Test
    public void deveInterpretarDescontoCombinadoPercentualMaisFixo() {
        InterpretadorFormulaPreco interp = new InterpretadorFormulaPreco("preco * 0.9 - 2.0");
        assertEquals(12.4, interp.interpretar(16.0), 0.001);
    }

    @Test
    public void deveInterpretarAdicaoDeAcrescimo() {
        InterpretadorFormulaPreco interp = new InterpretadorFormulaPreco("preco + 5.0");
        assertEquals(21.0, interp.interpretar(16.0), 0.001);
    }

    @Test
    public void deveInterpretarDivisao() {
        InterpretadorFormulaPreco interp = new InterpretadorFormulaPreco("preco / 2");
        assertEquals(8.0, interp.interpretar(16.0), 0.001);
    }

    @Test
    public void deveInterpretarFormulaComTresOperacoes() {
        InterpretadorFormulaPreco interp = new InterpretadorFormulaPreco("preco * 0.8 - 2.0 + 1.0");
        assertEquals(11.8, interp.interpretar(16.0), 0.001);
    }

    @Test
    public void deveLancarExcecaoParaFormulaVazia() {
        assertThrows(IllegalArgumentException.class, () -> new InterpretadorFormulaPreco(""));
    }

    @Test
    public void deveLancarExcecaoParaOperadorSemOperandoDireita() {
        assertThrows(IllegalArgumentException.class, () -> new InterpretadorFormulaPreco("preco *"));
    }

    @Test
    public void deveLancarExcecaoParaTokenInvalido() {
        assertThrows(IllegalArgumentException.class, () -> new InterpretadorFormulaPreco("preco $ 0.8"));
    }

    @Test
    public void deveLancarExcecaoParaDivisaoPorZero() {
        InterpretadorFormulaPreco interp = new InterpretadorFormulaPreco("preco / 0");
        assertThrows(ArithmeticException.class, () -> interp.interpretar(16.0));
    }

    @Test
    public void deveRegraDePrecAplicarFormulaCorretamente() {
        RegraDePreco regra = new RegraDePreco("preco * 0.75");
        assertEquals(12.0, regra.aplicar(16.0), 0.001);
    }

    @Test
    public void deveRegraDePrecNuncaRetornarNegativo() {
        RegraDePreco regra = new RegraDePreco("preco - 999.0");
        assertEquals(0.0, regra.aplicar(16.0), 0.001);
    }

    @Test
    public void deveRegraDePrecSerAlteradaEmRuntime() {
        RegraDePreco regra = new RegraDePreco("preco * 0.8");
        assertEquals(12.8, regra.aplicar(16.0), 0.001);

        regra.setFormula("preco * 0.6");
        assertEquals(9.6, regra.aplicar(16.0), 0.001);
    }

    @Test
    public void deveEstrategiaInterpretadaImplementarEstrategiaDeDesconto() {
        RegraDePreco regra = new RegraDePreco("preco * 0.8");
        assertInstanceOf(EstrategiaDeDesconto.class, new EstrategiaInterpretada(regra));
    }

    @Test
    public void deveEstrategiaInterpretadaAplicarDescontoNoComboTradicional() {
        Combo combo = new Combo(new FabricaComboTradicional());
        combo.setEstrategiaDeDesconto(new EstrategiaInterpretada(new RegraDePreco("preco * 0.85")));

        assertEquals(13.6, combo.getPrecoFinal(), 0.001);
    }

    @Test
    public void deveEstrategiaInterpretadaAplicarDescontoNoComboVegano() {
        Combo combo = new Combo(new FabricaComboVegano());
        combo.setEstrategiaDeDesconto(new EstrategiaInterpretada(new RegraDePreco("preco - 4.0")));

        assertEquals(13.0, combo.getPrecoFinal(), 0.001);
    }

    @Test
    public void deveDescricaoDaEstrategiaConterAFormula() {
        RegraDePreco regra = new RegraDePreco("preco * 0.9 - 2.0");
        EstrategiaInterpretada estrategia = new EstrategiaInterpretada(regra);

        assertTrue(estrategia.getDescricao().contains("preco * 0.9 - 2.0"));
    }

    @Test
    public void deveEstrategiaInterpretadaSerSubstituivelEmRuntime() {
        Combo combo = new Combo(new FabricaComboTradicional());

        combo.setEstrategiaDeDesconto(new EstrategiaInterpretada(new RegraDePreco("preco * 0.5")));
        assertEquals(8.0, combo.getPrecoFinal(), 0.001);

        combo.setEstrategiaDeDesconto(new EstrategiaInterpretada(new RegraDePreco("preco - 1.0")));
        assertEquals(15.0, combo.getPrecoFinal(), 0.001);
    }

    @Test
    public void deveFormulaCombinadaFuncionarNoComboPorMeioDoInterpreter() {
        Combo combo = new Combo(new FabricaComboTradicional());
        combo.setEstrategiaDeDesconto(new EstrategiaInterpretada(new RegraDePreco("preco * 0.75 - 2.0")));

        assertEquals(10.0, combo.getPrecoFinal(), 0.001);
    }

}
