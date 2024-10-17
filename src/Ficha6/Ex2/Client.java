package Ficha6.Ex2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        int port = 6000;

        try (
                Socket clientSocket = new Socket("localhost", port);
                ) {

        } catch(IOException e) {
            System.err.println("Ocorreu um erro ao iniciar o socket.");
        }
    }
}
