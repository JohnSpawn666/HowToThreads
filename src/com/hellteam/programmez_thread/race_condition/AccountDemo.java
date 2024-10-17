package com.hellteam.programmez_thread.race_condition;

public class AccountDemo implements Runnable{

    Account account = new Account();

    public static void main(String[] args) {
        AccountDemo demo = new AccountDemo();

        Thread thread1 = new Thread(demo, "Alice");
        Thread thread2 = new Thread(demo, "Bob");

        thread1.start();
        thread2.start();
    }

    @Override
    public void run() {
        for(int i=0; i<5; i++){
            makeWithDraw(20);

            if(account.getBalance() < 0){
                System.out.println("Account is overdrawn !");
            }
        }

    }

    private synchronized void makeWithDraw(int amount) {

        if(account.getBalance() >= amount){
            System.out.println(Thread.currentThread().getName() + " is going to withdraw");

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {

            }

            account.withDraw(amount);
            System.out.println(Thread.currentThread().getName() + " complete the withdraw");

        }else{
            System.out.println("Not enough in account for " + Thread.currentThread().getName() + " to withdraw "
                    + account.getBalance());
        }



    }
}
