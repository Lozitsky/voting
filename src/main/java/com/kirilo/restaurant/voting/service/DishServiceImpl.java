package com.kirilo.restaurant.voting.service;

import com.kirilo.restaurant.voting.model.Dish;
import com.kirilo.restaurant.voting.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    private final DishRepository repository;

    @Autowired
    public DishServiceImpl(DishRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Dish> getWithRestaurantsByDate(Date dateToday) {
        return repository.getDateToday(dateToday);
    }
}
