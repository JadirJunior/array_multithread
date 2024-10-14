package Trabalho.server;

import Trabalho.utils.FileHandler;
import Trabalho.utils.User;
import utils.InputValidation;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public static volatile FileHandler file;

    public static String WORD;
    private static int MAX_PLAYERS = 10;
    private static int TIMEOUT_WAIT_PLAYERS = 50000; //ms
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Server: Jogo da forca");
        file = new FileHandler(new File("src/Trabalho/server/resources/users.txt").getAbsolutePath());

        int portNumber = InputValidation.validateIntBetween(
                sc,
                "Digite a porta que deseja ouvir (1024...65535): ",
                1024, 65535);

        do {
            System.out.println("Digite a palavra a ser descoberta (Exceto a palavra 'desisto'):");
            WORD = sc.nextLine();
        } while (WORD.equalsIgnoreCase("desisto"));

        try (
                ServerSocket serverSocket = new ServerSocket(portNumber);
                ExecutorService executorService = Executors.newFixedThreadPool(MAX_PLAYERS)
                ) {

            //Tempo limite para aguardar a entrada de novos jogadores
            serverSocket.setSoTimeout(TIMEOUT_WAIT_PLAYERS);

            while (true) {
                System.out.println("Main: Esperando a chegada de novos jogadores.");
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Main: Nova ligação");
                    executorService.execute(new ServerThread(clientSocket));
                } catch (SocketTimeoutException e) {
                    System.out.println("Main: Acabou o tempo para aceitar novos jogadores");
                    executorService.shutdownNow();
                    break;
                }
            }

        } catch (IOException e) {
            System.err.println("Main: Ocorreu um erro de I/O ao iniciar o socket");
            System.exit(1);
        }


        sc.close();

    }

}
