package com.hellteam.programmez_thread.livelock;

import java.util.concurrent.TimeUnit;

public class LivelockDiskSpaceMain {

    public static void main(String[] args) {

        DiskManager diskManager = new DiskManager();

        Thread thread1 = new Thread(() -> {
            while (true) {
                if (diskManager.isTooLow()) {
                    diskManager.reclaimSpace();
                    sleep(500);
                }
                sleep(100);
            }
        }, "diskCleanderTask1");

        Thread thread2 = new Thread(() -> {

            String name = Thread.currentThread().getName();
            int applicationSize = 60;
            int packSize = 10;
            int maxPart = applicationSize / packSize;
            int progressBar = 0;
            int currentSize = 0;

            while (currentSize < applicationSize) {
                sleep(500);

                try {
                    int amountDone = diskManager.useSpace(packSize);
                    currentSize = currentSize + amountDone;
                    progressBar++;
                    System.out.println(name + " progressing " + progressBar + " / " + maxPart);
                } catch (RuntimeException ex) {
                    System.out.println(name + " insufficient space. Download aborted. Retry in a few moments");
                    progressBar = 0;
                    currentSize = 0;
                    sleep(500);
                }
            }
            System.out.println("End download");

        }, "downloader thread2");

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

    }


    private static void sleep(long timeout) {
        try {
            TimeUnit.MILLISECONDS.sleep(timeout);
        } catch (InterruptedException ex) {

        }
    }


    private static class DiskManager {

        private static final int MAX_THRESHOLD = 80;
        private static final int MAX_SPACE = 100;
        private static final int AMOUNT_TO_FREE = 4;

        private int usedSpace = 50;

        private synchronized void reclaimSpace() {
            usedSpace = usedSpace - AMOUNT_TO_FREE;
            System.out.println(Thread.currentThread().getName() + " space after clean " + usedSpace);
        }

        private synchronized int useSpace(int amount) {
            if (isTooLow()) {
                System.out.println(Thread.currentThread().getName() + " no more available space");
                throw new RuntimeException("Space disk almost full");
            } else if (usedSpace + amount <= MAX_SPACE) {
                usedSpace = usedSpace + amount;
                System.out.println(Thread.currentThread().getName() + " remaining space " + usedSpace);
            }
            return amount;
        }

        private boolean isTooLow() {
            return usedSpace >= MAX_THRESHOLD;
        }

    }

}
