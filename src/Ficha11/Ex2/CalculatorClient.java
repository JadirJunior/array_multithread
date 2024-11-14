package Ficha11.Ex2;

import Ficha11.Ex1.Calculator;
import Ficha11.Numbers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.NotBoundException;

public class CalculatorClient {

    public static void main(String[] args) {

        try (
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in))
                ) {
            Calculator calculador = (Calculator) Naming.lookup("rmi://localhost:6666/calculator");

            while (true) {
                System.out.println("Introduza \"Sair\" para terminar, ou a operação de cálculo efetuar: " +
                        "<operando1> <operador> <operando2>");

                String s = br.readLine();

                if (s.equalsIgnoreCase("sair")) {
                    break;
                }

                String[] pars = s.split(" ");

                if (pars.length != 3) {
                    System.out.println("Sintaxe inválida");
                    continue;
                }

                Numbers numbers = new Numbers();
                char op;

                try {
                    numbers.a = Float.parseFloat(pars[0]);
                    numbers.b = Float.parseFloat(pars[2]);
                    op = pars[1].charAt(0);
                } catch (NumberFormatException e) {
                    System.out.println("Sintaxe inváida");
                    continue;
                }

                switch (pars[1]) {
                    case "+":
                        System.out.println(s + " = " + calculador.add(numbers));
                        break;
                    case "-":
                        System.out.println(s + " = " + calculador.sub(numbers));
                        break;
                    case "*", "x", "X":
                        System.out.println(s + " = " + calculador.multi(numbers));
                        break;
                    case "/", ":":
                        System.out.println(s + " = " + calculador.div(numbers));
                        break;
                    default:
                        System.out.println("Operador inválido");
                        break;
                }
            }

        } catch (IOException e) {
            System.err.println("Ocorreu um erro ao tentar ler o texto introduzido pelo utilizador");
        } catch (NotBoundException e) {
            System.err.println("Erro ao tentar encontrar o servidor rmi");
        }

    }


}
