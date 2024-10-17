package Ficha6.Ex1;

import Ficha6.Numbers;
import utils.InputValidation;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) {
        int port = 6000;

        Scanner sc = new Scanner(System.in);

        int min = InputValidation.validateInt(sc, "Introduza o valor mínimo a colocar no array\n:> ");
        int max = InputValidation.validateIntGTN(sc, "Introduza o valor máximo a colocar no array\n:> ", min-1);
        int size = InputValidation.validateIntGT0(sc, "Introduza o tamanho do array\n:> ");
        sc.close();
        Numbers numbers = new Numbers(min, max, size);

        System.out.println("Objeto a enviar: \n" + numbers);
        while (true) {

            try (
                    ServerSocket serverSocket = new ServerSocket(port);
                    Socket clientSocket = serverSocket.accept();
                    ObjectOutputStream outObj = new ObjectOutputStream(clientSocket.getOutputStream());
                    ) {

                //Envia os dados
                outObj.writeObject(numbers);
                outObj.flush();

            } catch (IOException e) {
                System.out.println("Main: Ocorreu um erro ao criar o ServerSocket");
                System.exit(1);
            }

        }

    }

}
