package com.games.blackjack.beta.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Room {
    @Id
    private String id;
    private boolean gameStarted;
    private int currentPlayer;
    private boolean roundEnded;
    @OneToMany(targetEntity=Player.class, cascade = {CascadeType.ALL})
    private List<Player> players;

    public Room() {
        this.players = new ArrayList<>();
        this.currentPlayer = -1;
    }
}
