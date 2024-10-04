package Ficha2;

import utils.InputValidation;

import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {


    public static void main(String[] args) {
        Semaphore livre; //Número de elementos do array
        Semaphore ocups = new Semaphore(0);
        Lock mutex = new ReentrantLock();

        Scanner sc = new Scanner(System.in);

        Buffer.bufferSize = InputValidation.validateIntGT0(sc, "Introduza o tamanho do buffer: ");
        Buffer.min = InputValidation.validateInt(sc, "Introduza o valor mínimo a colocar no buffer: ");
        Buffer.max = InputValidation.validateIntGTN(sc, "Introduza o valor máximo a colocar no buffer: ", Buffer.min);

        livre = new Semaphore(Buffer.bufferSize);

        Buffer.buffer = new int[Buffer.bufferSize];
        Buffer.isOccuupied = new boolean[Buffer.bufferSize];

        int nProducerThreads = InputValidation.validateIntGT0(sc, "Introduza o número de threads produtoras: ");
        int nConsumerThreads = InputValidation.validateIntGT0(sc, "Introduza o número de threads consumidoras: ");

        ExecutorService executor = Executors.newFixedThreadPool(nProducerThreads + nConsumerThreads);

        for (int i = 0; i < nProducerThreads; i++) {
            Producer producer = new Producer(livre, ocups, mutex);
            executor.execute(producer);
        }

        for (int i = 0; i < nConsumerThreads; i++) {
            Consumer consumer = new Consumer(livre, ocups, mutex);
            executor.execute(consumer);
        }


        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            System.out.println("Erro de interrupção");
        }


        executor.shutdownNow();

        System.out.println("O main terminou");

        sc.close();
    }


}
