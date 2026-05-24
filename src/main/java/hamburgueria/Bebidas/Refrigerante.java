package hamburgueria.Bebidas;

import hamburgueria.Bebida;
import hamburgueria.HamburgueriaVisitor;

public class Refrigerante implements Bebida {
    @Override
    public String getDescricao() { return "Refrigerante Cola"; }

    @Override
    public String aceitar(HamburgueriaVisitor visitor) {
        return visitor.visitarBebida(this);
    }
}
