package Ficha5.Ex1;

import utils.InputValidation;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private final static int N_THREADS = 10;
    private final static int SOCKET_TIMEOUT = 10000;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int portNumber = InputValidation.validateIntBetween(
                sc,
                "Introduza o n�mero da porta que o servidor ir� escutar (1024...65535): ",
                1024, 65535);

        try (
                ServerSocket serverSocket = new ServerSocket(portNumber);
                ExecutorService executor = Executors.newFixedThreadPool(N_THREADS)
        ) {

            //Tempo limite para esperar novas conex�es
            serverSocket.setSoTimeout(SOCKET_TIMEOUT);

            while (true) {
                System.out.println("Main: � espera de novas liga��es");

                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Main: Nova liga��o");
                    executor.execute(new ServerThread(clientSocket));
                } catch (SocketTimeoutException e) {
                    System.out.println("Main: Acabou o tempo para aceitar novos jogadores");
                    executor.shutdown();
                    break;
                }
            }




        } catch (IOException e) {
            System.err.println("Main: Ocorreu um erro de I/O ao iniciar o socket.");
            System.exit(2);
        }

        sc.close();
    }

}
