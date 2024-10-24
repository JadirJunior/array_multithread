package Ficha7.Ex1;

import Ficha7.Calculator;
import Ficha7.base.Operation;

import javax.swing.text.html.Option;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Optional;

public class CalcClientThread extends Thread{

    private final Socket clientSocket;

    public CalcClientThread(Socket clientSocket) {
        System.out.println(this.getName() + ": ligação efetuada com o cliente");
        this.clientSocket = clientSocket;
    }

    public void run() {
        try (
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
                ) {

            while (true) {
                String expression = in.readLine();
                if (expression.equalsIgnoreCase("sair")) {
                    out.println("Até breve...");
                    break;
                }
                Optional<Operation> op = Calculator.validateExpression(expression);

                if (op.isEmpty()) {
                    out.println("Operação não foi bem sucedida");
                } else {
                    Operation operation = op.get();
                    float res = operation.operate();
                    out.println("Resultado: " + expression+" = "+res);
                }
            }


        } catch (IOException e) {
            System.err.println(this.getName() + ": Erro ao criar os buffers de comunicação");
        }

        System.out.println(this.getName() + ": Ligação terminada com o cliente");
    }


}
