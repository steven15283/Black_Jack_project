package com.games.blackjack.beta.repository;

import com.games.blackjack.beta.model.Dealer;
import com.games.blackjack.beta.model.Deck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface DeckDao extends JpaRepository <Deck, String> {
    Deck findDeckById(String id);
}
