import java.util.List;

public class Equipe {
    private Long id;
    private String nome;
    private List<Membro> membros;
    private Administrador adm;

    public Equipe(Long id, String nome, List<Membro> membros, Administrador adm) {
        this.id = id;
        this.nome = nome;
        this.membros = membros;
        this.adm = adm;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public List<Membro> getMembros() { return membros; }
    public void setMembros(List<Membro> membros) { this.membros = membros; }

    public Administrador getAdm() { return adm; }
    public void setAdm(Administrador adm) { this.adm = adm; }
}
