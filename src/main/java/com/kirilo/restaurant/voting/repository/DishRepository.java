package com.kirilo.restaurant.voting.repository;

import com.kirilo.restaurant.voting.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish, Integer> {
    @Query("SELECT d FROM Dish d JOIN FETCH d.restaurant WHERE d.date = ?1")
    List<Dish> getDateToday(Date date);
}
