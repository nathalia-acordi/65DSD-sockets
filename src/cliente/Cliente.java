package cliente;

import java.io.*;
import java.net.*;

public class Cliente {
    private static final String HOST = "localhost";
    private static final int PORTA = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PORTA);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Conectado ao servidor!!!");
            String mensagem;

            while (true) {
                System.out.print("Digite uma mensagem para o servidor (ou 'sair' para terminar): \n ");
                mensagem = teclado.readLine();

                if (mensagem.equalsIgnoreCase("sair")) {
                    break;
                }

                out.println(mensagem);
                lerResposta(in);
            }
        } catch (IOException e) {
            System.err.println("Erro ao conectar com o servidor: " + e.getMessage());
        }
    }

    private static void lerResposta(BufferedReader in) throws IOException {
        String linha;
        while ((linha = in.readLine()) != null) {
            System.out.println(linha);
            if (!in.ready()) {
                break;
            }
        }
    }
}
