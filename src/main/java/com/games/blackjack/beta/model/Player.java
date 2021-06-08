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
    private String username;
    private double balance;
    private int hand_value;
    @OneToMany(targetEntity=Card.class, cascade = {CascadeType.ALL})
    private List<Card> hand;
    private String move;
    private boolean isInGame;
    private double bet;


    public Player() {
        this.isInGame = false;
        this.hand = new ArrayList<Card>();
        this.hand_value = 0;
        this.bet = 0;
    }

    public Player(String username, double balance, List<Card> hand, String move, boolean isInGame) {
        super();
        this.username = username;
        this.balance = balance;
        this.hand = hand;
        this.move = move;
        this.isInGame = isInGame;
        this.bet = 0;
    }

    public void get_card(Card card){
        System.out.println("card:" + card.toString());
        hand.add(card);
        hand_value += card.get_face_value();
        System.out.println("hand:" + hand.toString());
    }

    public void clear_hand(){
        hand.clear();
    }
}
