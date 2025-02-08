package com.hellteam.programmez_thread.thread_safe;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class NonAtomicOperation {

    private final List<String> names = Collections.synchronizedList(new LinkedList<String>());

    public static void main(String[] args) {

        final NonAtomicOperation n1 = new NonAtomicOperation();
        n1.add("Foolbar");

        Runnable r = () -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                // Nothing to do
            }
            String name = n1.removeFirst();
            System.out.println(name);
        };

        Thread t1 = new Thread(r, "Thread 1");
        Thread t2 = new Thread(r, "Thread 2");

        t1.start();
        t2.start();


    }

    private void add(String name) {
        names.add(name);
    }

    /*
    Cette méthode doit être synchronisée du aux opérations atomiques, malgré le fait que la liste 'names' soit synchronisée
     */
    private synchronized String removeFirst() {
        return names.isEmpty()
                ? null
                : names.removeFirst();
    }

}
