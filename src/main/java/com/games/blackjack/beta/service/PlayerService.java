package com.games.blackjack.beta.service;

import com.games.blackjack.beta.model.Player;
import com.games.blackjack.beta.repository.PlayerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public boolean save(Player player) {
        int size = playerDao.findAll().stream().filter(foundplayer -> foundplayer.getRoom().equals(player.getRoom())).collect(Collectors.toList()).size();
        if(size < 5) {
            player.setBalance(1000);
            playerDao.save(player);
            return true;
        } else {
            return false;
        }
    }

    public Player get(String username) {
        return playerDao.findByUsername(username);
    }

    public void delete(String username) {
        playerDao.delete(playerDao.findByUsername(username));

    }


}
