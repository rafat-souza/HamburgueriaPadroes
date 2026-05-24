package hamburgueria.Acompanhamentos;

import hamburgueria.Acompanhamento;
import hamburgueria.HamburgueriaVisitor;

public class Salada implements Acompanhamento {
    @Override
    public String getDescricao() { return "Salada Orgânica"; }

    @Override
    public String aceitar(HamburgueriaVisitor visitor) {
        return visitor.visitarAcompanhamento(this);
    }
}
