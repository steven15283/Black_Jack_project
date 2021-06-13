package com.games.blackjack.beta.repository;

import com.games.blackjack.beta.model.Dealer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealerDao extends JpaRepository<Dealer, Long> {
}
