package com.games.blackjack.beta.model;


import lombok.AllArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
@AllArgsConstructor
@ToString
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String card_name;
    private int face_value;

    public Card() {

    }

    public Card(int num) {//
        super();
        switch (num) {
            case 1:
                this.card_name = "A";
                this.face_value = 11;
                break;
            case 2:
                this.card_name = "2";
                this.face_value = 2;
                break;
            case 3:
                this.card_name = "3";
                this.face_value = 3;
                break;
            case 4:
                this.card_name = "4";
                this.face_value = 4;
                break;
            case 5:
                this.card_name = "5";
                this.face_value = 5;
                break;
            case 6:
                this.card_name = "6";
                this.face_value = 6;
                break;
            case 7:
                this.card_name = "7";
                this.face_value = 7;
                break;
            case 8:
                this.card_name = "8";
                this.face_value = 8;
                break;
            case 9:
                this.card_name = "9";
                this.face_value = 9;
                break;
            case 10:
                this.card_name = "10";
                this.face_value = 10;
                break;
            case 11:
                this.card_name = "J";
                this.face_value = 10;
                break;
            case 12:
                this.card_name = "Q";
                this.face_value = 10;
                break;
            case 13:
                this.card_name = "K";
                this.face_value = 10;
                break;
        }
    }

    public int get_face_value() {
        return face_value;
    }

    public String get_card_name() {
        return card_name;
    }

    public void set_face_value(int num) {
        face_value = num;
    }

}
