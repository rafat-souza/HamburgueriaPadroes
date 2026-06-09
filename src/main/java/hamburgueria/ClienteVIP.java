package hamburgueria;

public class ClienteVIP {

    private final ICategoriaCliente categoriaCliente;
    private final DescontoLegadoAdapter desconto;

    public ClienteVIP() {
        categoriaCliente = new CategoriaClienteVIP();
        desconto = new DescontoLegadoAdapter(categoriaCliente);
    }

    public void setCategoria(String categoria) {
        categoriaCliente.setCategoria(categoria);
        desconto.salvarFator();
    }

    public String getCategoria() {
        return desconto.recuperarCategoria();
    }

    public float getFatorDesconto() {
        return desconto.getFatorDesconto();
    }

    public EstrategiaDeDesconto getEstrategiaDeDesconto() {
        return desconto;
    }

}
