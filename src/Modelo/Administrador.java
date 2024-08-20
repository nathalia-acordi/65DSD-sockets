package modelo;
public class Administrador extends Pessoa {
    private String setorResponsavel;

    public Administrador(String cpf, String nome, String endereco, String setorResponsavel) {
        super(cpf, nome, endereco);
        this.setorResponsavel = setorResponsavel;
    }

    public String getSetorResponsavel() {
        return setorResponsavel;
    }

    public void setSetorResponsavel(String setorResponsavel) {
        this.setorResponsavel = setorResponsavel;
    }

    @Override
    public String toString() {
        return super.toString() + ";" + setorResponsavel;
    }
}
