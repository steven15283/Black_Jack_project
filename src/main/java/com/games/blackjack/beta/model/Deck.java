package com.games.blackjack.beta.model;
import java.util.*;
import java.util.List;

public class Deck {
    private Stack<Card> deck;

    public Deck(){
        for(int i =0; i < 4; i++){
            for(int j =0; j < 50; j++){
                Card card = new Card(j);
                deck.push(card);
            }
        }
    }

    public Card draw() {
        return deck.pop();
    }
    public void shuffle() {
        Collections.shuffle(deck);
    }

}
