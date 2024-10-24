package Ficha7.operations;

import Ficha7.base.Operation;

public class Division extends Operation {
    public Division(String... addSymbols) {
        this.symbols = addSymbols;
    }

    @Override
    public float operate() {
        if (val2 == 0) return Float.NaN;
        return val1/val2;
    }
}
