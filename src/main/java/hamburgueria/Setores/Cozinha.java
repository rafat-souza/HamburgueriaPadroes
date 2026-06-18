package hamburgueria.Setores;

import hamburgueria.Combo;
import hamburgueria.PedidoObserver;
import hamburgueria.Preparo.*;
import hamburgueria.SetorDaHamburgueria;

public class Cozinha implements PedidoObserver, SetorDaHamburgueria {

    @Override
    public String onPedidoFinalizado(Combo combo) {
        return "[Cozinha] Notificação recebida via Observer para o lanche: " + combo.getHamburguer().getDescricao();
    }

    @Override
    public String receberNovoPedido(Combo combo) {
        StringBuilder sb = new StringBuilder();
        sb.append("A Cozinha começou a preparar: ").append(combo.getHamburguer().getDescricao()).append("\n");

        PreparacaoLanche preparo = new PreparacaoLanche(combo.getHamburguer());
        FilaPreparacao fila = new FilaPreparacao();

        sb.append(">> ").append(fila.executarEtapa(new ComandoGrelharProteina(preparo))).append("\n");
        sb.append(">> ").append(fila.executarEtapa(new ComandoMontarLanche(preparo))).append("\n");
        sb.append(">> ").append(fila.executarEtapa(new ComandoEmbalarPedido(preparo)));

        return sb.toString();
    }

    @Override
    public String receberCancelamento(Combo combo) {
        return "A Cozinha cancelou o preparo de: " + combo.getHamburguer().getDescricao()
                + "\n>> [Cozinha] Executando rollback das etapas de preparação.";
    }

    @Override
    public String receberPagamento(Combo combo) {
        return "A Cozinha foi informada que o pagamento foi confirmado para: " + combo.getHamburguer().getDescricao();
    }

}
