package com.kirilo.restaurant.voting.service;

import com.kirilo.restaurant.voting.model.Restaurant;
import com.kirilo.restaurant.voting.repository.RestaurantRepository;
import com.kirilo.restaurant.voting.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import static com.kirilo.restaurant.voting.util.ValidationDateTime.*;
import static com.kirilo.restaurant.voting.util.ValidationUtil.checkNotFoundWithId;
import static com.kirilo.restaurant.voting.util.ValidationUtil.checkNotFoundWithName;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository repository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository repository) {
        this.repository = repository;
    }

    @Override
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant);
    }

    @Override
    public void delete(int id) throws NotFoundException {

    }

    @Override
    public Restaurant get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.findById(id), id);
    }

    @Override
    public Restaurant getByName(String name) throws NotFoundException {
        return null;
    }

    @Override
    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithName(repository.save(restaurant), restaurant.getName());
    }

    @Override
    public List<Restaurant> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Restaurant> getWithDishes(int id) {
//        return repository.getWithDishes(id);
        return checkNotFoundWithId(repository.getWithDishes(id), id);
    }

    @Override
    public List<Restaurant> getWithDishes(String stringDate) {
        LocalDateTime startLocalDateTime = convertToLocalDateTime(getLocalDate(stringDate), getLocalTime("00:00:00"));
        LocalDateTime endLocalDateTime = convertToLocalDateTime(getLocalDate(stringDate), getLocalTime("23:59:59"));
//        return repository.getWithDishes(startLocalDateTime, endLocalDateTime);
//        return repository.getWithDishes(convertToDate(getLocalDate(stringDate)), convertToDate(getLocalDate(stringDate)));
        return repository.getWithDishes(convertToDate(startLocalDateTime), convertToDate(endLocalDateTime));
    }

    @Override
    public List<Restaurant> getWithDishes() {
        return repository.getWithDishes();
    }

    @Override
    public Restaurant getWithDishes(int id, Date date) {
        return repository.getWithDishes(id, date);
    }

    @Override
    public List<Restaurant> getWithVotes() {
        return repository.getWithVotes();
    }

    @Override
    public List<Restaurant> getWithVotes(int id) {
        return checkNotFoundWithId(repository.getWithVotes(id), id);
    }
}
