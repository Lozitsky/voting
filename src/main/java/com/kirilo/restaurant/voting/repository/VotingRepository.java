package com.kirilo.restaurant.voting.repository;

import com.kirilo.restaurant.voting.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface VotingRepository extends JpaRepository<Vote, Integer> {

    Vote getByRestaurantIdAndDate(int id, Date date);

    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant WHERE v.date = ?1")
    List<Vote> getWithRestaurantsByDate(Date date);
}
