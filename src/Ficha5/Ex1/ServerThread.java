package Ficha5.Ex1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread{

    private final Socket clientSocket;

    public ServerThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try (
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
                ) {
            int value = Integer.parseInt(in.readLine());
            int currentChips = value / 5;

            System.out.println("Thread " + this.getName() + ": O cliente investiu " + value + " euros ("
                    +currentChips+")");

            out.println("Fichas: " + currentChips);
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Thread " + this.getName() + ": vai ser informado que o jogo vai começar");
                out.println("Terminou o período para aceitar novos jogadores. Faça a sua aposta");
            }

            int number, chipsToBet;
            while (true) {
                number = Integer.parseInt(in.readLine());
                System.out.println("Thread " + this.getName() +": O jogador quer apostar no número " + number);
                chipsToBet = Integer.parseInt(in.readLine());
                System.out.println("Thread " + this.getName() +": O jogador quer apostar "
                        + chipsToBet + " fichas no número " + number);

            }

        } catch (IOException e) {
            System.err.println("Thread " + this.getName() + ": Erro de criação dos buffers do socket");
            System.exit(3);
        }
    }

}
