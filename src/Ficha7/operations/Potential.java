package Ficha7.operations;

import Ficha7.base.Operation;

public class Potential extends Operation {
    public Potential(String... addSymbols) {
        this.symbols = addSymbols;
    }

    @Override
    public float operate(float val1, float val2) {
        return (float) Math.pow(val1, val2);
    }
}
