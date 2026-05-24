package hamburgueria;

import hamburgueria.Descontos.SemDesconto;

import java.util.ArrayList;
import java.util.List;

public class Combo {
    private Hamburguer hamburguer;
    private Acompanhamento acompanhamento;
    private Bebida bebida;
    private List<PedidoObserver> observers = new ArrayList<>();
    private EstrategiaDeDesconto estrategiaDeDesconto = new SemDesconto();

    public Combo(FabricaDeCombo fabrica) {
        this.hamburguer = fabrica.criarHamburguer();
        this.acompanhamento = fabrica.criarAcompanhamento();
        this.bebida = fabrica.criarBebida();
    }

    public Combo(Hamburguer hamburguer, Acompanhamento acompanhamento,
                 Bebida bebida, EstrategiaDeDesconto desconto) {
        this.hamburguer = hamburguer;
        this.acompanhamento = acompanhamento;
        this.bebida = bebida;
        this.estrategiaDeDesconto = desconto;
    }

    public void adicionarObserver(PedidoObserver observer) {
        observers.add(observer);
    }

    public void removerObserver(PedidoObserver observer) {
        observers.remove(observer);
    }

    public void finalizarPedido() {
        for (PedidoObserver observer : observers) {
            observer.onPedidoFinalizado(this);
        }
    }

    public void adicionarIngrediente(Hamburguer hamburguerDecorado) {
        this.hamburguer = hamburguerDecorado;
    }

    public void setEstrategiaDeDesconto(EstrategiaDeDesconto estrategia) {
        this.estrategiaDeDesconto = estrategia;
    }

    public double getPrecoFinal() {
        return estrategiaDeDesconto.aplicar(hamburguer.getPreco());
    }

    public Hamburguer    getHamburguer()        { return hamburguer; }
    public Acompanhamento getAcompanhamento()   { return acompanhamento; }
    public Bebida        getBebida()            { return bebida; }
    public String        getDescricaoDesconto() { return estrategiaDeDesconto.getDescricao(); }

    public String aceitar(HamburgueriaVisitor visitor) {
        return visitor.visitarCombo(this);
    }

    public void exibirResumo() {
        System.out.println("Lanche: " + hamburguer.getDescricao() + " | R$ " + hamburguer.getPreco());
        System.out.println("Acompanhamento: " + acompanhamento.getDescricao());
        System.out.println("Bebida: " + bebida.getDescricao());
        System.out.println("Desconto: " + estrategiaDeDesconto.getDescricao());
        System.out.printf("Total com desconto: R$ %.2f%n", getPrecoFinal());
        System.out.println("--------------------------------------------------");
    }
}