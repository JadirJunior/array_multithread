package Ficha7.Ex1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CalcClientThread extends Thread{

    private final Socket clientSocket;

    public CalcClientThread(Socket clientSocket) {
        System.out.println(this.getName() + ": liga��o efetuada com o cliente");
        this.clientSocket = clientSocket;
    }

    public void run() {
        try (
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
                ) {

            out.println("Conectou-se ao servidor!");

        } catch (IOException e) {
            System.err.println(this.getName() + ": Erro ao criar os buffers de comunica��o");
        }

        System.out.println(this.getName() + ": Liga��o terminada com o cliente");
    }


}
