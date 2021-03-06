package com.kirilo.restaurant.voting.repository;

import com.kirilo.restaurant.voting.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
@Repository
public interface DishRepository extends JpaRepository<Dish, Integer> {
    @Query("SELECT d FROM Dish d JOIN FETCH d.restaurant WHERE d.date = ?1 ORDER BY d.date DESC")
    List<Dish> getMenuToday(LocalDateTime date);

    Dish getFirstByRestaurantIdAndDate(int id, LocalDateTime localDateTime);

/*    @Override
    @Transactional
    Dish save(Dish dish);*/

    @Transactional
    @Modifying
    @Query("DELETE FROM Dish d WHERE d.id=:id")
    int delete(int id);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id = ?1 ORDER BY d.date DESC")
    List<Dish> getAll(int restaurantId);
}
