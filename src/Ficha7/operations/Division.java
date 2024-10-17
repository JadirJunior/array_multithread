package Ficha7.operations;

import Ficha7.base.Operation;

public class Division extends Operation {
    public Division(String... addSymbols) {
        this.symbols = addSymbols;
    }

    @Override
    public float operate(float val1, float val2) {
        if (val2 == 0) return Float.NaN;
        return val1/val2;
    }
}
