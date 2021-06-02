package com.games.blackjack.beta.service;

import com.games.blackjack.beta.model.Player;
import com.games.blackjack.beta.repository.PlayerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerService {
    @Autowired
    private PlayerDao playerDao;

    public List<Player> listAll() {
        ArrayList<Player> data = new ArrayList<>();
        for (Player player : playerDao.findAll()) {
            data.add(player);
        }
        return data;
    }

    public void save(Player player) {
        playerDao.save(player);
    }

    public Player get(String username) {
        return playerDao.findByUsername(username);
    }

    public void delete(String username) {
        playerDao.deleteByUsername(username);
    }
}
