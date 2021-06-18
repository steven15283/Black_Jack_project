package com.games.blackjack.beta.service;

import com.games.blackjack.beta.model.Player;
import com.games.blackjack.beta.repository.PlayerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service //annotation to tell framework that this is the service class
public class PlayerService {

    @Autowired //injects the object into this class. like initializing it see below
    private PlayerDao playerDao;

    //can also be initialized like this
//    PlayerService(PlayerDao playerDao){
//        this.playerDao = playerDao;
//    }

    public List<Player> listAll() {
        return playerDao.findAll();
    }

    public void save(Player player) {
        player.setBalance(1000);
        playerDao.save(player);
    }

    public Player get(String username) {
        return playerDao.findByUsername(username);
    }

    public void delete(String username) {
        playerDao.delete(playerDao.findByUsername(username));

    }


}
