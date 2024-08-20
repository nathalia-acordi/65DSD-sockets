package servidor;

import java.io.*;
import java.net.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import modelo.Administrador;
import modelo.Equipe;
import modelo.Membro;
import modelo.Pessoa;

public class Servidor {
    private static final int PORTA = 12345;
    private static List<Pessoa> pessoas = new ArrayList<>();
    private static List<Equipe> equipes = new ArrayList<>();

    public static void main(String[] args) {
        try (ServerSocket servidor = new ServerSocket(PORTA)) {
            System.out.println("Servidor iniciado na porta " + PORTA);
            while (true) {
                try (Socket cliente = servidor.accept();
                     BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                     PrintWriter saida = new PrintWriter(cliente.getOutputStream(), true)) {

                    System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());

                    String mensagem;
                    while ((mensagem = entrada.readLine()) != null) {
                        if (mensagem.equalsIgnoreCase("sair")) {
                            break;
                        }

                        System.out.println("Mensagem recebida: " + mensagem);
                        String resposta = processarMensagem(mensagem);
                        saida.println(resposta);
                    }
                } catch (IOException e) {
                    System.err.println("Erro na comunicação com o cliente: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao iniciar o servidor: " + e.getMessage());
        }
    }

    private static String processarMensagem(String mensagem) {
        String[] partes = mensagem.split(";");
        String comando = partes[0].trim();

        switch (comando.toUpperCase()) {
            case "INSERT":
                return inserirPessoa(partes);
            case "UPDATE":
                return atualizarPessoa(partes);
            case "GET":
                return obterPessoa(partes);
            case "DELETE":
                return removerPessoa(partes);
            case "LIST":
                return listarPessoas();
            case "INSERT_EQUIPE":
                return inserirEquipe(partes);
            case "UPDATE_EQUIPE":
                return atualizarEquipe(partes);
            case "GET_EQUIPE":
                return obterEquipe(partes);
            case "DELETE_EQUIPE":
                return removerEquipe(partes);
            case "LIST_EQUIPE":
                return listarEquipes();
            case "INSERT_MEMBRO_EQUIPE":
                return adicionarMembroAEquipe(partes);
            case "DELETE_MEMBRO_EQUIPE":
                return removerMembroDaEquipe(partes);

            default:
                return "Erro: Operação desconhecida.";
        }
    }

    private static String inserirPessoa(String[] partes) {
        try {
            if (partes.length < 5) {
                return "Erro: Parâmetros insuficientes para INSERT.";
            }
            String tipo = partes[1].trim();
            String cpf = partes[2].trim();
            String nome = partes[3].trim();
            String endereco = partes[4].trim();

            if (tipo.equalsIgnoreCase("ADMIN")) {
                if (partes.length < 6) {
                    return "Erro: Parâmetros insuficientes para inserir ADMIN.";
                }
                String setor = partes[5].trim();
                Administrador admin = new Administrador(cpf, nome, endereco, setor);
                pessoas.add(admin);
            } else if (tipo.equalsIgnoreCase("MEMBRO")) {
                if (partes.length < 7) {
                    return "Erro: Parâmetros insuficientes para inserir MEMBRO.";
                }
                LocalDate dataEntrada = LocalDate.parse(partes[5].trim());
                LocalTime horaEntrada = LocalTime.parse(partes[6].trim());
                Membro membro = new Membro(cpf, nome, endereco, dataEntrada, horaEntrada);
                pessoas.add(membro);
            } else {
                return "Erro: Tipo de pessoa desconhecido.";
            }

            return "Pessoa inserida com sucesso.";
        } catch (Exception e) {
            return "Erro ao inserir pessoa: " + e.getMessage();
        }
    }

    private static String atualizarPessoa(String[] partes) {
        try {
            if (partes.length < 4) {
                return "Erro: Parâmetros insuficientes para UPDATE.";
            }
            String cpf = partes[1].trim();
            String nome = partes[2].trim();
            String endereco = partes[3].trim();

            for (Pessoa pessoa : pessoas) {
                if (pessoa.getCpf().equals(cpf)) {
                    pessoa.setNome(nome);
                    pessoa.setEndereco(endereco);
                    return "Pessoa atualizada com sucesso.";
                }
            }

            return "Erro: Pessoa não encontrada.";
        } catch (Exception e) {
            return "Erro ao atualizar pessoa: " + e.getMessage();
        }
    }

    private static String obterPessoa(String[] partes) {
        try {
            if (partes.length < 2) {
                return "Erro: Parâmetros insuficientes para GET.";
            }
            String cpf = partes[1].trim();

            for (Pessoa pessoa : pessoas) {
                if (pessoa.getCpf().equals(cpf)) {
                    return pessoa.toString();
                }
            }

            return "Erro: Pessoa não encontrada.";
        } catch (Exception e) {
            return "Erro ao obter pessoa: " + e.getMessage();
        }
    }

    private static String removerPessoa(String[] partes) {
        try {
            if (partes.length < 2) {
                return "Erro: Parâmetros insuficientes para DELETE.";
            }
            String cpf = partes[1].trim();

            for (Pessoa pessoa : pessoas) {
                if (pessoa.getCpf().equals(cpf)) {
                    pessoas.remove(pessoa);
                    return "Pessoa removida com sucesso.";
                }
            }

            return "Erro: Pessoa não encontrada.";
        } catch (Exception e) {
            return "Erro ao remover pessoa: " + e.getMessage();
        }
    }

    private static String listarPessoas() {
        StringBuilder resposta = new StringBuilder();
        resposta.append("Quantidade de pessoas: ").append(pessoas.size()).append("\n");

        for (Pessoa pessoa : pessoas) {
            resposta.append(pessoa).append("\n");
        }

        return resposta.toString().trim();
    }

    private static String inserirEquipe(String[] partes) {
        try {
            if (partes.length < 4) {
                return "Erro: Parâmetros insuficientes para INSERT_EQUIPE.";
            }
            Long id = Long.parseLong(partes[1].trim());
            String nome = partes[2].trim();
            String admCpf = partes[3].trim();

            Administrador adm = null;
            for (Pessoa pessoa : pessoas) {
                if (pessoa instanceof Administrador && pessoa.getCpf().equals(admCpf)) {
                    adm = (Administrador) pessoa;
                    break;
                }
            }

            if (adm == null) {
                return "Erro: Administrador não encontrado.";
            }

            Equipe equipe = new Equipe(id, nome, adm);
            equipes.add(equipe);
            return "Equipe inserida com sucesso.";
        } catch (Exception e) {
            return "Erro ao inserir equipe: " + e.getMessage();
        }
    }

    private static String atualizarEquipe(String[] partes) {
        try {
            if (partes.length < 4) {
                return "Erro: Parâmetros insuficientes para UPDATE_EQUIPE.";
            }
            Long id = Long.parseLong(partes[1].trim());
            String nome = partes[2].trim();
            String admCpf = partes[3].trim();

            Equipe equipe = null;
            for (Equipe eq : equipes) {
                if (eq.getId().equals(id)) {
                    equipe = eq;
                    break;
                }
            }

            if (equipe == null) {
                return "Erro: Equipe não encontrada.";
            }

            Administrador adm = null;
            for (Pessoa pessoa : pessoas) {
                if (pessoa instanceof Administrador && pessoa.getCpf().equals(admCpf)) {
                    adm = (Administrador) pessoa;
                    break;
                }
            }

            if (adm == null) {
                return "Erro: Administrador não encontrado.";
            }

            equipe.setNome(nome);
            equipe.setAdministrador(adm);
            return "Equipe atualizada com sucesso.";
        } catch (Exception e) {
            return "Erro ao atualizar equipe: " + e.getMessage();
        }
    }

    private static String obterEquipe(String[] partes) {
        try {
            if (partes.length < 2) {
                return "Erro: Parâmetros insuficientes para GET_EQUIPE.";
            }
            Long id = Long.parseLong(partes[1].trim());
    
            for (Equipe equipe : equipes) {
                if (equipe.getId().equals(id)) {
                    StringBuilder resposta = new StringBuilder();
                    resposta.append("ID: ").append(equipe.getId()).append("\n")
                            .append("Nome: ").append(equipe.getNome()).append("\n")
                            .append("Administrador: ").append(equipe.getAdministrador().toString()).append("\n")
                            .append("Membros: \n");
    
                    for (Membro membro : equipe.getMembros()) {
                        resposta.append("  - ").append(membro.toString()).append("\n");
                    }
    
                    return resposta.toString().trim();
                }
            }
    
            return "Erro: Equipe não encontrada.";
        } catch (Exception e) {
            return "Erro ao obter equipe: " + e.getMessage();
        }
    }
    
    private static String removerEquipe(String[] partes) {
        try {
            if (partes.length < 2) {
                return "Erro: Parâmetros insuficientes para DELETE_EQUIPE.";
            }
            Long id = Long.parseLong(partes[1].trim());

            for (Equipe equipe : equipes) {
                if (equipe.getId().equals(id)) {
                    equipes.remove(equipe);
                    return "Equipe removida com sucesso.";
                }
            }

            return "Erro: Equipe não encontrada.";
        } catch (Exception e) {
            return "Erro ao remover equipe: " + e.getMessage();
        }
    }

    private static String listarEquipes() {
        StringBuilder resposta = new StringBuilder();
        resposta.append("Quantidade de equipes: ").append(equipes.size()).append("\n");
    
        for (Equipe equipe : equipes) {
            resposta.append("ID: ").append(equipe.getId()).append("\n")
                    .append("Nome: ").append(equipe.getNome()).append("\n")
                    .append("Administrador: ").append(equipe.getAdministrador().toString()).append("\n")
                    .append("Membros: \n");
    
            for (Membro membro : equipe.getMembros()) {
                resposta.append("  - ").append(membro.toString()).append("\n");
            }
            resposta.append("\n"); 
        }
    
        return resposta.toString().trim();
    }
    

    private static String adicionarMembroAEquipe(String[] partes) {
        try {
            if (partes.length < 3) {
                return "Erro: Parâmetros insuficientes para INSERT_MEMBRO_EQUIPE.";
            }
            Long idEquipe = Long.parseLong(partes[1].trim());
            String cpfMembro = partes[2].trim();

            Equipe equipe = null;
            for (Equipe eq : equipes) {
                if (eq.getId().equals(idEquipe)) {
                    equipe = eq;
                    break;
                }
            }

            if (equipe == null) {
                return "Erro: Equipe não encontrada.";
            }

            Pessoa pessoa = null;
            for (Pessoa p : pessoas) {
                if (p instanceof Membro && p.getCpf().equals(cpfMembro)) {
                    pessoa = p;
                    break;
                }
            }

            if (pessoa == null) {
                return "Erro: Membro não encontrado.";
            }

            equipe.getMembros().add((Membro) pessoa);
            return "Membro adicionado à equipe com sucesso.";
        } catch (Exception e) {
            return "Erro ao adicionar membro à equipe: " + e.getMessage();
        }
    }

    private static String removerMembroDaEquipe(String[] partes) {
        try {
            if (partes.length < 3) {
                return "Erro: Parâmetros insuficientes para DELETE_MEMBRO_EQUIPE.";
            }
            Long idEquipe = Long.parseLong(partes[1].trim());
            String cpfMembro = partes[2].trim();

            Equipe equipe = null;
            for (Equipe eq : equipes) {
                if (eq.getId().equals(idEquipe)) {
                    equipe = eq;
                    break;
                }
            }

            if (equipe == null) {
                return "Erro: Equipe não encontrada.";
            }

            Membro membroARemover = null;
            for (Membro membro : equipe.getMembros()) {
                if (membro.getCpf().equals(cpfMembro)) {
                    membroARemover = membro;
                    break;
                }
            }

            if (membroARemover == null) {
                return "Erro: Membro não encontrado.";
            }

            equipe.getMembros().remove(membroARemover);
            return "Membro removido da equipe com sucesso.";
        } catch (Exception e) {
            return "Erro ao remover membro da equipe: " + e.getMessage();
        }
    }
}
