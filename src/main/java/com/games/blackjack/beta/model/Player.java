package com.games.blackjack.beta.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
public class Player {
    @Id
    private String username;
    private double balance;

    @OneToMany(targetEntity=Card.class, cascade = {CascadeType.ALL})
    private List<Card> hand;
    private String move;
    private boolean isInGame;

    public Player() {
        this.isInGame = false;
    }

    public Player(String username, double balance, List<Card> hand, String move, boolean isInGame) {
        super();
        this.username = username;
        this.balance = balance;
        this.hand = hand;
        this.move = move;
        this.isInGame = isInGame;
    }
}
