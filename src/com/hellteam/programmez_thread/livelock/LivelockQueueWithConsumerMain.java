package com.hellteam.programmez_thread.livelock;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LivelockQueueWithConsumerMain {

    public static void main(String[] args) {

        Deque<Integer> queue = new LinkedList<>();

        int maxElements = 5;

        for (int i = 0; i < maxElements; i++) {
            queue.add(i);
        }

        Runnable consumer = () -> {

            Integer id = 0;
            int typeMEssage = new Random().nextInt(maxElements);

            while ((id = queue.poll()) != null) {
                System.out.println("Processing value : " + id + " ...");
                if (id == typeMEssage) {
                    System.out.println("Simulate error for value : " + id + ". Rollback transaction and putting back to the queue.");
                    queue.addFirst(id);
                } else {
                    System.out.println("Processing value : " + id + " complete");
                }
            }
        };

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(consumer);

        executorService.shutdown();

    }

}
