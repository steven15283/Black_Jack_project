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

    @GetMapping("/player/{user}")
    public Player getPlayer(@PathVariable("user") String user) {
        return service.get(user);
    }
    @DeleteMapping("/player/{user}")
    public void deletePlayer(@PathVariable("user") String user){
        service.delete(user);
    }

}
