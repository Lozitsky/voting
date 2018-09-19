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
import java.sql.Date;
import java.util.List;

import static com.kirilo.restaurant.voting.security.SecurityUtil.getUser;
import static com.kirilo.restaurant.voting.util.ValidationDateTime.alreadyVoted;
import static com.kirilo.restaurant.voting.util.ValidationDateTime.getDateToday;

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

    //    @Secured("ROLE_ADMIN")
    @GetMapping("/restaurants")
    public List<Restaurant> goToRestaurants() {
        List<Restaurant> all = restaurantService.getAll();
        logger.info("Returning all restaurants: " + all.toString());
        return all;
    }

    @GetMapping("/dishes")
    public List<Dish> restaurantsWithDishes(){
        return dishService.getWithRestaurantsByDate(getDateToday());
    }


    @GetMapping("/result/{id}")
    public List<Vote> votesWithRestaurants(@PathVariable int id) {
        Restaurant restaurant = restaurantService.getWithVotes(id);
        return restaurant.getVotes();
    }

    //https://stackoverflow.com/questions/29085295/spring-mvc-restcontroller-and-redirect
    @GetMapping("/votes")
    public List<Vote> votesToday(HttpServletResponse response) throws IOException {
        logger.info("Get User from session");
        User user = getUser();

        if (!alreadyVoted(user)) {
            logger.info("User " + user.getName() + " not voted yet");
            response.sendRedirect("restaurants");
        }
        Date dateToday = getDateToday();

        logger.info("User " + user.getName() + " can see result for day " + dateToday);

        List<Vote> votes = votingService.getWithRestaurantsByDate(dateToday);
        return votes;
    }

}
