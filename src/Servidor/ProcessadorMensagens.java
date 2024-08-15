import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class ProcessadorMensagens {
    private RepositorioDados repositorioDados;

    public ProcessadorMensagens(RepositorioDados repositorioDados) {
        this.repositorioDados = repositorioDados;
    }

    public void processarMensagem(String mensagem, PrintWriter out) {
        String[] partes = mensagem.split(";");
        String operacao = partes[0];

        switch (operacao) {
            case "INSERT":
                if (partes.length == 4) {
                    String cpf = partes[1];
                    String nome = partes[2];
                    String endereco = partes[3];
                    repositorioDados.adicionarPessoa(cpf, nome, endereco);
                    out.println("Registro inserido com sucesso.");
                } else {
                    out.println("Dados insuficientes para inserção.");
                }
                break;

            case "UPDATE":
                if (partes.length == 4) {
                    String cpf = partes[1];
                    String nome = partes[2];
                    String endereco = partes[3];
                    String resposta = repositorioDados.atualizarPessoa(cpf, nome, endereco);
                    out.println(resposta);
                } else {
                    out.println("Dados insuficientes para atualização.");
                }
                break;

            case "GET":
                if (partes.length == 2) {
                    String cpf = partes[1];
                    String resposta = repositorioDados.obterPessoa(cpf);
                    out.println(resposta);
                } else {
                    out.println("CPF não informado.");
                }
                break;

            case "DELETE":
                if (partes.length == 2) {
                    String cpf = partes[1];
                    String resposta = repositorioDados.removerPessoa(cpf);
                    out.println(resposta);
                } else {
                    out.println("CPF não informado.");
                }
                break;

            case "LIST":
                String resposta = repositorioDados.listarPessoas();
                out.println(resposta);
                break;

            default:
                out.println("Operação desconhecida.");
                break;
        }
    }
}
