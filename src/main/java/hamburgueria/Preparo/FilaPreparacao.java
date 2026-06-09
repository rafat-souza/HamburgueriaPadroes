package hamburgueria.Preparo;

import java.util.ArrayList;
import java.util.List;

public class FilaPreparacao {

    private final List<ComandoPreparacao> historico = new ArrayList<>();

    public String executarEtapa(ComandoPreparacao comando) {
        historico.add(comando);
        return comando.executar();
    }

    public String desfazerUltimaEtapa() {
        if (historico.isEmpty()) {
            return "[Cozinha] Nenhuma etapa para desfazer.";
        }
        ComandoPreparacao ultima = historico.remove(historico.size() - 1);
        return ultima.cancelar();
    }

    public List<ComandoPreparacao> getHistorico() {
        return historico;
    }

}
