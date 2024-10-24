package Ficha7.Ex2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class CalcClient {

    private static final String[] hostnames = {"localhost1", "localhost", "localhost2"};
    private static final int[] ports = {6000, 6001, 6002};
    private static final int maxTries = 5;

    private static Socket connectToServer() {
        for (String hostName : hostnames) {
            for (int port : ports) {
                for (int k = 1; k <= maxTries; k++) {
                    System.out.println("A tentar ligar ao servidor " + hostName + ":" + port
                            + ", tentativa " + k + "...");

                    try {
                        return new Socket(hostName, port);

                    } catch (Exception e) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            System.err.println("Erro ao aguardar para tentar nova liga??o ao servidor");
                        }
                    }
                }
            }
        }
        System.out.println("Impossivel estabelecer ligação com os servidores");
        System.exit(2);
        return null;
    }

    public static void main(String[] args) {

        while (true) {
            try (
                    Socket socket = connectToServer();
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))
                    ) {
                System.out.println("Conexão estabelecida.");
                String input = "";

                while (!input.equalsIgnoreCase("sair")) {
                    System.out.print("Escreva a expressão matemática ou digite 'sair para sair'. \nUtilize o padrão: <operador1> <operador> <operador2>\n:> ");
                    input = userInput.readLine();
                    out.println(input);
                    System.out.println(in.readLine());
                }

                break;

            } catch (UnknownHostException e) {
                System.err.println("Servidor desconhecido");
            }

            catch (IOException e) {
                System.err.println("Ocorreu um erro ao criar os Buffers");
            }
        }


    }

}
