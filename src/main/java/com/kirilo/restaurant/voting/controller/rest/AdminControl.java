package com.kirilo.restaurant.voting.controller.rest;

import com.kirilo.restaurant.voting.model.Dish;
import com.kirilo.restaurant.voting.model.Restaurant;
import com.kirilo.restaurant.voting.service.DishService;
import com.kirilo.restaurant.voting.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AdminControl.REST_URL)
public class AdminControl {
    static final String REST_URL = "rest/admin";
    private final RestaurantService restaurantService;
    private final DishService dishService;

    @Autowired
    public AdminControl(RestaurantService restaurantService, DishService dishService) {
        this.restaurantService = restaurantService;
        this.dishService = dishService;
    }

    //    curl -s -X POST -d '{"name":"Restaurant","description":"something"}' -H 'Content-Type: application/json;charset=UTF-8' http://localhost:8080/rest/admin/restaurant/create --user user@yandex.ru:password
    //    curl -s -X POST -d '{"name":"Restaurant5stars","description":"something"}' -H 'Content-Type: application/json;charset=UTF-8' http://localhost:8080/rest/admin/restaurant/create --user admin@gmail.com:password
    //    curl -X POST -H 'Content-Type: application/json' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -i http://localhost:8080/rest/admin/restaurant/create --data '{"name":"Ресторан Golden Star","description":"text текст"}'
    @PostMapping("/restaurant/create")
    public String restaurantCreate(@RequestBody Restaurant restaurant) {
        restaurantService.create(restaurant);
        return "Save Restaurant is OK";
    }

    //    curl -X POST -H 'Content-Type: application/json' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -i http://localhost:8080/rest/admin/restaurant/update/10005 --data '{"name":"Ресторан New Star","description":"text текст"}'
    @PostMapping("/restaurant/update/{id}")
    public String restaurantUpdate(@RequestBody Restaurant restaurant, @PathVariable int id) {
        restaurantService.update(restaurant, id);
        return "Update Restaurant is OK";
    }

    //    curl -X POST -H 'Content-Type: application/json' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -i http://localhost:8080/rest/admin/dish/create/10094 --data '{"name":"Some Dish","price":100}'
    //    curl -X POST -H 'Content-Type: application/json' --user admin@gmail.com:password -i http://localhost:8080/rest/admin/dish/create/10094 --data '{"name":"Some Dish4","price":400}'
    @PostMapping("/dish/create/{id}")
    public String dishCreated(@RequestBody Dish dish, @PathVariable int id) {
        dishService.create(dish, id);
        return "Save Dish is OK";
    }

    //    curl -X POST -H 'Content-Type: application/json' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -i http://localhost:8080/rest/admin/dish/update/10020 --data '{"name":"Some Dish3 блюдо","price":350}'
    @PostMapping("/dish/update/{id}")
    public String dishUpdated(@RequestBody Dish dish, @PathVariable int id) {
        dishService.update(dish, id);
        return "Update Dish is OK";
    }
}
