package hamburgueria.Preparo;

public class ComandoMontarLanche implements ComandoPreparacao {

    private final PreparacaoLanche preparacao;

    public ComandoMontarLanche(PreparacaoLanche preparacao) {
        this.preparacao = preparacao;
    }

    @Override
    public String executar() {
        return preparacao.montarLanche();
    }

    @Override
    public String cancelar() {
        return preparacao.desmontarLanche();
    }

}
