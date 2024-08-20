package modelo;

import java.util.ArrayList;
import java.util.List;

public class Equipe {
    private Long id;
    private String nome;
    private List<Membro> membros;
    private Administrador administrador;

    public Equipe(Long id, String nome, Administrador administrador) {
        this.id = id;
        this.nome = nome;
        this.administrador = administrador;
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

    public List<Membro> getMembros() {
        return membros;
    }

    public void setMembros(List<Membro> membros) {
        this.membros = membros;
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    @Override
    public String toString() {
        return "Equipe{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", administrador=" + administrador.getNome() +
                ", membros=" + membros +
                '}';
    }
}
