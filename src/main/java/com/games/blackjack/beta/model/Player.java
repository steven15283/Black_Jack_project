package com.games.blackjack.beta.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
public class Player {
    @Id
    private String username;
    private double balance;
    @OneToMany(targetEntity=Card.class, cascade = {CascadeType.ALL})
    private List<Card> hand;
    public Player() {

    }

    public Player(String username, double balance) {
        super();
        this.username = username;
        this.balance = balance;
    }

    public int getHand() {
        int sum = 0;
        for (Card i: hand) {
            sum += i.get_face_value();
        }
        return sum;
    }

    public void check_Ace(Player player)
    {//
        if(player.getHand() > 21)
        {
            for (Card i: hand) {
                if(i.get_face_value() == 11)
                    i.set_face_value(1);
            }
        }
    }

    public void get_card(Card card){
        hand.add(card);
    }
}
