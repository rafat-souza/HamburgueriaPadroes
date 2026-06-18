package hamburgueria.Descontos;

import hamburgueria.EstrategiaDeDesconto;
import hamburgueria.Promocao;

public class DescontoPromocaoPrototype implements EstrategiaDeDesconto {

    private Promocao promocaoClonada;

    public DescontoPromocaoPrototype(Promocao promocaoBase) {
        try {
            this.promocaoClonada = promocaoBase.clone();
        } catch (CloneNotSupportedException e) {
            this.promocaoClonada = promocaoBase;
        }
    }

    @Override
    public double aplicar(double preco) {
        return preco * (1.0 - promocaoClonada.getPercentualDesconto() / 100.0);
    }

    @Override
    public String getDescricao() {
        return "Aplicado Prototype da " + promocaoClonada.getNome() + " (" + promocaoClonada.getPercentualDesconto() + "%)";
    }

}
