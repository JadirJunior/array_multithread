package Trabalho.server;

import Trabalho.utils.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.Semaphore;

public class ServerThread extends Thread{


    private Socket socket;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {

        System.out.println("Thread " + this.getName() + ": Entrou no jogo");
        try (
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
                ) {

            User logged = null;
            do {
                String username = in.readLine();
                String password = in.readLine();

                Optional<User> optionalUser = Server.file.getUserByUserName(new User(username, password, false));

                if (optionalUser == null) {
                    out.println("Esse usuário já está logado ou logou-se nesta partida.");
                } else if (optionalUser.isPresent()) {
                    logged = optionalUser.get();
                    System.out.println("Thread " + this.getName() + ": " + logged.getUser() + " autenticou-se");
                    out.println("ok");
                } else {
                    out.println("Credenciais incorretas.");
                }
            } while (logged == null);


            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    System.out.println("Thread " + this.getName() + ": O jogador será informado que o jogo começará");
                    out.println("O jogo começou, faça seus palpites!");
                }
            }

            String actualWord = "-".repeat(Server.WORD.length());
            StringBuilder sb = new StringBuilder(actualWord);
            //O jogo começou!
            while (true) {
                out.println(sb);

                String guess = in.readLine();

                if (Server.GAME_END) {

                    if (Server.WINNER.trim().equalsIgnoreCase("")) {
                        out.println("O tempo para o fim do jogo se esgotou e ninguém venceu.");
                    } else {
                        out.println("O jogo finalizou com a vitória de " + Server.WINNER + " (" + Server.WORD + ")");
                    }
                    break;
                }

                boolean isWinner = false, correct = false;
                if (guess.length() == 1) {
                    //Enviou apenas uma letra
                    for (int i = 0; i < Server.WORD.length(); i++) {
                        if (Server.WORD.charAt(i) == guess.charAt(0)) {
                            //Caso essa letra exista na palavra escolhida pelo servidor
                            if (sb.charAt(i) == '-') {
                                //Caso essa letra ainda não tenha sido escolhida
                                sb.setCharAt(i, guess.charAt(0));
                                correct = true;
                            } else {
                                //Caso tenha enviado uma letra repetida
                                break;
                            }
                        }
                    }

                    if (sb.toString().equals(Server.WORD)) {
                        isWinner = true;
                    }
                } else {
                    //Enviou a palavra inteira
                    if (Server.WORD.equals(guess)) {
                        isWinner = true;
                        sb = new StringBuilder(guess);
                    }
                }

                System.out.println("Thread " + this.getName() + ": O user " + logged.getUser() + " enviou o palpite: " + guess);
                if (isWinner) {
                    Server.GAME_END = true;
                    Server.WINNER = logged.getUser();
                    out.println("winner");
                    System.out.println("Thread " + this.getName() + ": O user " + logged.getUser() + " enviou o palpite: " + guess + " e venceu!");
                } else if (correct) {
                    out.println("correct");
                    System.out.println("Thread " + this.getName() + ": O user " + logged.getUser() + " enviou o palpite: " + guess + " e estava correto (" + Server.WORD + ")");
                } else {
                    out.println("incorrect");
                    System.out.println("Thread " + this.getName() + ": O user " + logged.getUser() + " enviou o palpite: " + guess + " e estava incorreto (" + Server.WORD + ")");
                }

            }

        } catch (IOException e) {
            System.err.println("Thread " + this.getName() + ": Ocorreu um erro de I/O");
        }

    }

}
