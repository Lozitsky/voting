package com.kirilo.restaurant.voting.service;

import com.kirilo.restaurant.voting.model.Restaurant;
import com.kirilo.restaurant.voting.util.exception.NotFoundException;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

public interface RestaurantService {
    Restaurant create(Restaurant restaurant);

    void delete(int id) throws NotFoundException;

    Restaurant get(int id) throws NotFoundException;

    Restaurant getByName(String name) throws NotFoundException;

    void update(Restaurant restaurant, int id);

    List<Restaurant> getAll();

    List<Restaurant> getWithDishes();

    Restaurant getWithDishes(int id);

    List<Restaurant> getWithDishes(String date);

    Restaurant getWithDishes(int id, Date dateToday);

    List<Restaurant> getWithVotes(int id, HttpServletResponse response);

    List<Restaurant> getWithVotes(HttpServletResponse response);

    List<Restaurant> getWithVotes(String stringDate, HttpServletResponse response);

//    List<Restaurant> getForVoting();
}
