package Ficha6;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

public class Numbers implements Serializable {

    private int min;
    private int max;
    private int size;

    private int[] array;

    public Numbers(int min, int max, int size) {
        this.min = min;
        this.max = max;
        this.size = size;

        array = new int[size];
        Random rand = new Random();

        for (int i=0; i<array.length;i++) {
            array[i] = rand.nextInt(min, max+1);
        }
    }

    public void multiply(int mult) {
        for (int i = 0; i < size;i++) {
            array[i] *= mult;
        }
        min *= mult;
        max *= mult;

        if (min < max) {
            int aux = min;
            min = max;
            max = aux;
        }
    }

    @Override
    public String toString() {
        return "\nMínimo: " + min + "\nMáximo: " + max + "\nTamanho: " + size + "\nArray: " + Arrays.toString(array);
    }

}
