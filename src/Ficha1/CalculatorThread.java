package Ficha1;

public class CalculatorThread extends Thread {

    private final double[] array;
    private final int mult;
    private final int init;
    private final int end;


    public CalculatorThread(double[] array, int mult, int init, int end) {
        this.array = array;
        this.mult = mult;
        this.init = init;
        this.end = end;
    }

    @Override
    public void run() {

        for (int i = init; i < end; i++) {

            array[i] = Math.cos(array[i]);

            // Não utilizar Math.pow para demorar mais tempo
            for (int j = 0; j < mult; j++) {
                array[i] *= Math.cos(array[i]);
            }
        }
    }
}
