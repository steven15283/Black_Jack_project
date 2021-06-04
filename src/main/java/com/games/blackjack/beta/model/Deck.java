package com.games.blackjack.beta.model;

import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Collections;
import java.util.Stack;

@Entity
@ToString

public class Deck {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private Stack<Card> deck;
    public Deck(){//initialize deck with 100 cards
        super();
        this.deck = new Stack<Card>();
        System.out.println("deck()");
        for(int i =0; i < 8; i++){
            System.out.println("for loop:" + i);
            for(int j =1; j <= 13; j++){
                System.out.println("for 1-13 loop:" + j);
                Card card = new Card(j);
                System.out.println("card created");
                deck.push(card);
                System.out.println("card pushed");
            }
        }
        System.out.println("finish insert cards");
        shuffle();
        System.out.println("finish fhuffling cards");
    }

    public Deck(String id, Stack<Card> deck) {
        this.id = id;
        this.deck = deck;
    }

    public Card draw() {
        return deck.pop();
    }//draws a card from the deck and pops the deck
    public void shuffle() {//shuffle the deck
        Collections.shuffle(deck);
    }

}
