package Ficha7.operations;

import Ficha7.base.Operation;

public class Multiplication extends Operation {
    public Multiplication(String... addSymbols) {
        this.symbols = addSymbols;
    }

    @Override
    public float operate(float val1, float val2) {
        return val1*val2;
    }
}
