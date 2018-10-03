package com.kirilo.restaurant.voting.service;

import com.kirilo.restaurant.voting.model.Restaurant;
import com.kirilo.restaurant.voting.repository.RestaurantRepository;
import com.kirilo.restaurant.voting.util.ValidationDateTime;
import com.kirilo.restaurant.voting.util.ValidationUtil;
import com.kirilo.restaurant.voting.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository repository;
    private final ValidationUtil util;
    private final ValidationDateTime dateTime;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository repository) {
        this.repository = repository;
        util = new ValidationUtil();
        dateTime = new ValidationDateTime();
    }

    @Override
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        util.checkNew(restaurant);
        return repository.save(restaurant);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        util.checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public Restaurant get(int id) throws NotFoundException {
        return util.checkNotFoundWithId(repository.findById(id), id);
    }

    @Override
    public Restaurant getByName(String name) throws NotFoundException {
        return repository.findByName(name);
    }

    @Override
    public void update(Restaurant restaurant, int id) {
        Assert.notNull(restaurant, "restaurant must not be null");
        util.assureIdConsistent(restaurant, id);
        if (repository.findById(restaurant.getId()).orElse(null) == null) {
            throw new NotFoundException("Restaurant not present in database");
        }
        util.checkNotFoundWithName(repository.save(restaurant), restaurant.getName());
    }

    @Override
    public List<Restaurant> getAll() {
        return repository.findAll();
    }

    @Override
    public Restaurant getWithDishes(int id) {
        return util.checkNotFoundWithId(repository.getWithDishes(id), id);
    }

    @Override
    public List<Restaurant> getWithDishes(String stringDate) {
        return repository.getWithDishes(dateTime.getDate(stringDate, "00:00:00"), dateTime.getDate(stringDate, "23:59:59"));
    }

    @Override
    public List<Restaurant> getWithDishes() {
        return repository.getWithDishes();
    }

    @Override
    public Restaurant getWithDishes(int id, LocalDateTime date) {
        return repository.getWithDishes(id, date);
    }

    @Override
    public List<Restaurant> getWithVotes(HttpServletResponse response) {
//            dateTime.checkVoting(getUser(), response);
        return repository.getWithVotes();
    }

    @Override
    public List<Restaurant> getWithVotes(String stringDate, HttpServletResponse response) {
//            dateTime.checkVoting(getUser(), response);
        return repository.getWithVotes(dateTime.getDate(stringDate, "00:00:00"), dateTime.getDate(stringDate, "23:59:59"));
    }

    @Override
    public List<Restaurant> getWithVotes(int id, HttpServletResponse response) {
//            dateTime.checkVoting(getUser(), response);
        return util.checkNotFoundWithId(repository.getWithVotes(id), id);
    }
}
