package hamburgueria.IngredientesInfo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DadosIngredienteFactory {

    private static final Map<String, DadosIngrediente> cache = new HashMap<>();

    private DadosIngredienteFactory() {}

    public static DadosIngrediente getDados(String nome,
                                            String categoria,
                                            String infoNutricional) {
        return cache.computeIfAbsent(nome,
                k -> new DadosIngrediente(nome, categoria, infoNutricional));
    }

    public static int getTotalDadosCriados() {
        return cache.size();
    }

    public static Map<String, DadosIngrediente> getCache() {
        return Collections.unmodifiableMap(cache);
    }

    public static void limparCache() {
        cache.clear();
    }

}
