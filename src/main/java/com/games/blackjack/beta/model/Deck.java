package com.games.blackjack.beta.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.*;

@Entity
@ToString
@Getter
@Setter
@Slf4j
public class Deck {
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @OneToMany(targetEntity=Card.class, cascade = {CascadeType.ALL})
    private List<Card> pile;

    public Deck() {//initialize deck with 100 cards
        super();
        this.pile = new ArrayList<Card>();
        for (int i = 0; i < 8; i++) {
            for (int j = 1; j <= 13; j++) {
                Card card = new Card(j);
                //log.info(card.toString());
                pile.add(card);
            }
        }
        shuffle();

    }

    public Deck(String id, List<Card> deck) {
        this.id = id;
        this.pile = deck;
    }

    public Card draw() {
        Card temp = pile.get(pile.size()-1);
        pile.remove(pile.size()-1);
        return temp;
    }//draws a card from the deck and pops the deck

    public void shuffle() {//shuffle the deck
        Collections.shuffle(pile, new Random());
    }
}

