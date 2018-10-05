package com.kirilo.restaurant.voting.repository;

import com.kirilo.restaurant.voting.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public interface VotingRepository extends JpaRepository<Vote, Integer> {

    @Query("SELECT v FROM Vote v WHERE v.restaurant.id =?1 and v.date =?2")
    Optional<Vote> findByRestaurantIdAndDate(int id, LocalDateTime date);

    @SuppressWarnings("JpaQlInspection")
    @Query("SELECT v FROM Vote v WHERE v.restaurant.id = ?1 and v.date BETWEEN ?2 AND ?3")
    Optional<Vote> getByRestaurantIdAndDate(int id, LocalDateTime start, LocalDateTime end);

    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant WHERE v.date = ?1 ORDER BY v.date DESC")
    List<Vote> getWithRestaurantsByDate(LocalDateTime date);

    @Override
    @Transactional
    Vote save(Vote vote);
}
