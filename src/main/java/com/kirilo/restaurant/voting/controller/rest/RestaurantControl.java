package com.kirilo.restaurant.voting.controller.rest;

import com.kirilo.restaurant.voting.controller.VotingController;
import com.kirilo.restaurant.voting.model.Dish;
import com.kirilo.restaurant.voting.model.Restaurant;
import com.kirilo.restaurant.voting.model.User;
import com.kirilo.restaurant.voting.model.Vote;
import com.kirilo.restaurant.voting.service.DishService;
import com.kirilo.restaurant.voting.service.RestaurantService;
import com.kirilo.restaurant.voting.service.VotingService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import static com.kirilo.restaurant.voting.security.SecurityUtil.getUser;
import static com.kirilo.restaurant.voting.util.ValidationDateTime.getDateToday;
import static com.kirilo.restaurant.voting.util.ValidationUtil.checkVoting;

//http://qaru.site/questions/37305/avoid-jackson-serialization-on-non-fetched-lazy-objects
@RestController
@RequestMapping(RestaurantControl.REST_URL)
//@PreAuthorize("hasRole('ADMIN')")
//@Secured("ROLE_ADMIN")
public class RestaurantControl {
    //    static final String REST_URL = "rest/admin";
    static final String REST_URL = "rest";

    public final Logger logger = Logger.getLogger(VotingController.class);

    @Autowired
    DishService dishService;

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    VotingService votingService;

    @GetMapping("/restaurants")
    public List<Restaurant> getAll() {
        List<Restaurant> restaurants = restaurantService.getAll();
        logger.info("Returning restaurants restaurants: " + restaurants.toString());
        return restaurants;
    }

    @GetMapping("/restaurant/{id}")
    public Restaurant goToRestaurant(@PathVariable int id) {
        Restaurant restaurant = restaurantService.get(id);
        logger.info("Returning restaurant: " + restaurant.toString());
        return restaurant;
    }

    @GetMapping("/restaurants/dishes")
    public List<Restaurant> getWithDishes() {
        List<Restaurant> restaurants = restaurantService.getWithDishes();
        logger.info("Returning all restaurants with dishes: " + restaurants.toString());
        return restaurants;
    }

    @GetMapping("/restaurants/dishes/{id}")
    public List<Restaurant> getWithDishesById(@PathVariable int id) {
        List<Restaurant> restaurants = restaurantService.getWithDishes(id);
        logger.info("Returning all restaurants with dishes by id{" + id + "}: " + restaurants.toString());
        return restaurants;
    }

    @GetMapping("/restaurants/dishes/date/{localDate}")
    public List<Restaurant> getWithDishesByDate(@PathVariable String localDate) throws ParseException {
        List<Restaurant> restaurants = restaurantService.getWithDishes(localDate);
        logger.info("Returning all restaurants with dishes by date{" + localDate + "}: " + restaurants.toString());
        return restaurants;
    }

    @GetMapping("/restaurants/votes/date/{localDate}")
    public List<Restaurant> getWithVotesByDate(@PathVariable String localDate, HttpServletResponse response) throws IOException {
        checkVoting(getUser(), response);
        List<Restaurant> restaurants = restaurantService.getWithVotes(localDate);
        logger.info("Returning all restaurants with votes by date: " + restaurants.toString());
        return restaurants;
    }

    @GetMapping("/restaurants/votes")
    public List<Restaurant> getWithVotes(HttpServletResponse response) throws IOException {
        checkVoting(getUser(), response);
        List<Restaurant> restaurants = restaurantService.getWithVotes();
        logger.info("Returning all restaurants with votes: " + restaurants.toString());
        return restaurants;
    }

    @GetMapping("/restaurants/votes/{id}")
    public List<Restaurant> getWithVotesById(@PathVariable int id, HttpServletResponse response) throws IOException {
        checkVoting(getUser(), response);
        List<Restaurant> restaurants = restaurantService.getWithVotes(id);
        logger.info("Returning all restaurants with votes by id: " + restaurants.toString());
        return restaurants;
    }



    @GetMapping("/dishes/restaurants")
    public List<Dish> dishesWithRestaurants() {
        return dishService.getWithRestaurantsByDate(getDateToday());
    }


    @GetMapping("/result/{id}")
    public List<Restaurant> votesWithRestaurants(@PathVariable int id) {
        List<Restaurant> restaurant = restaurantService.getWithVotes(id);
        return restaurant;
    }

    //https://stackoverflow.com/questions/29085295/spring-mvc-restcontroller-and-redirect
    @GetMapping("/votes")
    public List<Vote> votesToday(HttpServletResponse response) throws IOException {
        logger.info("Get authorized User ");
        User user = getUser();

        checkVoting(user, response);

        logger.info("User " + user.getName() + " can see result for today ");

        List<Vote> votes = votingService.getWithRestaurantsToday();
        return votes;
    }

}
