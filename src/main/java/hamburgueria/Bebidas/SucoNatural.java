package hamburgueria.Bebidas;

import hamburgueria.Bebida;
import hamburgueria.HamburgueriaVisitor;

public class SucoNatural implements Bebida {
    @Override
    public String getDescricao() { return "Suco de Laranja Natural"; }

    @Override
    public String aceitar(HamburgueriaVisitor visitor) {
        return visitor.visitarBebida(this);
    }
}
