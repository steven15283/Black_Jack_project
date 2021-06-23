package com.games.blackjack.beta.controller;

import com.games.blackjack.beta.model.Dealer;
import com.games.blackjack.beta.model.Player;
import com.games.blackjack.beta.service.BlackJackService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public int hit(@RequestBody Player player) {
        return service.hit(player);
    }

    @PostMapping("/dealerHit/{room}")
    public List<Integer> dealerHit(@PathVariable("room") String room) {
        return service.dealerHit(room);
    }

//    @PostMapping("/bj")
//    public boolean bj(@RequestBody Player player) { return service.isBlackJack(player); }

//    @GetMapping("/dealerBJCheck")
//    public boolean dealer_bj_check(@PathVariable("room") String room){ return service.dealer_bj_check(room); }

    @GetMapping("/getDealer/{room}")
    public Dealer getDealerDao(@PathVariable("room") String room) { return service.getDealerInRoom(room); }

    @GetMapping("/reset/{room}")
    public void reset(@PathVariable("room") String room) { service.reset(room);}
}

