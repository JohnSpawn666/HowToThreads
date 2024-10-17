package com.hellteam.programmez_thread.launch;

public class MyRunnable {

    public static void main(String[] args) {
        Runnable runnable = () -> {
            System.out.println("Thread started");
            for(int i=0; i<10; i++){
                System.out.println("Thread working " + i);
            }
            System.out.println("Thread stopped");
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }
}
