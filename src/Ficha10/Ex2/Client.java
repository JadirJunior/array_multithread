package Ficha10.Ex2;

import Ficha10.Ex1.Hello;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {
    public static void main(String[] args) {
        try {
            Hello stub = (Hello) Naming.lookup("rmi://localhost:1099/Hello");
            String response = stub.sayHello();

            System.out.println(response);

        } catch (NotBoundException e) {
            System.err.println("O nome não está registrado no servidor RMI");
        } catch (MalformedURLException e) {
            System.err.println("URL mal formatada");
        } catch (RemoteException e) {
            System.err.println("Ocorreu um erro com a conexão com o registro RMI");
        }
    }
}
