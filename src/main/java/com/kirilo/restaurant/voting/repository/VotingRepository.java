package com.kirilo.restaurant.voting.repository;

import com.kirilo.restaurant.voting.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public interface VotingRepository extends JpaRepository<Vote, Integer> {

    Optional<Vote> findByRestaurantIdAndDate(int id, Date date);

    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant WHERE v.date = ?1")
    List<Vote> getWithRestaurantsByDate(Date date);
}
