package com.hellteam.programmez_thread;

public class SynchronizationSummary {

    private final Object lockA = new Object();
    private final Object lockB = new Object();
    private static final Object LOCK_C = new Object();

    public void doAction1(){
        synchronized (lockA){
            // critical session exexcuted by one thread only
        }
    }

    public void doAction2(){
        synchronized (lockB){
            // critical session executed by one thread only
        }
    }

    public synchronized void doAction3(){
        // critical session executed by one thread only
    }

    public static synchronized void doAction4(){
        // critical session executed by one thread only
    }

    public void doAction5(){
        synchronized (LOCK_C){
            // critical session executed by one thread only
        }
    }

    public void doAction6(){
        synchronized (SynchronizationSummary.class){
            // critical session executed by one thread only
        }
    }
}
