package com.hellteam.programmez_thread.interaction_thread;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Synchronisation effective :
 * - le producer ne peut produire lorsque le buffer est plein
 * - le consommateur ne peut pas lire avec un buffer vide
 */
public class ProducerConsumerThreadSafe {

    private static final int MAX_ELEMENTS = 20;
    private static final int BUFFER_SIZE = 5;
    private final Object lock = new Object();
    private final Queue<Integer> queue = new LinkedList<>();

    public static void main(String[] args) {
        ProducerConsumerThreadSafe maine = new ProducerConsumerThreadSafe();
        maine.runDemo();
    }

    private void runDemo() {

        Runnable producer = () -> {

            for (int i = 0; i < MAX_ELEMENTS; i++) {

                synchronized (lock) {
                    while (queue.size() == BUFFER_SIZE) {
                        System.out.println(Thread.currentThread().getName() + ": Queue is full. Waiting for produce...");
                        try {
                            lock.wait();
                        } catch (InterruptedException ex) {
                            // Do nothing
                        }
                    }
                }

                synchronized (lock) {
                    System.out.println(Thread.currentThread().getName() + " put: " + i);
                    queue.offer(i);
                    lock.notify();
                }
            }
        };

        Runnable consumer = () -> {

            int counter = 0;

            while (counter < MAX_ELEMENTS) {

                while (queue.isEmpty()) {
                    System.out.println(Thread.currentThread().getName() + " Queue is empty. Waiting for data to consume...");
                    try {
                        lock.wait();
                    } catch (InterruptedException ex) {
                        // Do nothing
                    }
                }

                synchronized (lock) {
                    System.out.println(Thread.currentThread().getName() + " get: " + queue.poll());
                    lock.notify();
                    counter++;
                }
            }

        };

        Thread threadProducer = new Thread(producer, "Producer");
        Thread threadConsumer = new Thread(consumer, "Consumer");

        threadProducer.start();
        threadConsumer.start();

    }

}
