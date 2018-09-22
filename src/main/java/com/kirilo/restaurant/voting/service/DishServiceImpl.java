package com.kirilo.restaurant.voting.service;

import com.kirilo.restaurant.voting.model.Dish;
import com.kirilo.restaurant.voting.repository.DishRepository;
import com.kirilo.restaurant.voting.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.sql.Date;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    private final DishRepository repository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public DishServiceImpl(DishRepository repository, RestaurantRepository restaurantRepository) {
        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public List<Dish> getWithRestaurantsByDate(Date dateToday) {
        return repository.getDateToday(dateToday);
    }

    @Override
    @Transactional(readOnly = true)
    public Dish create(Dish dish, int id) {
        Assert.notNull(dish, "dish must not be null");
        dish.setRestaurant(restaurantRepository.getOne(id));
        return repository.save(dish);
    }

    public Dish update(Dish dish, int id) {
        if (!dish.isNew() && repository.findById(id).orElse(null) == null) {
            return null;
        }
        return repository.save(dish);
    }
}
