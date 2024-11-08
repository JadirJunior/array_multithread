package Ficha11.Ex1;

import Ficha11.Numbers;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Calculator extends Remote {

    float add(Numbers numbers) throws RemoteException;
    float sub(Numbers numbers) throws RemoteException;
    float multi(Numbers numbers) throws RemoteException;
    float div(Numbers numbers) throws RemoteException;

}
