package com.games.blackjack.beta.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Player {
    @Id
    private String username;
    private double balance;

    public Player() {

    }

    public Player(String username, double balance) {
        super();
        this.username = username;
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
