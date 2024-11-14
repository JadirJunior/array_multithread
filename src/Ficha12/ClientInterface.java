package Ficha12;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
    void printOnClient(String messageFromServer) throws RemoteException;
}
