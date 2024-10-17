package com.hellteam.programmez_thread.launch;

/**
 * pile d'appel avec 2 Threads
 */
public class StackMain {

    public static void main(String[] args) {
        method1();
        System.out.println(Thread.currentThread() + " : End main");
    }

    private static void method1() {
        Runnable runnable = () -> {
            System.out.println(Thread.currentThread() + " Task running");
        };

        Thread thread = new Thread(runnable, "Sonic");
        thread.run();
        thread.start();
    }
}
