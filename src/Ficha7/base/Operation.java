package Ficha7.base;

import Ficha7.operations.*;

public class Operation implements IOperate {

    protected float val1;
    protected float val2;

    public void setVal1(float val1) {
        this.val1 = val1;
    }

    public void setVal2(float val2) {
        this.val2 = val2;
    }

    public float getVal1() {
        return val1;
    }

    public float getVal2() {
        return val2;
    }

    public static Operation[] operationsAvailable = {
            new Addition("+", "add"),
            new Subtraction("-"),
            new Division("/", ":"),
            new Potential("**"),
            new Multiplication("*", "x", "X"),
    };

    protected String[] symbols;

    public String[] getSymbols() {
        return symbols;
    }

    @Override
    public float operate(float val1, float val2) {
        return 0;
    }
}
