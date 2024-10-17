package Ficha7.Ex1;

import utils.InputValidation;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CalcServer {

    private static int MAX_THREADS = 10;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int portNumber = InputValidation.validateIntBetween(
                sc,
                "Introduza a porta que o servidor vai escutar (entre 6000 e 6002) \n:> ",
                6000, 6002);

        try (
                ServerSocket serverSocket = new ServerSocket(portNumber);
                ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREADS)
                ) {
            System.out.println("Servidor ativo na porta " + portNumber);

            while (true) {
                System.out.println("À espera de clientes");
                Socket clientSocket = serverSocket.accept();
                executorService.execute(new CalcClientThread(clientSocket));
            }

        } catch (IOException e) {
            System.err.println("Erro ao criar o ServerSocket");
        }

        sc.close();
    }

}
