package com.games.blackjack.beta.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Entity
public class Dealer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @OneToMany(targetEntity=Card.class, cascade = {CascadeType.ALL})
    private List<Card> hand;
    public Dealer(){
        this.hand = new ArrayList<Card>();
    }

    public void deal_card(Deck deck, Player player){//calls draw from Deck and gets the card popped from deck. The popped card is then added into player hand
        player.get_card(deck.draw());
    }

    public void get_card(Deck deck){
        System.out.println("b4 adding to hand");
        hand.add(deck.draw());
        System.out.println("after adding to hand");
    }
}
