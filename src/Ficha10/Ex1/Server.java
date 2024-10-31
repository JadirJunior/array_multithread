package Ficha10.Ex1;


import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

class HelloWorld implements Hello {

    @Override
    public String sayHello() {
        System.out.println("O método sayHello foi chamado");
        return "Hello World!";
    }
}

public class Server {

    public static void main(String[] args) {
        try {
            HelloWorld helloWorld = new HelloWorld();
            Hello stub = (Hello) UnicastRemoteObject.exportObject(helloWorld, 0);
            LocateRegistry.createRegistry(1099);
            Naming.rebind("Hello", stub);

        } catch (RemoteException e) {
            System.err.println("Erro ao tentar exportar o objeto");
        } catch (MalformedURLException e) {
            System.err.println("URL mal definido");
        }
    }

}
