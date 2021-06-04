package com.games.blackjack.beta.model;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.*;
import java.util.List;
@Entity
public class Deck {
    @Id
    private String id;
    @OneToMany(targetEntity=Card.class, cascade = {CascadeType.ALL})
    private Stack<Card> deck;
    public Deck(){//initialize deck with 100 cards
        for(int i =0; i < 3; i++){
            for(int j =0; j < 50; j++){
                Card card = new Card(j);
                deck.push(card);
            }
        }
    }

    public Card draw() {
        return deck.pop();
    }//draws a card from the deck and pops the deck
    public void shuffle() {//shuffle the deck
        Collections.shuffle(deck);
    }

}
