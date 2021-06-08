package com.games.blackjack.beta.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "player")
public class Player {
    @Id
    @Column(name = "username")
    private String username;
    @Column(name = "balance")
    private double balance;

    @Column(name = "hand")
    @OneToMany(targetEntity=Card.class, cascade = {CascadeType.ALL})
    private List<Card> hand;
    @Column(name = "move")
    private String move;
    @Column(name = "is_in_game")
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
