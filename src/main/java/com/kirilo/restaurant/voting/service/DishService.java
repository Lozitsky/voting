package com.kirilo.restaurant.voting.service;

import com.kirilo.restaurant.voting.model.Dish;

import java.time.LocalDateTime;
import java.util.List;

public interface DishService {
    List<Dish> getWithRestaurantsByDate(LocalDateTime dateToday);

    Dish create(Dish dish, int id);

    void update(Dish dish, int id);

    List<Dish> getForVoting();

    int delete(int id);

    Dish get(int id);

    List<Dish> getAll(int restaurantId);
}
