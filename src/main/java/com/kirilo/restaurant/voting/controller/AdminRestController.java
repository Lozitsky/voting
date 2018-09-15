package com.kirilo.restaurant.voting.controller;

import com.kirilo.restaurant.voting.model.Restaurant;
import com.kirilo.restaurant.voting.service.RestaurantService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(AdminRestController.REST_URL)
//@PreAuthorize("hasRole('ADMIN')")
//@Secured("ROLE_ADMIN")
public class AdminRestController {
    static final String REST_URL = "rest/admin/restaurants/";

    public final Logger logger = Logger.getLogger(VotingController.class);

    @Autowired
    RestaurantService restaurantService;

//    @Secured("ROLE_ADMIN")
    @GetMapping()
    public List<Restaurant> goToRestaurants() {
        List<Restaurant> all = restaurantService.getAll();
        logger.info("Returning all restaurants: " + all.toString());
        return all;
    }

}
