package Ficha2;

import utils.Array;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;

public class Producer implements Runnable {
    private final Semaphore livre;
    private final Semaphore ocups;
    private final Lock mutex;

    public Producer(Semaphore livre, Semaphore ocups, Lock mutex) {
        this.livre = livre;
        this.ocups = ocups;
        this.mutex = mutex;
    }

    private int produzirItem() {
        Random rand = new Random();
        int item = rand.nextInt(Buffer.min, Buffer.max);
        System.out.println("A thread " + Thread.currentThread().getName() + " produziu o item " + item);

        return item;
    }

    private void depositarItem(int item) {
        for (int i = 0; i < Buffer.bufferSize; i++) {
            if (!Buffer.isOccuupied[i]) {
                Buffer.buffer[i] = item;
                Buffer.isOccuupied[i] = true;
                System.out.println("A thread " + Thread.currentThread().getName() + " depositou o item " + item + " na posição " + i);
                break;
            }
        }
    }

    @Override
    public void run() {
        Thread.currentThread().setName("Produtora_" + Thread.currentThread().getId());

        while (!Thread.currentThread().isInterrupted()) {


            int item = produzirItem();

            livre.acquireUninterruptibly();

            //Evita que mais de uma Thread acesse recurso, protegendo o método depositarItem para
            //impedir múltiplos acessos a um mesmo recurso.
            mutex.lock();
            depositarItem(item);
            mutex.unlock();

            ocups.release();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("A thread " + Thread.currentThread().getName() +
                        " recebeu o sinal de interrupção");
                return;
            }
        }

        System.out.println("A thread " + Thread.currentThread().getName() + " terminou");

    }
}
