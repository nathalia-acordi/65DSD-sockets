package modelo;
import java.util.ArrayList;
import java.util.List;

public class Equipe {
    private Long id;
    private String nome;
    private Administrador administrador;
    private List<Membro> membros;

    public Equipe(Long id, String nome) {
        this.id = id;
        this.nome = nome;
        this.membros = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    public List<Membro> getMembros() {
        return membros;
    }

    public void adicionarMembro(Membro membro) {
        membros.add(membro);
    }

    public void removerMembro(Membro membro) {
        membros.remove(membro);
    }

    @Override
    public String toString() {
        return "Equipe{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", administrador=" + (administrador != null ? administrador.getNome() : "Nenhum") +
                ", membros=" + membros.size() +
                '}';
    }
}
