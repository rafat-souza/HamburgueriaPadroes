package hamburgueria.Acompanhamentos;

import hamburgueria.Acompanhamento;
import hamburgueria.HamburgueriaVisitor;

public class BatataFrita implements Acompanhamento {
    @Override
    public String getDescricao() { return "Batata Frita Média"; }

    @Override
    public String aceitar(HamburgueriaVisitor visitor) {
        return visitor.visitarAcompanhamento(this);
    }
}
