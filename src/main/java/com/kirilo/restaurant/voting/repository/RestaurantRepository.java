package com.kirilo.restaurant.voting.repository;

import com.kirilo.restaurant.voting.model.Restaurant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    Restaurant findById(int id);

    Restaurant findByName(String name);

    @EntityGraph(attributePaths = {"dishes"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT r FROM Restaurant r WHERE r.id=?1 AND r.date=?2")
    Restaurant getWithDishes(int id, Date date);

    @EntityGraph(attributePaths = "dishes", type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT r FROM Restaurant r WHERE r.id=?1")
    Restaurant getWithDishes(int id);

    @EntityGraph(attributePaths = "dishes", type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT r FROM Restaurant r")
    List<Restaurant> getWithDishes();

    @EntityGraph(attributePaths = {"dishes"})
//    @Query("SELECT r FROM Restaurant r WHERE r.date=?1 ORDER BY r.date DESC")
    @Query("SELECT r FROM Restaurant r WHERE r.date BETWEEN ?1 AND ?2 ORDER BY r.date DESC")
    List<Restaurant> getWithDishes(LocalDateTime startDate, LocalDateTime endDate);

    @EntityGraph(attributePaths = {"dishes"})
    @Query("SELECT r FROM Restaurant r WHERE r.date BETWEEN ?1 AND ?2 ORDER BY r.date DESC")
    List<Restaurant> getWithDishes(LocalDate startLocalDate, LocalDate endLocalDate);

    @EntityGraph(attributePaths = {"dishes"})
    @Query("SELECT r FROM Restaurant r WHERE r.date BETWEEN ?1 AND ?2 ORDER BY r.date DESC")
    List<Restaurant> getWithDishes(Date startDate, Date endDate);

/*    @Query("SELECT r FROM Restaurant r WHERE")
    List<Restaurant> getWithDishes(Date dateToday);*/

    @EntityGraph(attributePaths = {"votes"})
    @Query("SELECT r FROM Restaurant r WHERE r.id=?1 AND r.date=?2 ORDER BY r.date DESC")
    Restaurant getWithVotes(int id, Date date);

    @EntityGraph(attributePaths = {"votes"})
    @Query("SELECT r FROM Restaurant r WHERE r.id=?1 ORDER BY r.date DESC")
    List<Restaurant> getWithVotes(int id);

    @EntityGraph(attributePaths = {"votes"})
    @Query("SELECT r FROM Restaurant r ORDER BY r.date DESC")
    List<Restaurant> getWithVotes();

    @EntityGraph(attributePaths = {"votes"})
    @Query("SELECT r FROM Restaurant r WHERE r.date=?1 ORDER BY r.date DESC")
//    List<Restaurant> getWithVotes(Date date);
    List<Restaurant> getWithVotes(Date date);

    @EntityGraph(attributePaths = {"votes"})
    @Query("SELECT r FROM Restaurant r WHERE r.date BETWEEN ?1 AND ?2 ORDER BY r.date DESC")
    List<Restaurant> getWithVotes(LocalDateTime startDate, LocalDateTime endDate);

    @EntityGraph(attributePaths = {"votes"})
    @Query("SELECT r FROM Restaurant r WHERE r.date BETWEEN ?1 AND ?2 ORDER BY r.date DESC")
    List<Restaurant> getWithVotes(Date startDate,   Date endDate);

    @Override
    @Transactional
    Restaurant save(Restaurant restaurant);

    @Transactional
    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.id=?1")
    int delete(int id);
}
