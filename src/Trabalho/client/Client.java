package Trabalho.client;

import utils.InputValidation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {


    static int attempts = 3;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Digite o hostname do servidor: ");
        String hostname = sc.nextLine();

        int portNumber = InputValidation.validateIntBetween(sc, "Digite a porta que irá se conectar (1024...65535): ", 1024, 65535);

        try (
                Socket clientSocket = new Socket(hostname, portNumber);
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            boolean logged = false;

            do {
                System.out.print("Username: ");
                out.println(sc.nextLine());
                System.out.print("Password: ");
                out.println(sc.nextLine());

                String status = in.readLine();

                if (status.equalsIgnoreCase("ok")) {
                    System.out.println("Logado com sucesso.");
                    logged = true;
                } else {
                    attempts--;
                    System.out.println(status);
                    if (attempts == 0) {
                        System.out.println("Acabaram suas tentativas.");
                        System.exit(0);
                    } else {
                        System.out.println("Tentativas restantes: " + attempts);
                    }
                }

            } while(!logged);

            System.out.println("Aguarde o jogo começar...");

            System.out.println(in.readLine());

        } catch (UnknownHostException e) {
            System.err.println("Host não encontrada");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Ocorreu um erro de I/O ao iniciar o socket");
            System.exit(1);
        }

    }
}
