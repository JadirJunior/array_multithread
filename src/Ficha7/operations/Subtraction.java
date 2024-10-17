package Ficha7.operations;

import Ficha7.base.Operation;

public class Subtraction extends Operation {

    public Subtraction(String... addSymbols) {
        this.symbols = addSymbols;
    }

    @Override
    public float operate(float val1, float val2) {
        return val1-val2;
    }

}
