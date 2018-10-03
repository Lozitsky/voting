package com.kirilo.restaurant.voting.service;

import com.kirilo.restaurant.voting.model.Dish;
import com.kirilo.restaurant.voting.model.Restaurant;
import com.kirilo.restaurant.voting.repository.DishRepository;
import com.kirilo.restaurant.voting.repository.RestaurantRepository;
import com.kirilo.restaurant.voting.repository.VotingRepository;
import com.kirilo.restaurant.voting.util.ValidationDateTime;
import com.kirilo.restaurant.voting.util.ValidationUtil;
import com.kirilo.restaurant.voting.util.VotingUtil;
import com.kirilo.restaurant.voting.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    private final DishRepository repository;
    private final RestaurantRepository restaurantRepository;
    private final VotingRepository votingRepository;
    private final ValidationUtil util;
    private final ValidationDateTime dateTime;
    private final VotingUtil valid;

    @Autowired
    public DishServiceImpl(DishRepository repository, RestaurantRepository restaurantRepository, VotingRepository votingRepository) {
        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
        this.votingRepository = votingRepository;
        util = new ValidationUtil();
        dateTime = new ValidationDateTime();
        valid = new VotingUtil();
    }

    @Override
    public List<Dish> getWithRestaurantsByDate(LocalDateTime dateToday) {
        return repository.getDateToday(dateTime.convertToDate(dateToday));
    }

    @Override
    @Transactional
    public Dish create(Dish dish, int id) {
        Assert.notNull(dish, "dish must not be null");
        util.checkNew(dish);
        Restaurant restaurant = restaurantRepository.getOne(id);
        valid.checkVotingEntity(votingRepository, restaurant);
        dish.setRestaurant(restaurant);
        return repository.save(dish);
    }

    @Override
    @Transactional
    public void update(Dish dish, int id) throws NotFoundException{
        Assert.notNull(dish, "dish must not be null");
        util.assureIdConsistent(dish, id);
        Dish updating = repository.findById(id).orElseThrow(() -> new NotFoundException("Dish with id="+ id +" not present in database"));
//                .orElse(null);
/*        if (updating == null) {
            throw new NotFoundException("Dish not present in database");
        }*/
        Restaurant restaurant = updating.getRestaurant();
        dish.setRestaurant(restaurant);
        valid.checkVotingEntity(votingRepository, restaurant);
        repository.save(dish);
    }

    @Override
    public List<Dish> getForVoting() {
        return getWithRestaurantsByDate(LocalDateTime.of(LocalDate.now(), LocalTime.MIN));
    }

    @Override
    public int delete(int id) {
        return util.checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public Dish get(int id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Dish with id="+ id +" not present in database"));
    }

    @Override
    public List<Dish> getAll(int restaurantId) {
        return repository.getAll(restaurantId);
    }

}
