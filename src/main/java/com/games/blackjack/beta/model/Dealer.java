package com.games.blackjack.beta.model;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Entity
@Getter
public class Dealer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToMany(targetEntity=Card.class, cascade = {CascadeType.ALL})
    private List<Card> hand;
    private int hand_value;
    public Dealer(){
        this.hand = new ArrayList<Card>();
        this.hand_value = 0;
    }



    public void get_card(Card card){
        System.out.println("b4 adding to dealer hand");
        System.out.println("card:" + card.toString());
        hand.add(card);
        hand_value += card.get_face_value();
        System.out.println("hand:" + hand.toString());

        System.out.println("after adding to dealer hand");
    }

    public void hide_card(){
        hand_value = hand.get(0).get_face_value();
    }

    public void show_card(){
        hand_value += hand.get(1).get_face_value();
    }

    public void deal_card(Deck deck, Player player){
        player.get_card(deck.draw());
    }

    public void setHand_value(int value){
        hand_value = value;
    }
}
