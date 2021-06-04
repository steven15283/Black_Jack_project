package com.games.blackjack.beta.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String username;
    private double balance;

    @OneToMany(targetEntity=Card.class, cascade = {CascadeType.ALL})
    private List<Card> hand;
    private String move;
    private boolean isInGame;


    public Player() {
        this.isInGame = false;
        this.hand = new ArrayList<Card>();
    }

    public Player(String username, double balance, List<Card> hand, String move, boolean isInGame) {
        super();
        this.username = username;
        this.balance = balance;
        this.hand = hand;
        this.move = move;
        this.isInGame = isInGame;

    }

    public void get_card(Card card){
        System.out.println("card:" + card.toString());
        hand.add(card);
        System.out.println("hand:" + hand.toString());
    }

    public void clear_hand(){
        hand.clear();
    }
}
