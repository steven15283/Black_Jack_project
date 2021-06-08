package com.games.blackjack.beta.model;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
@ToString
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int card_id;
    private int face_value;

    public Card() {
        super();
    }
    public Card(int face_value) {
        super();
        this.face_value = face_value;
    }

    public Card(int card_id, int face_value) {
        super();
        this.card_id = card_id;
        this.face_value = face_value;
    }

}
