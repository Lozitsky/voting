package com.kirilo.restaurant.voting.service;

import com.kirilo.restaurant.voting.model.Dish;

import java.util.Date;
import java.util.List;

public interface DishService {
    List<Dish> getWithRestaurantsByDate(Date dateToday);

    Dish create(Dish dish, int id);

    void update(Dish dish, int id);

    List<Dish> getForVoting();

    int delete(int id);

    Dish get(int id);
}
