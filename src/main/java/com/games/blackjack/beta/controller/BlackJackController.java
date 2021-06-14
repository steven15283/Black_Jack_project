package com.games.blackjack.beta.controller;

import com.games.blackjack.beta.model.Dealer;
import com.games.blackjack.beta.model.Deck;
import com.games.blackjack.beta.model.Player;
import com.games.blackjack.beta.repository.DealerDao;
import com.games.blackjack.beta.service.BlackJackService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/isBust")
    public boolean isBust(@RequestBody Player player) {
        return service.isBust(player);
    }

    @PostMapping("/bj")
    public boolean bj(@RequestBody Player player) { return service.isBlackJack(player); }

    @PostMapping("/is21")
    public void is21(@RequestBody Player player) {
        service.is21(player);
    }

    @PostMapping("/dealerWon")
    public void dealerWon(@RequestBody List<Player> players) {
        service.dealerWon(players);
    }

    @PostMapping("/dealerRL")
    public void dealer_reach_limit(Dealer dealer){service.dealer_reach_limit(dealer);}

    @PostMapping("/checkHand")
    public void checkHand(@RequestBody Player player){service.checkHand(player); }

    @PostMapping("/dealerBJCheck")
    public boolean dealer_bj_check(@RequestBody Dealer dealer){ return service.dealer_bj_check(dealer); }

    @GetMapping("/getDealer")
    public Dealer getDealerDao() {
        return service.getDealer();
    }
}

