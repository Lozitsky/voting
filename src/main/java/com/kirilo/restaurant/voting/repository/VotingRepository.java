package com.kirilo.restaurant.voting.repository;

import com.kirilo.restaurant.voting.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface VotingRepository extends JpaRepository<Vote, Integer> {

    Vote getByRestaurantIdAndDate(int id, Date date);
}
