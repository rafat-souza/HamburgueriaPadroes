package hamburgueria;

public class DescontoLegadoAdapter extends CalculadoraPrecosLegado implements EstrategiaDeDesconto {

    private final ICategoriaCliente categoriaCliente;

    public DescontoLegadoAdapter(ICategoriaCliente categoriaCliente) {
        this.categoriaCliente = categoriaCliente;
    }

    public String recuperarCategoria() {
        float fator = this.getFatorDesconto();
        if (fator >= 0.30f)
            categoriaCliente.setCategoria("PREMIUM");
        else if (fator >= 0.20f)
            categoriaCliente.setCategoria("OURO");
        else if (fator >= 0.10f)
            categoriaCliente.setCategoria("PRATA");
        else
            categoriaCliente.setCategoria("BRONZE");

        return categoriaCliente.getCategoria();
    }

    public void salvarFator() {
        switch (categoriaCliente.getCategoria()) {
            case "PREMIUM": this.setFatorDesconto(0.30f); break;
            case "OURO":    this.setFatorDesconto(0.20f); break;
            case "PRATA":   this.setFatorDesconto(0.10f); break;
            default:        this.setFatorDesconto(0.05f); break;
        }
    }

    @Override
    public double aplicar(double preco) {
        return preco * (1.0 - this.getFatorDesconto());
    }

    @Override
    public String getDescricao() {
        return "Desconto VIP " + recuperarCategoria()
                + " (" + Math.round(this.getFatorDesconto() * 100) + "%)";
    }

}
