package modelo;

public class Administrador extends Pessoa {
    private String setorResponsavel;
    private Equipe equipe;

    public Administrador(String cpf, String nome, String endereco, String setorResponsavel) {
        super(cpf, nome, endereco);
        this.setorResponsavel = setorResponsavel;
        this.equipe = null; 
    }

    public String getSetorResponsavel() {
        return setorResponsavel;
    }

    public void setSetorResponsavel(String setorResponsavel) {
        this.setorResponsavel = setorResponsavel;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    @Override
    public String toString() {
        return super.toString() + ", Setor: " + setorResponsavel;
    }
}
