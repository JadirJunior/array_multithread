package Ficha12.Ex1;

import Ficha12.ClientInterface;
import Ficha12.ServerInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Server extends UnicastRemoteObject implements ServerInterface {
    static ArrayList<User> users = new ArrayList<>();




    protected Server() throws RemoteException {
        super();
    }

    public static void main(String[] args) {
        try (
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in))
                ) {
            Server server = new Server();
            LocateRegistry.createRegistry(6666);
            Naming.rebind("rmi://localhost:6666/message", server);

            System.out.println("Servidor pronto para receber clientes");

            String messageToClients;
            while (true) {
                System.out.print("Introduza a mensagem que quer enviar a todos os clientes\n:> ");
                messageToClients = br.readLine();

                for (User user : users) {
                    user.clientInterface.printOnClient(messageToClients);
                }
            }

        } catch (IOException e) {
            System.err.println("Erro ao tentar ler o texto introduzido");
        }
    }

    @Override
    public void printOnServer(String clientName, String messageFromClient) throws RemoteException {

        if (messageFromClient.equalsIgnoreCase("sair")) {

            for (User user : users) {
                if (user.clientName.equals(clientName)) {
                    users.remove(user);
                    break;
                }
            }

            System.out.println("O cliente " + clientName + " saiu");

        } else {
            System.out.println("O cliente " + clientName + " enviou: " + messageFromClient);
        }

        System.out.print("Introduza a mensagem que quer enviar a todos os clientes: \n:> ");


    }

    @Override
    public String subscribe(String clientName, ClientInterface clientInterface) throws RemoteException {

        for (User user : users) {
            if (user.clientName.equalsIgnoreCase(clientName)) {
                return "Nome já utilizado. Por favor introduza um nome diferente";
            }
        }

        System.out.println("Novo cliente registrado no servidor: " + clientName);

        users.add(new User(clientName, clientInterface));

        System.out.print("Introduza a mensagem que quer enviar a todos os clientes: \n:> ");

        return "Client " + clientName + " registrado no servidor";
    }
}
