package Ficha2;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;

public class Consumer implements Runnable {

    private final Semaphore livre;
    private final Semaphore ocups;
    private final Lock mutex;

    public Consumer(Semaphore livre, Semaphore ocups, Lock mutex) {
        this.livre = livre;
        this.ocups = ocups;
        this.mutex = mutex;
    }


    private void consumirItem(int item) {
        System.out.println("A thread " + Thread.currentThread().getName() + " consumiu o item " + item);
    }

    private int retirarItem() {

//        System.out.println("A thread " + Thread.currentThread().getName() + " consumiu o item " + item);
        for (int i = 0; i < Buffer.bufferSize; i++) {
            if (Buffer.isOccuupied[i]) {
                Buffer.isOccuupied[i] = false;

                System.out.println("A thread " + Thread.currentThread().getName() + " retirou o item " + Buffer.buffer[i]
                    + " da posi��o " + i + " do buffer");
                return Buffer.buffer[i];
            }
        }

        return Buffer.max+1;
    }



    @Override
    public void run() {
        Thread.currentThread().setName("Consumidora_" + Thread.currentThread().getId());

        while (!Thread.currentThread().isInterrupted()) {

            //Solicita acesso a um recurso, reduzindo o contador do sem�foro e caso seja maior que zero continua a
            //acessar o recurso. Pedir permiss�o para acessar
            //Deixa de estar ocupado
            ocups.acquireUninterruptibly();

            mutex.lock();

            int item = retirarItem();

            mutex.unlock();

            //Avisando que h� um recurso para utilizar, incrementando 1 valor no contador
            //Est� livre para utiliza��o
            livre.release(); //Aumenta o valor no contador do sem�foro, avisando que � poss�vel utilizar os recursos

            consumirItem(item);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("A thread " + Thread.currentThread().getName() +
                        " recebeu o sinal de interrup��o");
                return;
            }
        }

        System.out.println("A thread " + Thread.currentThread().getName() + " terminou");

    }
}
