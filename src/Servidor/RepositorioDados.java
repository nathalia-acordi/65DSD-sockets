import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RepositorioDados {
    private Map<String, Pessoa> pessoas = new HashMap<>();

    public synchronized void adicionarPessoa(String cpf, String nome, String endereco) {
        if (!pessoas.containsKey(cpf)) {
            pessoas.put(cpf, new Pessoa(cpf, nome, endereco));
        }
    }

    public synchronized String atualizarPessoa(String cpf, String nome, String endereco) {
        if (pessoas.containsKey(cpf)) {
            Pessoa pessoa = pessoas.get(cpf);
            pessoa.setNome(nome);
            pessoa.setEndereco(endereco);
            return "Pessoa atualizada com sucesso.";
        } else {
            return "Pessoa não encontrada.";
        }
    }

    public synchronized String obterPessoa(String cpf) {
        Pessoa pessoa = pessoas.get(cpf);
        if (pessoa != null) {
            return pessoa.getCpf() + ";" + pessoa.getNome() + ";" + pessoa.getEndereco();
        } else {
            return "Pessoa não encontrada.";
        }
    }

    public synchronized String removerPessoa(String cpf) {
        if (pessoas.containsKey(cpf)) {
            pessoas.remove(cpf);
            return "Pessoa removida com sucesso.";
        } else {
            return "Pessoa não encontrada.";
        }
    }

    public synchronized String listarPessoas() {
        if (pessoas.isEmpty()) {
            return "0";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(pessoas.size()).append("\n");
            for (Pessoa pessoa : pessoas.values()) {
                sb.append(pessoa.getCpf()).append(";").append(pessoa.getNome()).append(";").append(pessoa.getEndereco()).append("\n");
            }
            return sb.toString();
        }
    }
}
