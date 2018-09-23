package com.kirilo.restaurant.voting.service;

import com.kirilo.restaurant.voting.model.Dish;
import com.kirilo.restaurant.voting.model.Restaurant;
import com.kirilo.restaurant.voting.repository.DishRepository;
import com.kirilo.restaurant.voting.repository.RestaurantRepository;
import com.kirilo.restaurant.voting.repository.VotingRepository;
import com.kirilo.restaurant.voting.util.ValidationDateTime;
import com.kirilo.restaurant.voting.util.ValidationUtil;
import com.kirilo.restaurant.voting.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    private final DishRepository repository;
    private final RestaurantRepository restaurantRepository;
    private final VotingRepository votingRepository;
    private final ValidationUtil util;
    private final ValidationDateTime dateTime;

    @Autowired
    public DishServiceImpl(DishRepository repository, RestaurantRepository restaurantRepository, VotingRepository votingRepository) {
        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
        this.votingRepository = votingRepository;
        util = new ValidationUtil();
        dateTime = new ValidationDateTime();
    }

    @Override
    public List<Dish> getWithRestaurantsByDate(Date dateToday) {
        return repository.getDateToday(dateToday);
    }

    @Override
    @Transactional
    public Dish create(Dish dish, int id) {
        Assert.notNull(dish, "dish must not be null");
        util.checkNew(dish);
        Restaurant restaurant = restaurantRepository.getOne(id);
        dish.setRestaurant(restaurant);
/*        if (votingRepository.findByRestaurantIdAndDate(restaurant.getId(), dateTime.getDateToday()).orElse(null) == null) {
            Vote vote = new Vote();
            vote.setRestaurant(restaurant);
            votingRepository.save(vote);
        }*/
        dateTime.checkVotingEntity(votingRepository, restaurant);
        return repository.save(dish);
    }

    @Override
    @Transactional
    public void update(Dish dish, int id) {
        Assert.notNull(dish, "dish must not be null");
        util.assureIdConsistent(dish, id);
        Dish updating = repository.findById(id).orElse(null);
        if (updating == null) {
            throw new NotFoundException("Dish not present in database");
        }
        Restaurant restaurant = updating.getRestaurant();
        dish.setRestaurant(restaurant);
        dateTime.checkVotingEntity(votingRepository, restaurant);
        repository.save(dish);
    }

    @Override
    public List<Dish> getForVoting() {
        return getWithRestaurantsByDate(dateTime.getDateToday());
    }

}
