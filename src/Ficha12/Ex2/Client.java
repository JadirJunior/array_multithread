package Ficha12.Ex2;

import Ficha12.ClientInterface;
import Ficha12.ServerInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLOutput;

public class Client extends UnicastRemoteObject implements ClientInterface {
    protected Client() throws RemoteException {
        super();
    }

    public static void main(String[] args) {
        try (
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                ) {
            ServerInterface serverInterface = (ServerInterface) Naming.lookup("rmi://localhost:6666/message");
            Client client = new Client();

            String name, messageFromServer;
            String messageToServer = "";

            do {
                System.out.print("Qual o nome com que se deseja registrar no servidor\n:> ");
                name = br.readLine();

                messageFromServer = serverInterface.subscribe(name, client);
                System.out.println(messageFromServer);
            } while (messageFromServer.equals("Nome já utilizado. Por favor introduza um nome diferente"));

            while (!messageToServer.equalsIgnoreCase("Sair")) {
                System.out.print("Introduza a mensagem que quer enviar ao servidor: \n:> ");
                messageToServer = br.readLine();

                System.out.println(messageToServer);

                serverInterface.printOnServer(name, messageToServer);
            }

            System.exit(0);


        } catch (IOException e) {
            System.err.println("Erro ao tentar ler o texto introduzido");
        } catch (NotBoundException e) {
            System.err.println("O nome não está registrado no servidor RMI");
        }
    }

    @Override
    public void printOnClient(String messageFromServer) throws RemoteException {
        System.out.println("O servidor enviou: " + messageFromServer);
        System.out.println("Introduza a mensagem para enviar ao servidor: \n:> ");
    }
}
