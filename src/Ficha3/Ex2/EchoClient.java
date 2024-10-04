package Ficha3.Ex2;

import utils.InputValidation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class EchoClient {
    public static void main(String[] args) {

        try (
                Scanner sc = new Scanner(System.in)
        ) {

            System.out.println("Introduza o hostname do servidor: ");
            String hostname = sc.nextLine();

            int portNumber = InputValidation.validateIntBetween(
                    sc,
                    "Introduza o número da porta que o servidor está escutando (1024...65535): ",
                    1025,
                    65535);

            try (
                    Socket clientSocket = new Socket(hostname, portNumber);
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
            ) {

                String toServer;

                do {
                    System.out.println("Introduza a mensagem a ser enviada ao servidor: ");
                    toServer = sc.nextLine();

                    out.println(toServer);

                    System.out.println("O servidor respondeu: " + in.readLine());
                } while (!toServer.equalsIgnoreCase("Adeus"));

            } catch (UnknownHostException e) {
                System.err.println("Host Desconhecido");
                System.exit(3);

            } catch (IOException e) {
                System.err.println("Ocorreu um erro de I/O ao criar o socket.");
                System.exit(2);
            }
        } catch (Exception e) {
            System.err.println("Ocorreu um erro");
            System.exit(4);
        }
    }
}
