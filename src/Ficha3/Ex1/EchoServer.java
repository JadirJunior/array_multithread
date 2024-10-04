package Ficha3.Ex1;

import utils.InputValidation;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class EchoServer {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int portNumber = InputValidation.validateIntBetween(
                sc,
                "Introduza o número da porta que o servidor irá escutar (1024...65535): ",
                1025,
                65535);

        System.out.println("Servidor à espera de ligação");

        try (
                //Essa sintaxe vai garantir que mesmo que dê erro as instâncias serão fechadas
                //Apenas alguns tipos de objeto são aceitos aqui
                ServerSocket serverSocket = new ServerSocket(portNumber);
                Socket clientSocket = serverSocket.accept();
                //Escrever ao socket ligado ao cliente
                //autoFlush para limpar o buffer toda vez
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                //Leitor de mensagnes
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
                ) {

            String fromClient;

            System.out.println("Cliente ligado");

            do {
                fromClient = in.readLine();
                System.out.println("O client enviou uma mensagem " + fromClient);

                out.println("A mensagem tem " + fromClient.length() + " caracteres");

            } while (!fromClient.equalsIgnoreCase("Adeus"));

        } catch (IOException e) {
            System.err.println("Ocorreu um erro de I/O ao criar o socket ou ler a mensagem");
            System.exit(2);
        }


        sc.close();
    }
}
