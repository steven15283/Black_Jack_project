package com.games.blackjack.beta.repository;

import com.games.blackjack.beta.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerDao extends CrudRepository<Player,String> {

    Player findByUsername(String username);
    void deleteByUsername(String username);

}

