package com.games.blackjack.beta.controller;

import com.games.blackjack.beta.model.Dealer;
import com.games.blackjack.beta.model.Deck;
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
        Deck deck =  new Deck();
        Dealer dealer = new Dealer();
        service.joinGame(players);
        service.dealCards(players);

        //goes through each player to hit/stand
        /*
        players.forEach(
                player -> {
                    if(service.isBlackJack(player)) {
                        //do nothing b/c player already won
                    }
                    else{
                        do {//keeps looping for player input aka clicking hit button or stand button.

                            if(//player's hit button has been hit) {
                                service.hit(player);
                                if(service.isBust(player)) {//player has busted, stop asking if player wants to hit or stand
                                    break;
                                }
                            }
                        }while(//stand button is not been hit);
                    }
                }
        );
*/

    }

    @PostMapping("/hit")
    public void hit(@RequestBody Player player) {
        service.hit(player);
    }
}
