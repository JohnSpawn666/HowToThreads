package com.hellteam.programmez_thread.race_condition;

public class Account {

    private int balance = 100;

    public int getBalance() {
        return balance;
    }

    void withDraw(int amount){
        this.balance = this.balance - amount;
    }
}
