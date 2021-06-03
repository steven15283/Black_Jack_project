package com.games.blackjack.beta.service;

import com.games.blackjack.beta.model.Card;
import com.games.blackjack.beta.model.Player;
import com.games.blackjack.beta.repository.PlayerDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class BlackJackService {

    @Autowired //injects the object into this class. like initializing it see below
    private PlayerDao playerDao;

    private static int TWENTY_ONE = 21;


    public void joinGame(List<Player> players) {
        if(players.size() > 5) {
            log.error(players.size() + " players is too many players, MAX is 5 players");
        }
        else if(players.size() > 0 && players.size() <= 5) {
            players.forEach(
                    player -> {
                        if(playerDao.findByUsername(player.getUsername()) != null) {
                            player.setInGame(true);
                            playerDao.save(player);
                        }else {
                            log.error(player.getUsername() + " does not exist.");
                        }
                    }
            );
        }
    }

    public void dealCards(List<Player> players) {
        List<Card> hand = new ArrayList<>();

        Card nextCardDealer = new Card(1);

        log.info(nextCardDealer.toString());

        players.forEach(
                player -> {
                    if(playerDao.findByUsername(player.getUsername()) != null) {

                        Card nextCard = new Card(1);
                        hand.add(nextCard);
                        player.setHand(hand);
                        playerDao.save(player);
                        hand.clear();
                    }
                }
        );
    }

    public void hit(Player player) {
        if(playerDao.findByUsername(player.getUsername()) != null && playerDao.findByUsername(player.getUsername()).isInGame()) {
            Player foundPlayer = playerDao.findByUsername(player.getUsername());

            List<Card> hand = new ArrayList<Card>(foundPlayer.getHand());
            Card nextCard = new Card(22);
            hand.add(nextCard);
            foundPlayer.setHand(hand);
            playerDao.save(foundPlayer);
            if (isBust(foundPlayer)) {
                foundPlayer.setInGame(false);
                playerDao.save(foundPlayer);
            }
        }
    }

    public void stand(Player player) {
        log.info(player.getUsername() + " stands.");
    }

//    public void isBlackJack() {
//
//    }
//
//    public void is21() {
//
//    }
//
    public boolean isBust(Player player) {
        int sum = 0;
        for (Card card : player.getHand()) {
            sum += card.getFace_value();
            if(sum > TWENTY_ONE) {
                log.info(player.getUsername() + " busted with a score of " + sum);
                return true;
            }
        }
        return false;
    }
//
//    public void dealerWon() {
//
//    }
}
