package com.games.blackjack.beta.controller;

import com.games.blackjack.beta.model.Player;
import com.games.blackjack.beta.service.BlackJackService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/blackjack/")
public class BlackJackController {

    private final BlackJackService service;

    @PostMapping("/start")
    public void startBlackJackGame(@RequestBody List<Player> players) {
        service.joinGame(players);

        service.dealCards(players);
    }

    @PostMapping("/hit")
    public void hit(@RequestBody Player player) {
        service.hit(player);
    }
}
