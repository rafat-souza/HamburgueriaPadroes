package hamburgueria;

import hamburgueria.Cardapio.Cardapio;
import hamburgueria.Cardapio.ItemHamburguer;
import hamburgueria.Cardapio.SecaoCardapio;
import hamburgueria.Combos.FabricaComboTradicional;
import hamburgueria.Combos.FabricaComboVegano;
import hamburgueria.Descontos.DescontoPromocaoPrototype;
import hamburgueria.Setores.Caixa;
import hamburgueria.Setores.Cozinha;

public class HamburgueriaPedidoFacade {

    private final Atendente atendente = new Atendente();
    private final Cardapio cardapioDoSistema;
    private final Promocao promocaoCadastrada;

    public HamburgueriaPedidoFacade(Cardapio cardapioDoSistema, Promocao promocaoCadastrada) {
        this.cardapioDoSistema = cardapioDoSistema;
        this.promocaoCadastrada = promocaoCadastrada;
    }

    public String exibirCardapioSincronizado() {
        return cardapioDoSistema.getCardapio();
    }

    public Combo criarPedidoTradicional() {
        FabricaDeCombo fabrica = new FabricaComboTradicional();
        return new ComboBuilder()
                .setHamburguer(fabrica.criarHamburguer())
                .setAcompanhamento(fabrica.criarAcompanhamento())
                .setBebida(fabrica.criarBebida())
                .adicionarObserver(new Cozinha())
                .adicionarObserver(new Caixa())
                .build();
    }

    public Combo criarPedidoVegano() {
        FabricaDeCombo fabrica = new FabricaComboVegano();
        return new ComboBuilder()
                .setHamburguer(fabrica.criarHamburguer())
                .setAcompanhamento(fabrica.criarAcompanhamento())
                .setBebida(fabrica.criarBebida())
                .adicionarObserver(new Cozinha())
                .adicionarObserver(new Caixa())
                .build();
    }

    public String enviarPedido(Combo combo) {
        return atendente.fazerPedido(combo);
    }

    public String aplicarDescontoPromocionalPrototype(Combo combo) {
        EstrategiaDeDesconto descontoPrototype = new DescontoPromocaoPrototype(promocaoCadastrada);
        combo.setEstrategiaDeDesconto(descontoPrototype);
        return atendente.processarPagamento(combo);
    }

    public String aplicarDescontoEProcessarPagamento(Combo combo, EstrategiaDeDesconto desconto) {
        combo.setEstrategiaDeDesconto(desconto);
        return atendente.processarPagamento(combo);
    }

    public String cancelarPedido(Combo combo) {
        return atendente.cancelarPedido(combo);
    }

}
