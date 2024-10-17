package Ficha7.operations;

import Ficha7.base.Operation;

public class Addition extends Operation {

    public Addition(String... addSymbols) {
        this.symbols = addSymbols;
    }

    @Override
    public float operate(float val1, float val2) {
        return val1+val2;
    }
}
