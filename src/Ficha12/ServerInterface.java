package Ficha12;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
    void printOnServer(String clientName, String messageFromClient) throws RemoteException;
    String subscribe(String clientName, ClientInterface clientInterface) throws RemoteException;
}
