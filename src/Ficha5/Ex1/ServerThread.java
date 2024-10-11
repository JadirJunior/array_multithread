package Ficha5.Ex1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.Phaser;

public class ServerThread extends Thread{

    private final Socket clientSocket;

    private final Phaser phaser;

    public ServerThread(Socket clientSocket, Phaser phaser) {
        this.clientSocket = clientSocket;
        this.phaser = phaser;

        phaser.register(); //Thread aguarda pelo Phaser
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

            out.println(currentChips);


            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    System.out.println("Thread " + this.getName() + ": vai ser informado que o jogo vai come�ar");
                    out.println("Terminou o per�odo para aceitar novos jogadores. Fa�a a sua aposta");
                }
            }


            int number, chipsToBet;
            while (true) {
                number = Integer.parseInt(in.readLine());
                System.out.println("Thread " + this.getName() +": O jogador quer apostar no n�mero " + number);
                chipsToBet = Integer.parseInt(in.readLine());
                System.out.println("Thread " + this.getName() +": O jogador quer apostar "
                        + chipsToBet + " fichas no n�mero " + number);

                currentChips -= chipsToBet;

                Random rand = new Random();
                Server.EXTRACTED_NUMBER = rand.nextInt(1, 37);
                phaser.arriveAndAwaitAdvance(); //Baseado no n�mero de Threads para registro



                System.out.println("Thread " + this.getName() +": O n�mero extra�do foi " + Server.EXTRACTED_NUMBER);
                out.println(Server.EXTRACTED_NUMBER);

                if (Server.EXTRACTED_NUMBER == number) {
                    currentChips += chipsToBet*35;
                }

                out.println(currentChips);

                String text = in.readLine();

                if (text.equalsIgnoreCase("Sair")) {
                    System.out.println("Thread " + this.getName() + ": O cliente saiu do jogo");
                    break;
                } else {
                    System.out.println("Thread " + this.getName() + ": O cliente vai continuar a jogar");
                }

                if (Server.game_ended) {
                    System.out.println("Thread " + this.getName() + ": O jogo terminou");
                    out.println("Sair");
                    break;
                } else {
                    out.println("Continuar");
                }
            }

        } catch (IOException e) {
            System.err.println("Thread " + this.getName() + ": Erro de cria��o dos buffers do socket");
            phaser.arriveAndDeregister();
            System.exit(3);
        }

        phaser.arriveAndDeregister();

    }

}
