package com.games.blackjack.beta.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@ToString
public class Room {
    @Id
    private String id;
    private boolean gameStarted;
    private int currentPlayer;

    public Room() {

    }
}
