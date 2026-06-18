package Testes;

import hamburgueria.Acompanhamentos.BatataFrita;
import hamburgueria.Acompanhamentos.Salada;
import hamburgueria.Bebidas.Refrigerante;
import hamburgueria.Bebidas.SucoNatural;
import hamburgueria.Cardapio.*;
import hamburgueria.EstilosDeBurger.BurgerClassico;
import hamburgueria.EstilosDeBurger.BurgerGourmet;
import hamburgueria.EstilosDeBurger.SmashBurger;
import hamburgueria.Extras.BaconVegano;
import hamburgueria.Proteinas.Carne;
import hamburgueria.Proteinas.Planta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CardapioTest {

    private Cardapio cardapio;
    private SecaoCardapio secaoTradicionais;
    private SecaoCardapio secaoVeganos;
    private SecaoCardapio secaoAcompanhamentos;
    private SecaoCardapio secaoBebidas;

    private ItemHamburguer itemClassico;
    private ItemHamburguer itemSmashCarne;
    private ItemHamburguer itemGourmet;
    private ItemHamburguer itemSmashVegano;
    private ItemHamburguer itemVeganoDecorado;

    @BeforeEach
    public void setUp() {
        itemClassico       = new ItemHamburguer(new BurgerClassico(new Carne()));
        itemSmashCarne     = new ItemHamburguer(new SmashBurger(new Carne()));
        itemGourmet        = new ItemHamburguer(new BurgerGourmet(new Carne()));
        itemSmashVegano    = new ItemHamburguer(new SmashBurger(new Planta()));

        itemVeganoDecorado = new ItemHamburguer(
                new BaconVegano(new BurgerClassico(new Planta())));

        secaoTradicionais = new SecaoCardapio("Lanches Tradicionais");
        secaoTradicionais.addItem(itemClassico);
        secaoTradicionais.addItem(itemSmashCarne);
        secaoTradicionais.addItem(itemGourmet);

        secaoVeganos = new SecaoCardapio("Lanches Veganos");
        secaoVeganos.addItem(itemSmashVegano);
        secaoVeganos.addItem(itemVeganoDecorado);

        secaoAcompanhamentos = new SecaoCardapio("Acompanhamentos");
        secaoAcompanhamentos.addItem(new ItemAcompanhamento(new BatataFrita()));
        secaoAcompanhamentos.addItem(new ItemAcompanhamento(new Salada()));

        secaoBebidas = new SecaoCardapio("Bebidas");
        secaoBebidas.addItem(new ItemBebida(new Refrigerante()));
        secaoBebidas.addItem(new ItemBebida(new SucoNatural()));

        SecaoCardapio raiz = new SecaoCardapio("Cardápio Completo");
        raiz.addItem(secaoTradicionais);
        raiz.addItem(secaoVeganos);
        raiz.addItem(secaoAcompanhamentos);
        raiz.addItem(secaoBebidas);

        cardapio = new Cardapio();
        cardapio.setRaiz(raiz);
    }

    @Test
    public void deveItemHamburguerExibirDescricaoDaClasseReal() {
        assertTrue(itemClassico.getDetalhes().contains("Hambúrguer Clássico de Carne Bovina"));
    }

    @Test
    public void deveItemHamburguerExibirPrecoCalculadoPelaClasseReal() {
        assertTrue(itemClassico.getDetalhes().contains("16,00")
                || itemClassico.getDetalhes().contains("16.00"));
    }

    @Test
    public void deveItemGourmetExibirExtrasDoHook() {
        assertTrue(itemGourmet.getDetalhes().contains("Pão Brioche Artesanal"));
    }

    @Test
    public void deveItemVeganoDecoradoExibirExtraDoDecorator() {
        assertTrue(itemVeganoDecorado.getDetalhes().contains("Bacon de Soja"));
    }

    @Test
    public void deveItemVeganoDecoradoTerPrecoAcumuladoPeloDecorator() {
        assertEquals(22.0, itemVeganoDecorado.getHamburguer().getPreco());
    }

    @Test
    public void deveItemAcompanhamentoExibirDescricaoCorreta() {
        ItemAcompanhamento item = new ItemAcompanhamento(new BatataFrita());
        assertTrue(item.getDetalhes().contains("Batata Frita Média"));
    }

    @Test
    public void deveItemBebidaExibirDescricaoCorreta() {
        ItemBebida item = new ItemBebida(new SucoNatural());
        assertTrue(item.getDetalhes().contains("Suco de Laranja Natural"));
    }

    @Test
    public void deveSecaoTradicionaisListarTodosOsLanches() {
        String detalhes = secaoTradicionais.getDetalhes();
        assertTrue(detalhes.contains("Hambúrguer Clássico de Carne Bovina"));
        assertTrue(detalhes.contains("Smash Burger de Carne Bovina"));
        assertTrue(detalhes.contains("Burger Gourmet de Carne Bovina"));
    }

    @Test
    public void deveSecaoVeganosListarLanchesVeganos() {
        String detalhes = secaoVeganos.getDetalhes();
        assertTrue(detalhes.contains("Proteína de Ervilha"));
        assertTrue(detalhes.contains("Bacon de Soja"));
    }

    @Test
    public void deveRemoverItemDaSecaoNaoExibiLo() {
        secaoBebidas.removeItem(secaoBebidas.getItens().get(0)); // remove Refrigerante
        assertFalse(secaoBebidas.getDetalhes().contains("Refrigerante Cola"));
        assertTrue(secaoBebidas.getDetalhes().contains("Suco de Laranja Natural"));
    }

    @Test
    public void deveCardapioExibirTodasAsSecoes() {
        String menu = cardapio.getCardapio();
        assertTrue(menu.contains("Lanches Tradicionais"));
        assertTrue(menu.contains("Lanches Veganos"));
        assertTrue(menu.contains("Acompanhamentos"));
        assertTrue(menu.contains("Bebidas"));
    }

    @Test
    public void deveCardapioExibirTodosOsItensEmQualquerNivel() {
        String menu = cardapio.getCardapio();
        assertTrue(menu.contains("Hambúrguer Clássico de Carne Bovina"));
        assertTrue(menu.contains("Burger Gourmet de Carne Bovina"));
        assertTrue(menu.contains("Smash Burger de Proteína de Ervilha"));
        assertTrue(menu.contains("Batata Frita Média"));
        assertTrue(menu.contains("Salada Orgânica"));
        assertTrue(menu.contains("Refrigerante Cola"));
        assertTrue(menu.contains("Suco de Laranja Natural"));
    }

    @Test
    public void deveCardapioSemRaizLancarNullPointerException() {
        Cardapio vazio = new Cardapio();
        assertThrows(NullPointerException.class, vazio::getCardapio);
    }

    @Test
    public void deveTodosOsTiposDeFolhaImplementaremItemCardapio() {
        ItemCardapio h = new ItemHamburguer(new BurgerClassico(new Carne()));
        ItemCardapio a = new ItemAcompanhamento(new BatataFrita());
        ItemCardapio b = new ItemBebida(new Refrigerante());
        ItemCardapio s = new SecaoCardapio("Qualquer");

        assertNotNull(h.getDetalhes());
        assertNotNull(a.getDetalhes());
        assertNotNull(b.getDetalhes());
        assertNotNull(s.getDetalhes());
    }

}
