package com.games.blackjack.beta.service;

import com.games.blackjack.beta.model.Player;
import com.games.blackjack.beta.model.Room;
import com.games.blackjack.beta.repository.PlayerDao;
import com.games.blackjack.beta.repository.RoomDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service //annotation to tell framework that this is the service class
public class PlayerService {

    @Autowired //injects the object into this class. like initializing it see below
    private PlayerDao playerDao;

    @Autowired //injects the object into this class. like initializing it see below
    private RoomDao roomDao;

    public List<Player> listAll() {
        return playerDao.findAll();
    }

    public boolean save(Player player) {
        int size = playerDao.findAll().stream().filter(foundplayer -> foundplayer.getRoom().equals(player.getRoom())).collect(Collectors.toList()).size();
        if(size < 5) {
            player.setBalance(1000);
            playerDao.save(player);
            if(roomDao.findById(player.getRoom()).isPresent()) {
                Room room = roomDao.findById(player.getRoom()).get();
                List<Player> players = room.getPlayers();
                if(players.stream().filter(foundplayer -> foundplayer.getUsername().equals(player.getUsername())).collect(Collectors.toList()).size() == 0) {
                    players.add(player);
                    room.setPlayers(players);
                    roomDao.save(room);
                }
            } else {
                Room room = new Room();
                List<Player> players = new ArrayList<>();
                players.add(player);
                room.setPlayers(players);
                room.setId(player.getRoom());
                roomDao.save(room);
            }
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

    public List<Player> returnPlayersInRoom(String room) {
        return roomDao.findById(room).get().getPlayers();
    }

}
