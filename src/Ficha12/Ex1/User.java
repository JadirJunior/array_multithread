package Ficha12.Ex1;

import Ficha12.ClientInterface;

public class User {
    ClientInterface clientInterface;
    String clientName;

    public User(String clientName, ClientInterface clientInterface) {
        this.clientInterface = clientInterface;
        this.clientName = clientName;
    }
}
