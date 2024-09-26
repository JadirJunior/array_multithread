import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
// programa multithread que crie um array de números (double) aleatórios e que,
// posteriormente aplique uma potência ao cosseno de cada um dos seus elementos

//utilizar InputValidation e Array
//Intervalo de Valores
// n array
// o A potência a aplicar ao cosseno de cada um dos elementos do array;
//o O número de threads a utilizar para efetuar os cálculos.

public class Main {

    public static double process(double[] chunk, int power) {
        double sum = 0;
        //executar o cosseno[
        for (var item : chunk) {
            item = Math.pow(Math.cos(item), power);
            sum += item;
        }

        return sum;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Input Min Value: ");
        double min = sc.nextDouble();

        System.out.print("Input Max Value: ");
        double max = sc.nextDouble();

        System.out.print("Array Size: ");
        int vectorSize = sc.nextInt();

        System.out.print("Pow on Cosine: ");
        int power = sc.nextInt();

        System.out.print("Threads: ");
        int numThreads = sc.nextInt();

        //instance vectors
        double[] vector = new double[vectorSize];
        double[] copyVector = new double[vectorSize];

        //randomize vector
        for (int i = 0; i < vectorSize; i++) {
            vector[i] = new Random().nextDouble() * (max - min) + min;
        }

        //copy vector
        copyVector = vector.clone();

        try {

            if (numThreads <= 0) {
                return;
            }

            // single
            long startSingleThread = System.currentTimeMillis();
            ExecutorService threadExecutor = Executors.newCachedThreadPool();

            threadExecutor.submit(new Runnable() {

                @Override
                public void run() {
                    double sumSingleThread = 0.0;
                    for (var item : vector) {
                        item = Math.pow(Math.cos(item), power);
                        sumSingleThread += item;
                    }

                    long endSingleThread = System.currentTimeMillis();
                    System.out.println("Sum Single Threads: " + sumSingleThread);
                    System.out.println("Time Single Threads: " + (endSingleThread - startSingleThread) + " ms");
                }
            });

            //multiple
            long startMultiThread = System.currentTimeMillis();
            ExecutorService executor = Executors.newFixedThreadPool(numThreads);
            List<Future<Double>> results = new ArrayList<>();
            int chunkSize = (int) Math.ceil(vectorSize / (double) numThreads);

            for (int i = 0; i < numThreads; i++) {
                int start = i * chunkSize;
                int end = Math.min(start + chunkSize, vectorSize);

                double[] chunk = new double[end - start];

                System.arraycopy(copyVector, start, chunk, 0, chunk.length);

                results.add(executor.submit(() -> process(chunk, power)));

            }

            double sumMultiThread = 0.0;

            for (var result : results) {
                sumMultiThread += result.get();
            }

            long endMultiThread = System.currentTimeMillis();

            executor.shutdown();
            threadExecutor.shutdown();

            System.out.println("Sum Multiple Threads: " + sumMultiThread);
            System.out.println("Time Multiple Threads: " + (endMultiThread - startMultiThread) + " ms\n");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

}