package hamburgueria;

public class CategoriaClienteVIP implements ICategoriaCliente {

    private String categoria;

    @Override
    public String getCategoria() {
        return categoria;
    }

    @Override
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

}
