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

    @Autowired //injects the object into this class. like initializing it see below
    private PlayerDao playerDao;
    @Autowired //injects the object into this class. like initializing it see below
    private DeckDao deckDao;
    @Autowired //injects the object into this class. like initializing it see below
    private DealerDao dealerDao;
    @Autowired //injects the object into this class. like initializing it see below
    private RoomDao roomdao;
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
                            log.error("Cannot join game, " + player.getUsername() + " does not exist.");
                        }
                    }
            );
        }
    }

    public void dealCards(List<Player> players) {
        String room = players.get(0).getRoom();
        Dealer dealer;
        Deck deck;

        if(dealerDao.findDealerById(room) == null){
            System.out.println("dealcards.dealerDao.dealerDao.findById(room) == null");
            dealer = new Dealer();
            dealer.setId(room);
        }
        else{
            System.out.println("dealcards.dealerDao.dealerDao.findById(room) != null");
            dealer = dealerDao.findDealerById(room);
        }

        if(deckDao.findDeckById(room) == null){
            System.out.println("deckDao.findById(room) == null");
            deck = new Deck();
            deck.setId(room);
        }
        else{
            System.out.println("deckDao.findById(room) != null");
            deck = deckDao.findDeckById(room);
        }

        for(int i =0; i < 2;i++)
        {
            dealer.get_card(deck.draw());//dealer gets a card
            dealer.hide_card();//takes face value of only the first card to simulate that the second card is face down
            players.forEach(
                    player -> {
                        if(playerDao.findByUsername(player.getUsername()) != null) {
                            dealer.deal_card(deck, player);//player gets a card
                            playerDao.save(player);
                        }
                    }
            );
        }
        dealer.show_card();//takes both face values of dealer's hand
        deckDao.save(deck);
        dealerDao.save(dealer);
        System.out.println("Deck ID: " + deck.getId() + "has " + deck.getPile().size() + " cards");

    }

    public int hit(Player player) {
        if(playerDao.findByUsername(player.getUsername()) != null && playerDao.findByUsername(player.getUsername()).isInGame()) {
            Player foundPlayer = playerDao.findByUsername(player.getUsername());
            String room = foundPlayer.getRoom();
            System.out.println(room);
            Deck deck = deckDao.findDeckById(room);
            Dealer dealer = dealerDao.findDealerById(room);
            dealer.deal_card(deck, foundPlayer);//player gets a card
            deckDao.save(deck);
            dealerDao.save(dealer);
            playerDao.save(foundPlayer);
            System.out.println("Deck ID: " + deck.getId() + " has " + deck.getPile().size() + " cards");
            if (isBust(foundPlayer)) {
                foundPlayer.setInGame(false);
                playerDao.save(foundPlayer);
                return 1; //Player Bust
            }
            else if (is21(foundPlayer)) {
                foundPlayer.setInGame(false);
                playerDao.save(foundPlayer);
                return 2; //Player Has 21
            }
            return 3; //Player Hit
        }
        return 4; //Player Not Found
    }

    public List<Integer> dealerHit(String room) {
        System.out.println("dealerHit");
        Dealer dealer = dealerDao.findDealerById(room);
        Deck deck = deckDao.findDeckById(room);
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
        Dealer dealer = dealerDao.findDealerById(room);
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


    public boolean isBlackJack(Player player) {
        if(player.getHand_value() == TWENTY_ONE) {
            player.setBalance(player.getBalance() + player.getBet()*1.5);
            playerDao.save(player);
            return true;
        }
        else
        {
            return false;
        }

    }

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

    public void dealerWon(List<Player> players) {
        String room = players.get(0).getRoom();
        Dealer dealer = dealerDao.findDealerById(room);
        if(dealer.getHand_value() == TWENTY_ONE) {
            players.forEach(
                    player -> {
                        if(playerDao.findByUsername(player.getUsername()) != null) {
                            player.setBalance(player.getBalance() - player.getBet());
                            playerDao.save(player);
                        }
                    }
            );
        }
    }

    public boolean dealer_bj_check(String room)
    {
        Dealer dealer = dealerDao.findDealerById(room);
        if(dealer.getHand_value() == 21)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public Dealer getDealer(String room) {
        Dealer dealer = dealerDao.findDealerById(room);
        return dealer;
    }

    public void reset(String room) {
        Dealer dealer = dealerDao.findDealerById(room);
        dealer.clear_hand();
        System.out.println("dealer hand:" + dealer.getHand_value());
        dealerDao.save(dealer);
        Deck deck = deckDao.findDeckById(room);
        Optional<Room> room1 = roomdao.findById(room);
        room1.get().setCurrentPlayer(0);
        roomdao.save(room1.get());
        List <Player> players = playerDao.findAll().stream().filter(player -> player.getRoom().equals(room)).collect(Collectors.toList());
        players.forEach(
                player -> {
                    if(playerDao.findByUsername(player.getUsername()) != null) {
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
        System.out.println(dealer.toString());
//        players.forEach(
//                player -> {
//                    if(playerDao.findByUsername(player.getUsername()) != null) {
//                        player.clear_hand();
//                        System.out.println(player.getUsername() + " hand:" + player.getHand_value());
//                        player.setBet(100);
//                        //player.setInGame(true);
//                        playerDao.save(player);
//                    }
//                    else {
//                        log.error(player.getUsername() + " does not exist.");
//                    }
//                }
//        );
//
//        for(int i =0; i < 2;i++)
//        {
//            dealer.get_card(deck.draw());//dealer gets a card
//            System.out.println("dealer get card");
//            dealer.hide_card();//takes face value of only the first card to simulate that the second card is face down
//            players.forEach(
//                    player -> {
//                        System.out.println("player get card");
//                        if(playerDao.findByUsername(player.getUsername()) != null) {
//                            dealer.deal_card(deck, player);//player gets a card
//                            playerDao.save(player);
//                        }
//                    }
//            );
//        }
//        dealer.show_card();//takes both face values of dealer's hand
//        dealerDao.save(dealer);
//        deckDao.save(deck);

    }
}




