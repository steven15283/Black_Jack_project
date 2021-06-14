package com.games.blackjack.beta.service;

import com.games.blackjack.beta.model.Card;
import com.games.blackjack.beta.model.Dealer;
import com.games.blackjack.beta.model.Deck;
import com.games.blackjack.beta.model.Player;
import com.games.blackjack.beta.repository.DealerDao;
import com.games.blackjack.beta.repository.DeckDao;
import com.games.blackjack.beta.repository.PlayerDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BlackJackService {

    @Autowired //injects the object into this class. like initializing it see below
    private PlayerDao playerDao;
    @Autowired //injects the object into this class. like initializing it see below
    private DeckDao deckDao;
    @Autowired //injects the object into this class. like initializing it see below
    private DealerDao dealerDao;
    private static int TWENTY_ONE = 21;


    public void joinGame(List<Player> players) {
        if(players.size() > 5) {
            log.error(players.size() + " players is too many players, MAX is 5 players");
        }
        else if(players.size() > 0 && players.size() <= 5) {
            players.forEach(
                    player -> {
                        if(playerDao.findByUsername(player.getUsername()) != null) {
                            System.out.println(player.getUsername());
                            player.setInGame(true);
                            player.setRoom(1);
                            playerDao.save(player);
                        }else {
                            log.error(player.getUsername() + " does not exist.");
                        }
                    }
            );
        }
    }

    public void dealCards(List<Player> players) {
        Deck deck = new Deck();//initialize deck
        Dealer dealer = new Dealer();
        for(int i =0; i < 2;i++)
        {
            dealer.get_card(deck.draw());//dealer gets a card
            System.out.println("dealer get card");
            dealer.hide_card();//takes face value of only the first card to simulate that the second card is face down
            players.forEach(
                    player -> {
                        System.out.println("dealer get card");
                        if(playerDao.findByUsername(player.getUsername()) != null) {
                            player.setBet(100);
                            dealer.deal_card(deck, player);//player gets a card
                            playerDao.save(player);
                        }
                    }
            );
        }
        dealer.show_card();//takes both face values of dealer's hand
        deckDao.save(deck);
        dealerDao.save(dealer);
    }

    public void hit(Player player) {
        if(playerDao.findByUsername(player.getUsername()) != null && playerDao.findByUsername(player.getUsername()).isInGame()) {
            Player foundPlayer = playerDao.findByUsername(player.getUsername());
            Deck deck = deckDao.findAll().get(0);
            Dealer dealer = dealerDao.findAll().get(0);
            dealer.deal_card(deck, foundPlayer);//player gets a card
            deckDao.save(deck);
            dealerDao.save(dealer);
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
            player.setBalance(player.getBalance() + player.getBet());
            playerDao.save(player);
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
        Dealer dealer = dealerDao.findAll().get(0);
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

    public void dealer_reach_limit(Dealer dealer) {
        Deck deck = deckDao.findAll().get(0);
        Card card = new Card();
        do{
            card = deck.draw();
            dealer.setHand_value(dealer.getHand_value()+card.get_face_value());
            dealer.get_card(card);
        }while(dealer.getHand_value() < 17);

    }

    public void checkHand(Player player) {
        Dealer dealer = dealerDao.findAll().get(0);
        if(dealer.getHand_value() > player.getHand_value()) {
            if(playerDao.findByUsername(player.getUsername()) != null && playerDao.findByUsername(player.getUsername()).isInGame()) {
                Player foundPlayer = playerDao.findByUsername(player.getUsername());

                if (foundPlayer.getHand_value() < dealer.getHand_value()) {
                    foundPlayer.setInGame(false);
                    player.setBalance(player.getBalance() - player.getBet());
                    playerDao.save(foundPlayer);
                }
            }
        }
        else if(dealer.getHand_value() == player.getHand_value()){
            if(playerDao.findByUsername(player.getUsername()) != null && playerDao.findByUsername(player.getUsername()).isInGame()) {
                Player foundPlayer = playerDao.findByUsername(player.getUsername());
                player.setBalance(player.getBalance());
                playerDao.save(foundPlayer);
            }
        }
        else {
            if(playerDao.findByUsername(player.getUsername()) != null && playerDao.findByUsername(player.getUsername()).isInGame()) {
                    Player foundPlayer = playerDao.findByUsername(player.getUsername());
                    player.setBalance(player.getBalance() + player.getBet());
                    playerDao.save(foundPlayer);
                }
            }
        }

    public boolean dealer_bj_check(Dealer dealer)
    {
        if(dealer.getHand_value() == 21)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public Dealer getDealer() {
        Dealer dealer = dealerDao.findAll().get(0);
        return dealer;
    }
}




