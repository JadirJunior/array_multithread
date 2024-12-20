package Ficha5.Ex1;

import utils.InputValidation;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class Server {

    private final static int N_THREADS = 10;
    private final static int SOCKET_TIMEOUT = 10000;
    private final static Phaser ph = new Phaser();
    public static volatile int EXTRACTED_NUMBER = -1;
    private final static int MAX_GAME_TIME = 30;
    public static volatile boolean game_ended = false;

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
                    executor.execute(new ServerThread(clientSocket, ph));
                } catch (SocketTimeoutException e) {
                    System.out.println("Main: Acabou o tempo para aceitar novos jogadores");
                    executor.shutdownNow();
                    break;
                }
            }


            if (!executor.awaitTermination(MAX_GAME_TIME, TimeUnit.SECONDS)) {
                //Timeout terminou
                game_ended = true;
                System.out.println("Main: O tempo de jogo terminou.");
            }

        } catch (IOException e) {
            System.err.println("Main: Ocorreu um erro de I/O ao iniciar o socket.");
            System.exit(2);
        } catch (InterruptedException e) {
            System.out.println("Main: Ocorreu um erro em awaitTermination");
            System.exit(3);
        }

        sc.close();
    }

}
