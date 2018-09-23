package com.kirilo.restaurant.voting.controller.rest;

import com.kirilo.restaurant.voting.controller.VotingController;
import com.kirilo.restaurant.voting.model.Dish;
import com.kirilo.restaurant.voting.model.Restaurant;
import com.kirilo.restaurant.voting.service.DishService;
import com.kirilo.restaurant.voting.service.RestaurantService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

//http://qaru.site/questions/37305/avoid-jackson-serialization-on-non-fetched-lazy-objects
@RestController
@RequestMapping(RestaurantControl.REST_URL)
//@PreAuthorize("hasRole('ADMIN')")
//@Secured("ROLE_ADMIN")
public class RestaurantControl {
    static final String REST_URL = "rest";

    public final Logger logger = Logger.getLogger(VotingController.class);

    private final DishService dishService;
    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantControl(DishService dishService, RestaurantService restaurantService) {
        this.dishService = dishService;
        this.restaurantService = restaurantService;
    }

    //    curl -X GET -H 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ=' -i http://localhost:8080/rest/restaurants
    @GetMapping("/restaurants")
    public List<Restaurant> getAll() {
        List<Restaurant> restaurants = restaurantService.getAll();
        logger.info("Returning restaurants restaurants: " + restaurants.toString());
        return restaurants;
    }

    //    curl -X GET -H 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ=' -i http://localhost:8080/rest/restaurant/10004
    @GetMapping("/restaurant/{id}")
    public Restaurant goToRestaurant(@PathVariable int id) {
        Restaurant restaurant = restaurantService.get(id);
        logger.info("Returning restaurant: " + restaurant.toString());
        return restaurant;
    }

    //curl -X GET -H 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ=' -i http://localhost:8080/rest/restaurants/dishes
    @GetMapping("/restaurants/dishes")
    public List<Restaurant> getWithDishes() {
        List<Restaurant> restaurants = restaurantService.getWithDishes();
        logger.info("Returning all restaurants with dishes: " + restaurants.toString());
        return restaurants;
    }

    //    curl -X GET -H 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ=' -i http://localhost:8080/rest/restaurant/dishes/10006
    @GetMapping("/restaurant/dishes/{id}")
    public Restaurant getWithDishesById(@PathVariable int id) {
        Restaurant restaurant = restaurantService.getWithDishes(id);
        logger.info("Returning restaurant with dishes by id{" + id + "}: " + restaurant.toString());
        return restaurant;
    }

    //    curl -X GET -H 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ=' -i http://localhost:8080/rest/restaurants/dishes/date/2018-09-19
    @GetMapping("/restaurants/dishes/date/{textDate}")
    public List<Restaurant> getWithDishesByDate(@PathVariable String textDate) {
        List<Restaurant> restaurants = restaurantService.getWithDishes(textDate);
        logger.info("Returning all restaurants with dishes by date{" + textDate + "}: " + restaurants.toString());
        return restaurants;
    }

    @GetMapping("/restaurants/votes/date/{textDate}")
    public List<Restaurant> getWithVotesByDate(@PathVariable String textDate, HttpServletResponse response) {
        List<Restaurant> restaurants = restaurantService.getWithVotes(textDate, response);
        logger.info("Returning all restaurants with votes by date: " + restaurants.toString());
        return restaurants;
    }

    @GetMapping("/restaurants/votes")
    public List<Restaurant> getWithVotes(HttpServletResponse response) {
        List<Restaurant> restaurants = restaurantService.getWithVotes(response);
        logger.info("Returning all restaurants with votes: " + restaurants.toString());
        return restaurants;
    }

    @GetMapping("/restaurant/votes/{id}")
    public List<Restaurant> getWithVotesById(@PathVariable int id, HttpServletResponse response) {
        List<Restaurant> restaurants = restaurantService.getWithVotes(id, response);
        logger.info("Returning all restaurants with votes by id: " + restaurants.toString());
        return restaurants;
    }

    //    curl -X GET -H 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ=' -i http://localhost:8080/rest/dishes/forVoting
    @GetMapping("/dishes/forVoting")
    public List<Dish> dishesWithRestaurants() {
        List<Dish> dishes = dishService.getForVoting();
        logger.info("Returning all dishes with restaurants for voting: " + dishes.toString());
        return dishes;
    }
}
