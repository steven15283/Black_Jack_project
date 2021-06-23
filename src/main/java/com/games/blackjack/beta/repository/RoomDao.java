package com.games.blackjack.beta.repository;

import com.games.blackjack.beta.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomDao extends JpaRepository<Room,String> {
}
