package Ficha7.operations;

import Ficha7.base.Operation;

public class Subtraction extends Operation {

    public Subtraction(String... addSymbols) {
        this.symbols = addSymbols;
    }

    @Override
    public float operate() {
        return val1-val2;
    }

}
