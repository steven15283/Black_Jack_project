package com.games.blackjack.beta.repository;

import com.games.blackjack.beta.model.Deck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeckDao extends JpaRepository <Deck, Long> {
}
