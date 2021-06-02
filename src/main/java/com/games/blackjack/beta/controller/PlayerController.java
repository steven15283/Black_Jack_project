package com.games.blackjack.beta.controller;

import com.games.blackjack.beta.model.Player;
import com.games.blackjack.beta.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("api/v1")
public class PlayerController {

    @Autowired
    private PlayerService service;

    @GetMapping(path = "players")
    public List<Player> getPlayers() {
        return service.listAll();
    }

    @PostMapping(path = "newPlayer")
    public void createPlayer(@RequestBody Player player) {
        service.save(player);
    }
}
