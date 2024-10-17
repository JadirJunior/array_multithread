package Ficha7.Ex2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class CalcClient {

    private static final String[] hostnames = {"localhost1", "localhost", "localhost2"};
    private static final int[] ports = {6000, 6001, 6002};
    private static final int maxTries = 5;
    private static int currentHostNameIndex = 0;
    private static int currentPortIndex = 0;

    private static Socket connectToServer(String hostname, int port) throws IOException {
        return new Socket(hostname, port);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);


        while (true) {
            if (currentHostNameIndex == hostnames.length) {
                System.err.println("Não foi possível se conectar com nenhum hostname");
                System.exit(0);
            }

            System.out.println("Tentando conexão com host " + hostnames[currentHostNameIndex] + ":" + ports[currentPortIndex]);

            try (
                    Socket socket = connectToServer(hostnames[currentHostNameIndex], ports[currentPortIndex]);
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
                    ) {
                System.out.println("Conexão estabelecida.");
                String input = "";

                while (!input.equalsIgnoreCase("sair")) {
                    System.out.print("Escreva a expressão matemática ou digite 'sair para sair'. \nUtilize o padrão: <operador1> <operador> <operador2>\n:> ");
                    input = sc.nextLine();
                    out.println(input);
                    System.out.println(in.readLine());
                }

                break;

            } catch (IOException e) {
                //System.err.println("A conexão com o host " + hostnames[currentHostNameIndex] + ":" + ports[currentPortIndex] + " falhou");

                if (currentPortIndex == (ports.length-1)) {
                    currentHostNameIndex++;
                    currentPortIndex = 0;
                } else {
                    currentPortIndex++;
                }
            }
        }

        sc.close();

    }

}
