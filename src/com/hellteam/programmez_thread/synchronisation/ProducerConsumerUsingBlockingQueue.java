package com.hellteam.programmez_thread.synchronisation;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class ProducerConsumerUsingBlockingQueue {

    private static final int MAX_ELEMENTS = 20;
    private static final int BUFFER_SIZE = 5;

    private final BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(BUFFER_SIZE);

    public static void main(String[] args) {
        ProducerConsumerUsingBlockingQueue main = new ProducerConsumerUsingBlockingQueue();
        main.runDemo();
    }

    private void runDemo() {

        Runnable producer = () -> {

            Thread.currentThread().setName("Producer");

            for (int i = 0; i < MAX_ELEMENTS; i++) {

                try {
                    System.out.println(Thread.currentThread().getName() + " put " + i);
                    queue.put(i);
                } catch (InterruptedException ex) {
                    // Do nothing
                }
            }
        };

        Runnable consumer = () -> {

            int counter = 0;
            Thread.currentThread().setName("Consumer");

            while (counter < MAX_ELEMENTS) {

                try {
                    System.out.println(Thread.currentThread().getName() + " get : " + queue.take());
                    counter++;
                } catch (InterruptedException ex) {
                    // Do nothing
                }
            }
        };

        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(producer);
        executor.execute(consumer);

        executor.shutdown();

    }

}
