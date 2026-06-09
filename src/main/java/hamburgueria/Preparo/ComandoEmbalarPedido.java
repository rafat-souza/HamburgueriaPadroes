package hamburgueria.Preparo;

public class ComandoEmbalarPedido implements ComandoPreparacao {

    private final PreparacaoLanche preparacao;

    public ComandoEmbalarPedido(PreparacaoLanche preparacao) {
        this.preparacao = preparacao;
    }

    @Override
    public String executar() {
        return preparacao.embalarPedido();
    }

    @Override
    public String cancelar() {
        return preparacao.desembalarPedido();
    }

}
