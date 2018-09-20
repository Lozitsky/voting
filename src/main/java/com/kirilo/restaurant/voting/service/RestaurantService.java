package com.kirilo.restaurant.voting.service;

import com.kirilo.restaurant.voting.model.Restaurant;
import com.kirilo.restaurant.voting.util.exception.NotFoundException;

import java.sql.Date;
import java.util.List;

public interface RestaurantService {
    Restaurant create(Restaurant user);

    void delete(int id) throws NotFoundException;

    Restaurant get(int id) throws NotFoundException;

    Restaurant getByName(String name) throws NotFoundException;

    void update(Restaurant restaurant);

    List<Restaurant> getAll();

    List<Restaurant> getWithDishes();

    List<Restaurant> getWithDishes(int id);

    List<Restaurant> getWithDishes(String date);

    List<Restaurant> getWithVotes(int id);

    Restaurant getWithDishes(int id, Date dateToday);

    List<Restaurant> getWithVotes();
}
