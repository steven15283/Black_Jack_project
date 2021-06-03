package com.games.blackjack.beta.model;

public class Card {
    private String card_name;
    private int face_value;

    public Card(){

    }
    public Card(int num){
        switch(num){
            case 1:
                this.card_name ="A";
                this.face_value = 11;
            case 2:
                this.card_name ="2";
                this.face_value = 2;
            case 3:
                this.card_name ="3";
                this.face_value = 3;
            case 4:
                this.card_name ="4";
                this.face_value = 4;
            case 5:
                this.card_name ="5";
                this.face_value = 5;
            case 6:
                this.card_name ="6";
                this.face_value = 6;
            case 7:
                this.card_name ="7";
                this.face_value = 7;
            case 8:
                this.card_name ="8";
                this.face_value = 8;
            case 9:
                this.card_name ="9";
                this.face_value = 9;
            case 10:
                this.card_name ="10";
                this.face_value = 10;
            case 11:
                this.card_name ="J";
                this.face_value = 10;
            case 12:
                this.card_name ="Q";
                this.face_value = 10;
            case 13:
                this.card_name ="K";
                this.face_value = 10;
        }
    }

    public double get_face_value() {
        return face_value;
    }
    public double get_card_name() {
        return face_value;
    }
    public void set_face_value(int num) {
        face_value = num;
    }


}
