package com.games.blackjack.beta.controller;

import com.games.blackjack.beta.model.Player;
import com.games.blackjack.beta.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PlayerController {

    private final PlayerService service;

    @Autowired
    public PlayerController(PlayerService service) {
        this.service = service;
    }

    @GetMapping("/players")
    public List<Player> getPlayers() {
        return service.listAll();
    }

    @PostMapping("/newPlayer")
    public void createPlayer(@RequestBody Player player) {
        service.save(player);
    }
}
