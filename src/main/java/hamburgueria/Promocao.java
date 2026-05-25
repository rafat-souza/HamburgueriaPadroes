package hamburgueria;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Promocao implements Cloneable {

    private String nome;
    private String descricao;
    private double percentualDesconto;
    private String validade;
    private List<String> combosElegiveis;

    public Promocao(String nome, String descricao, double percentualDesconto, String validade) {
        this.nome = nome;
        this.descricao = descricao;
        this.percentualDesconto = percentualDesconto;
        this.validade = validade;
        this.combosElegiveis = new ArrayList<>();
    }

    @Override
    public Promocao clone() throws CloneNotSupportedException {
        Promocao clone = (Promocao) super.clone();
        clone.combosElegiveis = new ArrayList<>(this.combosElegiveis);
        return clone;
    }

    public void adicionarComboElegivel(String nomeCombo) {
        combosElegiveis.add(nomeCombo);
    }

    public void removerComboElegivel(String nomeCombo) {
        combosElegiveis.remove(nomeCombo);
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setPercentualDesconto(double p) {
        this.percentualDesconto = p;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getPercentualDesconto() {
        return percentualDesconto;
    }

    public String getValidade() {
        return validade;
    }

    public List<String> getCombosElegiveis() {
        return Collections.unmodifiableList(combosElegiveis);
    }

    @Override
    public String toString() {
        return "Promocao{" +
                "nome='" + nome + '\'' +
                ", desconto=" + percentualDesconto + "%" +
                ", validade='" + validade + '\'' +
                ", combosElegiveis=" + combosElegiveis +
                '}';
    }

}
