package Ficha7.operations;

import Ficha7.base.Operation;

public class Addition extends Operation {

    public Addition(String... addSymbols) {
        this.symbols = addSymbols;
    }

    @Override
    public float operate() {
        return val1+val2;
    }
}
