package com.games.blackjack.beta.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "player")
public class Player {
    @Id
    @Column(name = "username")
    private String username;
    @Column(name = "balance")
    private double balance;
    private int hand_value;
    private String room;

    @Column(name = "hand")

    @OneToMany(targetEntity=Card.class, cascade = {CascadeType.ALL})
    private List<Card> hand;

    @Column(name = "is_in_game")
    private boolean isInGame;
    private double bet;


    public Player() {
        this.isInGame = false;
        this.hand = new ArrayList<Card>();
        this.hand_value = 0;
        this.bet = 0;
    }

    public Player(String username, double balance, List<Card> hand, boolean isInGame) {
        super();
        this.username = username;
        this.balance = balance;
        this.hand = hand;
        this.isInGame = isInGame;
        this.bet = 0;
    }

    public void get_card(Card card){
        hand.add(card);
        hand_value += card.get_face_value();

        if(hand_value > 21)
        {
            for(int i =0; i < hand.size();i++)
            {
                if(hand.get(i).get_face_value() == 11)
                {
                    hand.get(i).set_face_value(1);
                    break;
                }
            }
            setHand_value(0);
            for(int i =0; i < hand.size();i++)
            {

                hand_value += hand.get(i).get_face_value();

            }
        }
    }

    public void clear_hand(){
        hand.clear();
        hand_value = 0;
    }
}
