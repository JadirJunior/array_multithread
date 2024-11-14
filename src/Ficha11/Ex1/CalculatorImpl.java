package Ficha11.Ex1;

import Ficha11.Numbers;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class CalculatorImpl extends UnicastRemoteObject implements Calculator{
    protected CalculatorImpl() throws RemoteException {
        super();
    }

    @Override
    public float add(Numbers numbers) {
        return numbers.a + numbers.b;
    }

    @Override
    public float sub(Numbers numbers) {
        return numbers.a - numbers.b;
    }

    @Override
    public float multi(Numbers numbers) {
        return numbers.a * numbers.b;
    }

    @Override
    public float div(Numbers numbers) {
        return numbers.b == 0 ? Float.NaN : (numbers.a/numbers.b);
    }

    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(6666);
            CalculatorImpl calculator = new CalculatorImpl();
            Naming.rebind("rmi://localhost:6666/calculator", calculator);
            System.out.println("Calculadora preparada");
        } catch (RemoteException e) {
            System.err.println("Erro ao tentar exportar o objeto");
        } catch (MalformedURLException e) {
            System.err.println("URL mal definido");
        }
    }
}
