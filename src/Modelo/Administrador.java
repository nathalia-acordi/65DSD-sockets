package modelo;
public class Administrador extends Pessoa {
    private String setorResponsavel;

    public Administrador(String cpf, String nome, String endereco, String setorResponsavel, Equipe equipe) {
        super(cpf, nome, endereco);
        this.setorResponsavel = setorResponsavel;
        this.setEquipe(equipe); // Associação obrigatória com uma equipe
    }

    public String getSetorResponsavel() {
        return setorResponsavel;
    }

    public void setSetorResponsavel(String setorResponsavel) {
        this.setorResponsavel = setorResponsavel;
    }

    @Override
    public String toString() {
        return "Administrador{" +
                "cpf='" + getCpf() + '\'' +
                ", nome='" + getNome() + '\'' +
                ", endereco='" + getEndereco() + '\'' +
                ", setorResponsavel='" + setorResponsavel + '\'' +
                '}';
    }
}
