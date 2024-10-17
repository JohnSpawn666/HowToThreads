package com.hellteam.programmez_thread.prio_scheduling;

public class ThreadPriority {

    public static void main(String[] args) {
        Runnable runnable = () -> {
            System.out.println(Thread.currentThread().getName() + " thread started");;
            for(int i=0; i<10; i++){
                System.out.println(Thread.currentThread().getName() + " thread working " + i);
            }
            System.out.println(Thread.currentThread().getName() + " thread stopped");
        };

        Thread thread1 = new Thread(runnable, "thread1");
        thread1.setPriority(2);

        Thread thread2 = new Thread(runnable, "thread2");
        thread1.setPriority(Thread.MAX_PRIORITY);

        Thread thread3 = new Thread(runnable, "thread3");
        thread3.setPriority(Thread.MIN_PRIORITY);


        Thread thread4 = new Thread(runnable, "thread4");
        thread4.setPriority(Thread.NORM_PRIORITY);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }
}
