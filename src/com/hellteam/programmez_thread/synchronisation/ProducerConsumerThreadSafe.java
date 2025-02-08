package com.hellteam.programmez_thread.synchronisation;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerThreadSafe {

    private static final int MAX_ELEMENTS = 20;
    private static final int BUFFER_SIZE = 5;
    private final Lock lock = new ReentrantLock();
    private final Queue<Integer> queue = new LinkedList<>();

    public static void main(String[] args) {
        ProducerConsumerThreadSafe main = new ProducerConsumerThreadSafe();
        main.runDemo();
    }

    private void runDemo() {

        final Condition conditionBufferEmpty = lock.newCondition();
        final Condition conditionBufferFull = lock.newCondition();

        Runnable producer = () -> {

            Thread.currentThread().setName("Producer");

            for (int i = 0; i < MAX_ELEMENTS; i++) {

                try {
                    lock.lock();
                    while (queue.size() == BUFFER_SIZE) {
                        System.out.println(Thread.currentThread().getName() + " : Queue is full. Waiting for the producer....");
                        try {
                            conditionBufferFull.await();
                        } catch (InterruptedException ex) {
                            // Do nothing
                        }
                    }
                } finally {
                    lock.unlock();
                }

                try {
                    lock.lock();
                    System.out.println(Thread.currentThread().getName() + " put " + i);
                    queue.offer(i);
                    conditionBufferEmpty.signal();
                } finally {
                    lock.unlock();
                }
            }
        };

        Runnable consumer = () -> {
            int counter = 0;
            Thread.currentThread().setName("Consumer");

            while (counter < MAX_ELEMENTS) {

                try {
                    lock.lock();
                    while (queue.isEmpty()) {
                        System.out.println(Thread.currentThread().getName() + " Queue is empty. Waiting for data to consume....");

                        try {
                            conditionBufferEmpty.await();
                        } catch (InterruptedException ex) {
                            // Do nothing
                        }
                    }
                } finally {
                    lock.unlock();
                }

                try {
                    lock.lock();
                    System.out.println(Thread.currentThread().getName() + " get: " + queue.poll());
                    conditionBufferFull.signal();
                    counter++;
                } finally {
                    lock.unlock();
                }
            }
        };

        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(producer);
        executor.execute(consumer);

        executor.shutdown();

    }
}
