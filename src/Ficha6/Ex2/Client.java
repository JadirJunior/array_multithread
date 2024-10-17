package Ficha6.Ex2;


import Ficha6.Numbers;
import utils.InputValidation;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        int port = 6000;
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduza o hostname do servidor ao qual se vai ligar: ");
        String hostname = sc.nextLine();


        while (true) {
            try (
                    Socket clientSocket = new Socket(hostname, port);
                    ObjectInputStream objIn = new ObjectInputStream(clientSocket.getInputStream());
                    ) {

                Numbers numbers = (Numbers) objIn.readObject();
                System.out.println(numbers);

                int mult = InputValidation.validateInt(sc, "Introduza o valor pelo qual deseja multiplicar os elementos do array: \n");
                numbers.multiply(mult);
                System.out.println(numbers);

            } catch (UnknownHostException e) {
                System.err.println("Servidor desconhecido");
                System.exit(2);
            } catch(IOException e) {
                System.err.println("Ocorreu um erro ao iniciar o socket.");
                System.exit(3);
            } catch (ClassNotFoundException e) {
                System.err.println("Tipo de objeto não conhecido");
                System.exit(4);
            } catch (ClassCastException e) {
                System.err.println("Classe errada");
                System.exit(5);
            } finally {
                sc.close();
            }
        }
    }
}
