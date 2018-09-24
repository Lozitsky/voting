package com.kirilo.restaurant.voting.controller;

import com.kirilo.restaurant.voting.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RootController {
    @Autowired
    public RootController(RestaurantService restaurantService) {
    }

    @GetMapping("/")
    public String goToVote() {
        return "Incorrect command";
    }
}
