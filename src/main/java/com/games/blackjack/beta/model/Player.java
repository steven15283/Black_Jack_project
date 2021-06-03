package com.games.blackjack.beta.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
public class Player {
    @Id
    private String username;
    private double balance;
    private List<Card> hand;
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

    public int getHand() {
        int sum = 0;
        for (Card i: hand) {
            sum += i.get_face_value();
        }
        return sum;
    }

    public void check_Ace(Player player)
    {//
        if(player.getHand() > 21)
        {
            for (Card i: hand) {
                if(i.get_face_value() == 11)
                    i.set_face_value(1);
            }
        }
    }
}
