package hamburgueria.Preparo;

public class ComandoGrelharProteina implements ComandoPreparacao {

    private final PreparacaoLanche preparacao;

    public ComandoGrelharProteina(PreparacaoLanche preparacao) {
        this.preparacao = preparacao;
    }

    @Override
    public String executar() {
        return preparacao.grelharProteina();
    }

    @Override
    public String cancelar() {
        return preparacao.descartarProteina();
    }

}
