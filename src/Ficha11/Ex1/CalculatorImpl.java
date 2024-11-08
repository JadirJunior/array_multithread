package Ficha11.Ex1;

import Ficha11.Numbers;

public class CalculatorImpl implements Calculator{
    @Override
    public float add(Numbers numbers) {
        return numbers.a + numbers.b;
    }

    @Override
    public float sub(Numbers numbers) {
        return numbers.a - numbers.b;
    }

    @Override
    public float multi(Numbers numbers) {
        return numbers.a * numbers.b;
    }

    @Override
    public float div(Numbers numbers) {
        return numbers.b == 0 ? Float.NaN : (numbers.a/numbers.b);
    }
}
