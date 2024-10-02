package Ficha1;

import utils.Array;
import utils.InputValidation;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        double[] randomArrayOriginal = Array.newDoubleRandomArray(sc);
        double[] randomArrayCopy = Arrays.copyOf(randomArrayOriginal, randomArrayOriginal.length);

        int mult = InputValidation.validateIntGT0(sc, "Introduza a pot�ncia a aplicar a cada elemento do array: ");

        int nThreads = InputValidation.validateIntGT0(sc, "Introduza o n�mero de threads a utilizar: ");

        sc.close();

        CalculatorThread thread = new CalculatorThread(randomArrayOriginal, mult, 0, randomArrayOriginal.length);

        long startTime = System.nanoTime();

        thread.start();

        try {
            thread.join();

        } catch (InterruptedException e) {
            System.out.println("A thread foi interrompida");
        }

        long estimatedTime = System.nanoTime() - startTime;

        double sum = 0;
        for (double v : randomArrayOriginal) {
            sum += v;
        }

        System.out.println("\nResultado final com 1 thread: " + sum);
        System.out.println("Tempo de execu��o com 1 thread: " + estimatedTime / 1E9 + "s");

        // n�mero de threads a executar
        ExecutorService executor = Executors.newFixedThreadPool(nThreads);

        int subElem = randomArrayCopy.length / nThreads;

        startTime = System.nanoTime();

        // n�mero de tarefas a serem executadas pelas threads
        int i;
        for (i = 0; i < nThreads - 1; i++) {
            CalculatorThread worker = new CalculatorThread(randomArrayCopy, mult, i * subElem, (i + 1) * subElem);
            executor.execute(worker);
        }
        CalculatorThread worker = new CalculatorThread(randomArrayCopy, mult, i * subElem, randomArrayCopy.length);
        executor.execute(worker);

        // n�o bloqueia � espera que as threads terminem, mas faz com que deixem de aceitar novas tarefas
        // envia sinal �s mesmas a dizer que foram interrompidas, mas se elas n�o estiverem � espera desse sinal,
        // isso n�o serve de nada. Podem esperar por esse sinal no wait() com syncronized
        // isto coloca tamb�m a flag Thread.currentThread().isInterrupted() de cada thread a true
        executor.shutdownNow();

        try {
            // espera que as threads terminem, mas se as threads n�o terminarem at� passarem 30s, o main continua,
            // mas as threads ficar�o a ser executadas at� terminarem e s� depois o programa termina.
            // para as threads serem obrigadas a terminar ap�s 30s (caso n�o terminarem antes), as mesmas teriam de apanhar InterruptedException
            // e/ou vericar o estado de Thread.currentThread().isInterrupted() frequentemente
            if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {

                System.out.println("O timeout terminou");
            }

        } catch (InterruptedException e) {
            System.out.println("As threads foram interrompidas");
        }

        estimatedTime = System.nanoTime() - startTime;

        sum = 0;
        for (double v : randomArrayCopy) {
            sum += v;
        }

        System.out.println("Resultado final com " + nThreads + " threads: " + sum);

        System.out.printf("Tempo de execu��o com " + nThreads + " threads: " + estimatedTime / 1E9 + "s");
    }
}
