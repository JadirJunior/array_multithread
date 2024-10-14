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

        } catch (IOException e) {
            System.err.println("Thread " + this.getName() + ": Ocorreu um erro de I/O");
        }

    }

}
