package com.kirilo.restaurant.voting.service;

import com.kirilo.restaurant.voting.model.Dish;

import java.sql.Date;
import java.util.List;

public interface DishService {
    List<Dish> getWithRestaurantsByDate(Date dateToday);

    Dish create(Dish dish, int id);
}
