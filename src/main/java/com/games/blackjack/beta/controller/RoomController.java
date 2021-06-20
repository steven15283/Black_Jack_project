package com.games.blackjack.beta.controller;

import com.games.blackjack.beta.model.Room;
import com.games.blackjack.beta.repository.RoomDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/room")
public class RoomController {

    @Autowired
    private RoomDao roomDao;

    @PostMapping("/status")
    public void saveStatus(@RequestBody Room room) {
        roomDao.save(room);
    }

    @GetMapping("/status/{room}")
    public boolean getStatus(@PathVariable("room") String room) {
        if(roomDao.findById(room).isPresent()) {
            return roomDao.findById(room).get().isGameStarted();
        }
        return false;
    }

    @GetMapping("/currentPlayer/{room}")
    public int getCurrentPlayer(@PathVariable("room") String room) {
        if(roomDao.findById(room).isPresent()) {
            return roomDao.findById(room).get().getCurrentPlayer();
        }
        return -1;
    }
}
