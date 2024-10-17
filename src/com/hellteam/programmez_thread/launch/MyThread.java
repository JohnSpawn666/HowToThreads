package com.hellteam.programmez_thread.launch;

public class MyThread {

    public static void main(String[] args) {

        Thread thread = new Thread(() -> {
            System.out.println("Thread started");
            for(int i=0; i<10; i++){
                System.out.println("Thread working " + i);
            }
            System.out.println("Thread stopped");
        });
        thread.start();
    }

}
