package Testes;

import hamburgueria.Combo;
import hamburgueria.Combos.FabricaComboTradicional;
import hamburgueria.Setores.Caixa;
import hamburgueria.Setores.Cozinha;
import hamburgueria.PedidoObserver;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class NotificacaoSetoresTest {
    @Test
    public void deveNotificarAoFinalizar() {
        Combo combo = new Combo(new FabricaComboTradicional());

        PedidoObserver cozinha = new Cozinha();
        PedidoObserver caixa = new Caixa();

        combo.adicionarObserver(cozinha);
        combo.adicionarObserver(caixa);

        assertDoesNotThrow(() -> combo.finalizarPedido());
    }
}
