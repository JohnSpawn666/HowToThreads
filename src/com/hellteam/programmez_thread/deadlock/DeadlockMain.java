package com.hellteam.programmez_thread.deadlock;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class DeadlockMain {

    private static final int COMPUTE_HARD = 1000;

    public static void main(String[] args) {

        final Runner runner = new Runner();

        Runnable runnable1 = () -> {
            try {
                runner.firstThread();
                System.out.println("Thread 1 is finished");
            } catch (InterruptedException ex) {

            }
        };

        Runnable runnable2 = () -> {
            try {
                runner.secondThread();
                System.out.println("Thread 2 is finished");
            } catch (InterruptedException ex) {

            }
        };

        Thread thread1 = new Thread(runnable1, "Thread1");
        Thread thread2 = new Thread(runnable2, "Thread2");

        thread1.start();
        thread2.start();

        // Le lancement se bloque

        // La solution pour éviter ce problème est de toujours prendre les verrous dans le même ordre

    }

    private static class Runner {

        private final Object lock1 = new Object();
        private final Object lock2 = new Object();
        private final Account account1 = new Account();
        private final Account account2 = new Account();

        public void firstThread() throws InterruptedException {

            Random random = new Random();

            for (int i = 0; i < COMPUTE_HARD; i++) {

                synchronized (lock1) {
                    synchronized (lock2) {
                        // Transfer from 1 to 2
                        int amount = random.nextInt(100);
                        Account.transfer(account1, account2, amount);
                        System.out.println(Thread.currentThread().getName()
                                + " completes the transaction from account1 to account2 of " + amount + " €");
                        TimeUnit.MILLISECONDS.sleep(100);
                    }
                }
            }
        }

        public void secondThread() throws InterruptedException {

            Random random = new Random();

            for (int i = 0; i < COMPUTE_HARD; i++) {
                transferCredits(random);
            }
        }

        private void transferCredits(Random random) throws InterruptedException {

            // Locks must be held and released in same order that in others thread
            // Otherwise, deadlock happens

            synchronized (lock2) {
                synchronized (lock1) {
                    // transfer from 2 to 1
                    int amount = random.nextInt(100);
                    Account.transfer(account2, account1, amount);
                    System.out.println(Thread.currentThread().getName()
                            + " completes the transaction from account2 to account1 of " + amount + " €");
                    TimeUnit.MILLISECONDS.sleep(100);
                }
            }

        }

        public void finished() {
            System.out.println("Account 1 balance : " + account1.getBalance());
            System.out.println("Account 2 balance : " + account2.getBalance());
            System.out.println("Total of accounts balances : " + account1.getBalance() + account2.getBalance());
        }

    }

    private static class Account {

        private int balance = 10000;

        public static void transfer(Account account1, Account account2, int amount) {
            account1.withDraw(amount);
            account2.deposit(amount);
        }

        public void deposit(int amount) {
            balance += amount;
        }

        public void withDraw(int amount) {
            balance -= amount;
        }

        public int getBalance() {
            return balance;
        }

    }

}
