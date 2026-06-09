package hamburgueria.Preparo;

import hamburgueria.Hamburguer;

public class PreparacaoLanche {

    private final Hamburguer hamburguer;

    private boolean proteinaGrelhada = false;
    private boolean lancheMontado    = false;
    private boolean pedidoEmbalado   = false;

    public PreparacaoLanche(Hamburguer hamburguer) {
        this.hamburguer = hamburguer;
    }

    public String grelharProteina() {
        proteinaGrelhada = true;
        return "[Cozinha] Proteína grelhada: " + hamburguer.getDescricao();
    }

    public String descartarProteina() {
        proteinaGrelhada = false;
        return "[Cozinha] Proteína descartada: " + hamburguer.getDescricao();
    }

    public String montarLanche() {
        lancheMontado = true;
        return "[Cozinha] Lanche montado: " + hamburguer.getDescricao();
    }

    public String desmontarLanche() {
        lancheMontado = false;
        return "[Cozinha] Lanche desmontado: " + hamburguer.getDescricao();
    }

    public String embalarPedido() {
        pedidoEmbalado = true;
        return "[Cozinha] Pedido embalado: " + hamburguer.getDescricao();
    }

    public String desembalarPedido() {
        pedidoEmbalado = false;
        return "[Cozinha] Pedido desembalado: " + hamburguer.getDescricao();
    }

    public boolean isProteinaGrelhada() { return proteinaGrelhada; }
    public boolean isLancheMontado()    { return lancheMontado; }
    public boolean isPedidoEmbalado()   { return pedidoEmbalado; }

    public Hamburguer getHamburguer()   { return hamburguer; }

}
