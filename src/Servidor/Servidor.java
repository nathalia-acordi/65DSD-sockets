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

            // Verifica se o CPF já está em uso
            for (Pessoa p : pessoas) {
                if (p.getCpf().equals(cpf)) {
                    return "Erro: Pessoa com o CPF já existe.";
                }
            }

            if (tipo.equalsIgnoreCase("ADMIN")) {
                if (partes.length < 6) {
                    return "Erro: Parâmetros insuficientes para inserir ADMIN.";
                }
                String setor = partes[5].trim();
                
                // Verifica se já existe um administrador para a equipe
                for (Equipe e : equipes) {
                    if (e.getAdministrador() != null && e.getAdministrador().getCpf().equals(cpf)) {
                        return "Erro: Já existe um administrador com este CPF na equipe.";
                    }
                }
                
                // Verifica se a pessoa está associada a uma equipe
                Administrador admin = new Administrador(cpf, nome, endereco, setor);
                pessoas.add(admin);
            } else if (tipo.equalsIgnoreCase("MEMBRO")) {
                if (partes.length < 7) {
                    return "Erro: Parâmetros insuficientes para inserir MEMBRO.";
                }
                LocalDate dataEntrada = LocalDate.parse(partes[5].trim());
                LocalTime horaEntrada = LocalTime.parse(partes[6].trim());

                // Verifica se a pessoa está associada a uma equipe
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

            Pessoa pessoaARemover = null;
            for (Pessoa pessoa : pessoas) {
                if (pessoa.getCpf().equals(cpf)) {
                    pessoaARemover = pessoa;
                    break;
                }
            }

            if (pessoaARemover == null) {
                return "Erro: Pessoa não encontrada.";
            }

            // Remove o administrador da equipe se houver
            if (pessoaARemover instanceof Administrador) {
                Administrador admin = (Administrador) pessoaARemover;
                for (Equipe equipe : equipes) {
                    if (equipe.getAdministrador() != null && equipe.getAdministrador().equals(admin)) {
                        equipe.setAdministrador(null);
                        break;
                    }
                }
            }

            // Remove o membro da equipe se houver
            if (pessoaARemover instanceof Membro) {
                Membro membro = (Membro) pessoaARemover;
                for (Equipe equipe : equipes) {
                    equipe.removeMembro(membro);
                }
            }

            pessoas.remove(pessoaARemover);
            return "Pessoa removida com sucesso.";
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

            // Verifica se já existe uma equipe com o mesmo ID
            for (Equipe e : equipes) {
                if (e.getId().equals(id)) {
                    return "Erro: Já existe uma equipe com este ID.";
                }
            }

            // Cria e adiciona a nova equipe
            Equipe equipe = new Equipe(id, nome, adm);
            equipes.add(equipe);
            adm.setEquipe(equipe); // Associar a equipe ao administrador

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

            Equipe equipeParaAtualizar = null;
            for (Equipe equipe : equipes) {
                if (equipe.getId().equals(id)) {
                    equipeParaAtualizar = equipe;
                    break;
                }
            }

            if (equipeParaAtualizar == null) {
                return "Erro: Equipe não encontrada.";
            }

            // Atualiza os detalhes da equipe
            Administrador novoAdm = null;
            for (Pessoa pessoa : pessoas) {
                if (pessoa instanceof Administrador && pessoa.getCpf().equals(admCpf)) {
                    novoAdm = (Administrador) pessoa;
                    break;
                }
            }

            if (novoAdm == null) {
                return "Erro: Administrador não encontrado.";
            }

            equipeParaAtualizar.setNome(nome);
            equipeParaAtualizar.setAdministrador(novoAdm);

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
                    return equipe.toString();
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

            Equipe equipeARemover = null;
            for (Equipe equipe : equipes) {
                if (equipe.getId().equals(id)) {
                    equipeARemover = equipe;
                    break;
                }
            }

            if (equipeARemover == null) {
                return "Erro: Equipe não encontrada.";
            }

            // Remove a equipe
            equipes.remove(equipeARemover);

            // Remove o administrador da equipe se houver
            Administrador adm = equipeARemover.getAdministrador();
            if (adm != null) {
                adm.setEquipe(null);
            }

            // Remove os membros da equipe
            for (Membro membro : equipeARemover.getMembros()) {
                membro.setEquipe(null);
            }

            return "Equipe removida com sucesso.";
        } catch (Exception e) {
            return "Erro ao remover equipe: " + e.getMessage();
        }
    }

    private static String listarEquipes() {
        StringBuilder resposta = new StringBuilder();
        resposta.append("Quantidade de equipes: ").append(equipes.size()).append("\n");

        for (Equipe equipe : equipes) {
            resposta.append(equipe).append("\n");
        }

        return resposta.toString().trim();
    }

    private static String adicionarMembroAEquipe(String[] partes) {
        try {
            // Verifica se há parâmetros suficientes
            if (partes.length < 3) {
                return "Erro: Parâmetros insuficientes para INSERT_MEMBRO_EQUIPE.";
            }
    
            // Extraí o ID da equipe e o CPF do membro
            Long idEquipe = Long.parseLong(partes[1].trim());
            String cpfMembro = partes[2].trim();
    
            // Encontra a equipe com o ID especificado
            Equipe equipe = null;
            for (Equipe e : equipes) {
                if (e.getId().equals(idEquipe)) {
                    equipe = e;
                    break;
                }
            }
    
            if (equipe == null) {
                return "Erro: Equipe não encontrada.";
            }
    
            // Encontra o membro com o CPF especificado
            Membro membro = null;
            for (Pessoa p : pessoas) {
                if (p instanceof Membro && p.getCpf().equals(cpfMembro)) {
                    membro = (Membro) p;
                    break;
                }
            }
    
            if (membro == null) {
                return "Erro: Membro não encontrado.";
            }
    
            // Verifica se o membro já está associado a outra equipe
            if (membro.getEquipe() != null) {
                return "Erro: Membro já pertence a outra equipe.";
            }
    
            // Adiciona o membro à equipe
            equipe.addMembro(membro);
            membro.setEquipe(equipe);
    
            return "Membro adicionado à equipe com sucesso.";
        } catch (Exception e) {
            return "Erro ao adicionar membro à equipe: " + e.getMessage();
        }
    }    

    private static String removerMembroDaEquipe(String[] partes) {
        try {
            if (partes.length < 4) {
                return "Erro: Parâmetros insuficientes para DELETE_MEMBRO_EQUIPE.";
            }
            Long idEquipe = Long.parseLong(partes[1].trim());
            String cpfMembro = partes[2].trim();

            Equipe equipe = null;
            for (Equipe e : equipes) {
                if (e.getId().equals(idEquipe)) {
                    equipe = e;
                    break;
                }
            }

            if (equipe == null) {
                return "Erro: Equipe não encontrada.";
            }

            Membro membro = null;
            for (Pessoa p : pessoas) {
                if (p instanceof Membro && p.getCpf().equals(cpfMembro)) {
                    membro = (Membro) p;
                    break;
                }
            }

            if (membro == null) {
                return "Erro: Membro não encontrado.";
            }

            if (membro.getEquipe() == null || !membro.getEquipe().equals(equipe)) {
                return "Erro: Membro não pertence a esta equipe.";
            }

            equipe.removeMembro(membro);
            membro.setEquipe(null);

            return "Membro removido da equipe com sucesso.";
        } catch (Exception e) {
            return "Erro ao remover membro da equipe: " + e.getMessage();
        }
    }
}
