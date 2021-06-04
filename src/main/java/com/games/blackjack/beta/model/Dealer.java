package com.games.blackjack.beta.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
@Entity
public class Dealer {
    @Id
    private String id;
    @OneToMany(targetEntity=Card.class, cascade = {CascadeType.ALL})
    private List<Card> hand;
    public Dealer(){

    }

    public void Deal_card(Deck deck, Player player){//calls draw from Deck and gets the card popped from deck. The popped card is then added into player hand
        player.get_card(deck.draw());
    }

    public void get_card(Card card){
        hand.add(card);
    }
}
