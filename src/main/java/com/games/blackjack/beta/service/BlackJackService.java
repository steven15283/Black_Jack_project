package com.games.blackjack.beta.service;

import com.games.blackjack.beta.model.*;
import com.games.blackjack.beta.repository.DealerDao;
import com.games.blackjack.beta.repository.DeckDao;
import com.games.blackjack.beta.repository.PlayerDao;
import com.games.blackjack.beta.repository.RoomDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BlackJackService {

    @Autowired
    private PlayerDao playerDao;
    @Autowired
    private DeckDao deckDao;
    @Autowired
    private DealerDao dealerDao;
    @Autowired
    private RoomDao roomdao;

    private static int TWENTY_ONE = 21;

    public Dealer getDealerInRoom(String room) {
        Dealer dealer;

        if(dealerDao.findDealerById(room) == null){
            log.error("Could not find dealer");
            dealer = new Dealer();
            dealer.setId(room);
        }
        else{
            dealer = dealerDao.findDealerById(room);
        }

        return dealer;
    }

    public Deck getDeckInRoom(String room) {
        Deck deck;
        if(deckDao.findDeckById(room) == null){
            log.error("Could not find deck");
            deck = new Deck();
            deck.setId(room);
        }
        else{
            deck = deckDao.findDeckById(room);
        }
        return deck;
    }

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
                            log.error("Cannot join game, " + player.getUsername() + " does not exist.");
                        }
                    }
            );
        }
    }

    public void dealCards(List<Player> players) {
        String room = players.get(0).getRoom();
        Dealer dealer = getDealerInRoom(room);
        Deck deck = getDeckInRoom(room);
        log.info("Deck ID: " + deck.getId() + " has " + deck.getPile().size() + " cards at the beginning of the round");

        for(int i = 0; i < 2; i++) {
            dealer.get_card(deck.draw());
            dealer.hide_card();
            players.forEach(
                    player -> {
                        if(playerDao.findByUsername(player.getUsername()) != null) {
                            dealer.deal_card(deck, player);
                            playerDao.save(player);
                        }
                    }
            );
        }
        dealer.show_card();
        deckDao.save(deck);
        dealerDao.save(dealer);
        log.info("Deck ID: " + deck.getId() + " has " + deck.getPile().size() + " cards after dealing cards");
    }

    public int hit(Player player) {
        if(playerDao.findByUsername(player.getUsername()) != null && playerDao.findByUsername(player.getUsername()).isInGame()) {
            Player foundPlayer = playerDao.findByUsername(player.getUsername());
            String room = foundPlayer.getRoom();
            Deck deck = getDeckInRoom(room);
            Dealer dealer = getDealerInRoom(room);
            dealer.deal_card(deck, foundPlayer);//player gets a card
            deckDao.save(deck);
            playerDao.save(foundPlayer);
            log.info("Deck ID: " + deck.getId() + " has " + deck.getPile().size() + " cards after player hit");
            if (isBust(foundPlayer)) {
//                foundPlayer.setInGame(false);
//                playerDao.save(foundPlayer);
                return 1; //Player Bust
            }
            else if (is21(foundPlayer)) {
//                foundPlayer.setInGame(false);
//                playerDao.save(foundPlayer);
                return 2; //Player Has 21
            }
            return 3; //Player Hit
        }
        return 4; //Player Not Found
    }

    public List<Integer> dealerHit(String room) {
        Dealer dealer = getDealerInRoom(room);
        Deck deck = getDeckInRoom(room);
        while(dealer.getHand_value() < 17){
            dealer.deal_card(deck, dealer);
            dealerDao.save(dealer);
            deckDao.save(deck);
        }
        return checkHands(room);
    }

    public List<Integer> checkHands(String room) {
        List<Integer> winners = new ArrayList<>();
        List<Player> players = playerDao.findAll();
        Dealer dealer = getDealerInRoom(room);
        for(int i = 0; i < players.size(); i++) {
            if (playerDao.findByUsername(players.get(i).getUsername()) != null) {
                if (players.get(i).getHand_value() < dealer.getHand_value() && dealer.getHand_value() <= 21) {
                    players.get(i).setBalance(players.get(i).getBalance() - players.get(i).getBet());
                    playerDao.save(players.get(i));
                    winners.add(players.size());
                } else if (players.get(i).getHand_value() > dealer.getHand_value() && players.get(i).getHand_value() <= 21) {
                    players.get(i).setBalance(players.get(i).getBalance() + players.get(i).getBet());
                    playerDao.save(players.get(i));
                    winners.add(i);
//                } else if (players.get(i).getHand_value() == dealer.getHand_value()) {
//                    playerDao.save(players.get(i));
//                    winners.add(i);
                } else if(dealer.getHand_value() > 21) {
                    if(players.get(i).getHand_value() <= 21) {
                        players.get(i).setBalance(players.get(i).getBalance() + players.get(i).getBet());
                        playerDao.save(players.get(i));
                        winners.add(i);
                    }
                }
            }
        }
        return winners;
    }


//    public boolean isBlackJack(Player player) {
//        if(player.getHand_value() == TWENTY_ONE) {
//            player.setBalance(player.getBalance() + player.getBet()*1.5);
//            playerDao.save(player);
//            return true;
//        }
//        else
//        {
//            return false;
//        }
//
//    }

    public boolean is21(Player player) {
        if(player.getHand_value() == TWENTY_ONE) {
//            player.setBalance(player.getBalance() + player.getBet());
//            playerDao.save(player);
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isBust(Player player) {

        if(player.getHand_value() > TWENTY_ONE) {
            log.info(player.getUsername() + " busted with a score of " + player.getHand_value());
            player.setBalance(player.getBalance() - player.getBet());
            playerDao.save(player);
            return true;
        }
        else{
            return false;
        }


    }

//    public void dealerWon(List<Player> players) {
//        String room = players.get(0).getRoom();
//        Dealer dealer = dealerDao.findDealerById(room);
//        if(dealer.getHand_value() == TWENTY_ONE) {
//            players.forEach(
//                    player -> {
//                        if(playerDao.findByUsername(player.getUsername()) != null) {
//                            player.setBalance(player.getBalance() - player.getBet());
//                            playerDao.save(player);
//                        }
//                    }
//            );
//        }
//    }

//    public boolean dealer_bj_check(String room)
//    {
//        Dealer dealer = dealerDao.findDealerById(room);
//        if(dealer.getHand_value() == 21)
//        {
//            return true;
//        }
//        else
//        {
//            return false;
//        }
//    }

    public void reset(String room) {
        dealerDao.delete(getDealerInRoom(room));
        deckDao.delete(getDeckInRoom(room));
        Optional<Room> foundRoom = roomdao.findById(room);
        foundRoom.get().setCurrentPlayer(0);
        foundRoom.get().setRoundEnded(true);
        foundRoom.get().setGameStarted(true);
        roomdao.save(foundRoom.get());

        List<Player> players = playerDao.findAll().stream().filter(player -> player.getRoom().equals(room)).collect(Collectors.toList());
        players.forEach(
                player -> {
                    if(playerDao.findByUsername(player.getUsername()) != null) {
                        player.setInGame(true);
                        player.clear_hand();
                        player.setBet(100);
                        playerDao.save(player);
                    }
                    else {
                        log.error(player.getUsername() + " does not exist.");
                    }
                }
        );
        dealCards(players);
    }
}




