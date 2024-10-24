package Ficha7;

import Ficha7.base.Operation;
import Ficha7.operations.Addition;
import Ficha7.operations.Division;
import Ficha7.operations.Multiplication;
import Ficha7.operations.Subtraction;

import java.util.Optional;

public class Calculator {

    private static Operation[] operationsAvailable = {
            new Addition("+", "add"),
            new Subtraction("-"),
            new Division("/", ":"),
            new Multiplication("*", "x", "X"),
    };

    public static Optional<Operation> validateExpression(String expression) {
        Operation[] operatorsAvailable = operationsAvailable;

        String[] separated = expression.split(" ");

        if (separated.length != 3) return Optional.empty();

        float op1, op2;
        try {
            op1 = Float.parseFloat(separated[0]);
            op2 = Float.parseFloat(separated[2]);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }

        String op = separated[1];

        Operation operation = null;

        for (int i = 0; i < operatorsAvailable.length; i++) {
            boolean stop = false;
            for (int j = 0; j < operatorsAvailable[i].getSymbols().length; j++) {
                String actualSymbol = operatorsAvailable[i].getSymbols()[j];
                if (actualSymbol.equalsIgnoreCase(op)) {
                    //Operação encontrada
                    operation = operatorsAvailable[i];
                    stop= true;
                    break;
                }
            }
            if (stop) break;
        }

        if (operation == null) return Optional.empty();

        operation.setVal1(op1);
        operation.setVal2(op2);

        return Optional.of(operation);

    }

}
