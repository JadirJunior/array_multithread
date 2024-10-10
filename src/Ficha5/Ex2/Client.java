package Ficha5.Ex2;

import utils.InputValidation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Introduza o hostname do servidor ao qual vai se ligar: ");
        String hostname = sc.nextLine();

        int portNumber = InputValidation.validateIntBetween(
                sc,
                "Introduza o número da porta do servidor que irá se conectar (1024...65535): ",
                1024, 65535);

        try (
                Socket clientSocket = new Socket(hostname, portNumber);
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
                ){

            int value=0;
            do {
                System.out.print("\n\nIndique o montante de Euros que deseja investir. "
                        + " O valor tem que ser um múltiplo de 5\n :>");

                String texto = sc.nextLine();

                try {
                    value = Integer.parseInt(texto);
                } catch (NumberFormatException e) {
                    System.err.println("Número inválido: " + texto);
                }
            } while (value < 5 || value % 5 != 0);

            out.println(value);
            int currentChips = Integer.parseInt(in.readLine());
            System.out.println("Número de fichas investidas: " + currentChips);

            System.out.println(in.readLine());
            int number, chipsToBet;
            while (true) {
                number = InputValidation.validateIntBetween(sc, "Introduza o número que " +
                        "quer apostar. Tem de ser um número entre 1 e 36\n :>", 1 ,36);

                out.println(number);
                System.out.println("Atualmente tem " + currentChips + " fichas");

                chipsToBet = InputValidation.validateIntBetween(sc, "Introduza o número de fichas que " +
                        "quer apostar no número "+number+"\n :>", 1 , currentChips);
                out.println(chipsToBet);

                currentChips -= chipsToBet;


            }

        } catch (UnknownHostException e) {
            System.err.println("Host desconhecido: " + hostname);
            System.exit(4);
        } catch (IOException e) {
            System.err.println("Ocorreu um erro  de I/O ao tentar se conectar ao servidor.");
            System.exit(5);
        } finally {
            sc.close();
        }

    }
}
