package com.kirilo.restaurant.voting.controller.rest;

import com.kirilo.restaurant.voting.controller.VotingController;
import com.kirilo.restaurant.voting.model.Restaurant;
import com.kirilo.restaurant.voting.service.RestaurantService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(RestaurantControl.REST_URL)
//@PreAuthorize("hasRole('ADMIN')")
//@Secured("ROLE_ADMIN")
public class RestaurantControl {
    static final String REST_URL = "rest/admin";

    public final Logger logger = Logger.getLogger(VotingController.class);

    @Autowired
    RestaurantService restaurantService;

//    @Secured("ROLE_ADMIN")
    @GetMapping("/restaurants")
    public List<Restaurant> goToRestaurants() {
        List<Restaurant> all = restaurantService.getAll();
        logger.info("Returning all restaurants: " + all.toString());
        return all;
    }

}
