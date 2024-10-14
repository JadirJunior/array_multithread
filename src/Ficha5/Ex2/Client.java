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
                "Introduza o n�mero da porta do servidor que ir� se conectar (1024...65535): ",
                1024, 65535);

        try (
                Socket clientSocket = new Socket(hostname, portNumber);
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
                ){

            int value=0;
            do {
                System.out.print("\n\nIndique o montante de Euros que deseja investir. "
                        + " O valor tem que ser um m�ltiplo de 5\n :>");

                String texto = sc.nextLine();
                try {
                    value = Integer.parseInt(texto);
                } catch (NumberFormatException e) {
                    System.err.println("N�mero inv�lido: " + texto);
                }
            } while (value < 5 || value % 5 != 0);

            out.println(value);
            int currentChips = Integer.parseInt(in.readLine());
            System.out.println("N�mero de fichas investidas: " + currentChips);

            System.out.println(in.readLine());

            String text;
            int number, chipsToBet, extractedNumber;

            while (true) {
                number = InputValidation.validateIntBetween(sc, "Introduza o n�mero que " +
                        "quer apostar. Tem de ser um n�mero entre 1 e 36\n :>", 1 ,36);

                out.println(number);
                System.out.println("Atualmente tem " + currentChips + " fichas");

                chipsToBet = InputValidation.validateIntBetween(sc, "Introduza o n�mero de fichas que " +
                        "quer apostar no n�mero "+number+"\n :>", 1 , currentChips);
                out.println(chipsToBet);

                extractedNumber = Integer.parseInt(in.readLine());
                currentChips = Integer.parseInt(in.readLine());

                if (extractedNumber == number) {
                    System.out.println("Parab�ns, acertou o n�mero. \nFicou com " + currentChips + " fichas!");
                } else if (currentChips == 0) {
                    System.out.println("O n�mero extra�do foi " + extractedNumber);
                    System.out.println("N�o tem mais fichas para apostar. Adeus.");
                    out.println("Sair");
                    break;
                } else {
                    System.out.println("O n�mero extra�do foi " + extractedNumber);
                    System.out.println("Parece que n�o foi desta vez! Ainda possui " + currentChips + " fichas.");
                }

                do {
                    System.out.print("Deseja fazer mais uma aposta? (S/N)\n :> ");
                    text = sc.nextLine();
                } while ((!text.equalsIgnoreCase("S")) && (!text.equalsIgnoreCase("N")));

                if (text.equalsIgnoreCase("N")) {
                    //Sair
                    System.out.println("Obrigado por jogar. At� breve.");
                    out.println("Sair");
                    break;
                } else {
                    //Continuar
                    out.println("Continuar");
                }

                if (in.readLine().equalsIgnoreCase("Sair")) {
                    System.out.println("O jogo terminou.");
                    System.out.println("Obrigado por jogar. At� breve.");
                    break;
                }


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
