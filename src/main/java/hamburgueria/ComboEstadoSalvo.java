package hamburgueria;

public class ComboEstadoSalvo implements ComboEstado {

    private final Hamburguer           hamburguer;
    private final Acompanhamento       acompanhamento;
    private final Bebida               bebida;
    private final EstrategiaDeDesconto estrategiaDeDesconto;

    public ComboEstadoSalvo(Hamburguer hamburguer, Acompanhamento acompanhamento, Bebida bebida, EstrategiaDeDesconto estrategiaDeDesconto) {
        this.hamburguer = hamburguer;
        this.acompanhamento = acompanhamento;
        this.bebida = bebida;
        this.estrategiaDeDesconto = estrategiaDeDesconto;
    }

    @Override public Hamburguer getHamburguer() {
        return hamburguer;
    }

    @Override public Acompanhamento getAcompanhamento() {
        return acompanhamento;
    }

    @Override public Bebida getBebida() {
        return bebida;
    }

    @Override public EstrategiaDeDesconto getEstrategiaDeDesconto() {
        return estrategiaDeDesconto;
    }

    @Override
    public String getDescricaoEstado() {
        return hamburguer.getDescricao()
                + " | " + acompanhamento.getDescricao()
                + " | " + bebida.getDescricao()
                + " | " + estrategiaDeDesconto.getDescricao();
    }

}
