package Ficha4;

import utils.InputValidation;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ThreadedEchoServer {
    public static void main(String[] args) {

        try (
                Scanner sc = new Scanner(System.in)
                ) {

            int portNumber = InputValidation.validateIntBetween(
                    sc,
                    "Introduza o número da porta que o servidor irá escutar (1024...65535): ",
                    1025,
                    65535);


            try (
                    ServerSocket serverSocket = new ServerSocket(portNumber);
            ) {

                while (true) {
                    System.out.println("Servidor à espera de ligação");
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Cliente ligado");
                    new EchoThread(clientSocket).start();
                }

            } catch (IOException e) {
                System.err.println("Ocorreu um erro de I/O ao criar o socket");
                System.exit(2);
            }
        } catch (Exception e) {
            System.err.println("Ocorreu um erro");
            System.exit(4);
        }
    }
}
