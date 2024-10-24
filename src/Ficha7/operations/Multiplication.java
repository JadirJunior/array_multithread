package Ficha7.operations;

import Ficha7.base.Operation;

public class Multiplication extends Operation {
    public Multiplication(String... addSymbols) {
        this.symbols = addSymbols;
    }

    @Override
    public float operate() {
        return val1*val2;
    }
}
