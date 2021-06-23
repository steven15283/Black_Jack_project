package com.games.blackjack.beta.controller;

import com.games.blackjack.beta.model.Player;
import com.games.blackjack.beta.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/")
public class PlayerController {

    private final PlayerService service;

    @Autowired
    public PlayerController(PlayerService service) {
        this.service = service;
    }

    @GetMapping("/player/players")
    public List<Player> getPlayers() {
        return service.listAll();
    }

    @PostMapping("register/newPlayer")
    public boolean createPlayer(@RequestBody Player player) {
        return service.save(player);
    }

    @GetMapping("/player/{user}")
    public Player getPlayer(@PathVariable("user") String user) {
        return service.get(user);
    }
    @DeleteMapping("/player/{user}")
    public void deletePlayer(@PathVariable("user") String user){
        service.delete(user);
    }

    @GetMapping("/player/players/room/{room}")
    public List<Player> getPlayersInRoom(@PathVariable("room") String room) {
        return service.listAll().stream().filter(player -> player.getRoom().equals(room)).collect(Collectors.toList());
    }
}
