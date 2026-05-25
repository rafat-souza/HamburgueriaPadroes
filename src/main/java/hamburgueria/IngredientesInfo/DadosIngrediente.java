package hamburgueria.IngredientesInfo;

public class DadosIngrediente {

    private final String nome;
    private final String categoria;
    private final String infoNutricional;

    public DadosIngrediente(String nome, String categoria, String infoNutricional) {
        this.nome = nome;
        this.categoria = categoria;
        this.infoNutricional = infoNutricional;
    }

    public String getNome() {
        return nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getInfoNutricional() {
        return infoNutricional;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (%s)", categoria, nome, infoNutricional);
    }

}
