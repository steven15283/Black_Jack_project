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
//        if(roomDao.findById(room.getId()).isPresent()) {
//            Room foundRoom = roomDao.findById(room.getId()).get();
//            foundRoom.
//        } else {
        System.out.println(room.toString());
            roomDao.save(room);
//        }
    }

    @GetMapping("/status/{room}")
    public boolean getStatus(@PathVariable("room") String room) {
        if(roomDao.findById(room).isPresent()) {
            return roomDao.findById(room).get().isGameStarted();
        }
        return false;
    }

    @GetMapping("/currentPlayer/{room}")
    public Room getCurrentPlayer(@PathVariable("room") String room) {
        if(roomDao.findById(room).isPresent()) {
            return roomDao.findById(room).get();
        }
        Room newRoom = new Room();
        newRoom.setCurrentPlayer(-1);
        return newRoom;
    }

}
