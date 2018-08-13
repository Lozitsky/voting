package com.kirilo.restaurant.voting.repository;

import com.kirilo.restaurant.voting.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepo extends JpaRepository<Restaurant, Integer> {
    Restaurant findById(int id);
}
