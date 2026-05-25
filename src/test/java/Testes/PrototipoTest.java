package Testes;

import hamburgueria.Promocao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PrototipoTest {

    private Promocao promoVerao;

    @BeforeEach
    public void setUp() {
        promoVerao = new Promocao("Promo Verão", "Desconto de verão", 20.0, "31/01/2026");
        promoVerao.adicionarComboElegivel("Tradicional");
        promoVerao.adicionarComboElegivel("Vegano");
    }

    @Test
    public void deveCloneNomeIgualAoOriginal() throws CloneNotSupportedException {
        Promocao clone = promoVerao.clone();
        assertEquals(promoVerao.getNome(), clone.getNome());
    }

    @Test
    public void deveCloneDescontoIgualAoOriginal() throws CloneNotSupportedException {
        Promocao clone = promoVerao.clone();
        assertEquals(promoVerao.getPercentualDesconto(), clone.getPercentualDesconto());
    }

    @Test
    public void deveCloneCombosElegiveisIguaisAoOriginal() throws CloneNotSupportedException {
        Promocao clone = promoVerao.clone();
        assertEquals(promoVerao.getCombosElegiveis(), clone.getCombosElegiveis());
    }

    @Test
    public void deveAdicionarComboNoCloneNaoAfetarOriginal() throws CloneNotSupportedException {
        Promocao clone = promoVerao.clone();
        clone.adicionarComboElegivel("Gourmet");

        assertFalse(promoVerao.getCombosElegiveis().contains("Gourmet"));
        assertTrue(clone.getCombosElegiveis().contains("Gourmet"));
    }

    @Test
    public void deveAdicionarComboNoOriginalNaoAfetarClone() throws CloneNotSupportedException {
        Promocao clone = promoVerao.clone();
        promoVerao.adicionarComboElegivel("Gourmet");

        assertFalse(clone.getCombosElegiveis().contains("Gourmet"));
    }

    @Test
    public void deveAlterarValidadeNoCloneNaoAfetarOriginal() throws CloneNotSupportedException {
        Promocao promoInverno = promoVerao.clone();
        promoInverno.setNome("Promo Inverno");
        promoInverno.setValidade("31/07/2026");
        promoInverno.setPercentualDesconto(15.0);

        assertEquals("Promo Verão", promoVerao.getNome());
        assertEquals("31/01/2026", promoVerao.getValidade());
        assertEquals(20.0, promoVerao.getPercentualDesconto());
    }

    @Test
    public void deveCloneSerObjetoDistintoDoOriginal() throws CloneNotSupportedException {
        Promocao clone = promoVerao.clone();
        assertNotSame(promoVerao, clone);
    }

    @Test
    public void deveListaCombosDoCloneSerObjetoDistintoDaListaOriginal()
            throws CloneNotSupportedException {
        Promocao clone = promoVerao.clone();
        assertNotSame(promoVerao.getCombosElegiveis(), clone.getCombosElegiveis());
    }

}
