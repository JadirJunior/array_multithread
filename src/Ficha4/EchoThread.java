package Ficha4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EchoThread extends Thread {
    private final Socket clientSocket;

    public EchoThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {


        try (
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
                ){

            String fromClient;

            do {
                System.out.println("Thread " + this.getName() + ": À espera de uma mensagem");
                fromClient = in.readLine();
                System.out.println("Thread " + this.getName() + ": Recebeu a mensagem " + fromClient);
                out.println("Thread " + this.getName() + ": A mensagem tem " + fromClient.length() + " caracteres");

            } while (!fromClient.equalsIgnoreCase("Adeus"));

            System.out.println("Thread " + this.getName() + ": Terminou");


        } catch (IOException e) {
            System.err.println("Thread " + this.getName() + ": Ocorreu um erro de I/O ao criar os canais de comunicação");
        }

    }
}
