package hamburgueria;

import hamburgueria.Funcionarios.FuncionarioAtendente;

public class ConsultaPedidoProxy implements IConsultaPedido {

    private ConsultaPedido consultaReal;

    private final int numeroPedido;
    private final Combo combo;

    public ConsultaPedidoProxy(int numeroPedido, Combo combo) {
        this.numeroPedido = numeroPedido;
        this.combo = combo;
    }

    @Override
    public String obterResumo() {
        if (this.consultaReal == null) {
            this.consultaReal = new ConsultaPedido(numeroPedido, combo);
        }
        return this.consultaReal.obterResumo();
    }

    @Override
    public String obterDetalhesFinanceiros(FuncionarioHamburgueria funcionario) {
        if (funcionario instanceof FuncionarioAtendente) {
            throw new IllegalArgumentException(
                    "Funcionário '" + funcionario.getDescricaoCargo()
                            + "' não autorizado a consultar detalhes financeiros.");
        }
        if (this.consultaReal == null) {
            this.consultaReal = new ConsultaPedido(numeroPedido, combo);
        }
        return this.consultaReal.obterDetalhesFinanceiros(funcionario);
    }

}
