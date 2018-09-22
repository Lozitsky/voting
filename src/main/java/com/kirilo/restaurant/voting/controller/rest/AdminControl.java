package com.kirilo.restaurant.voting.controller.rest;

import com.kirilo.restaurant.voting.model.Dish;
import com.kirilo.restaurant.voting.model.Restaurant;
import com.kirilo.restaurant.voting.service.DishService;
import com.kirilo.restaurant.voting.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.kirilo.restaurant.voting.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(AdminControl.REST_URL)
public class AdminControl {
    static final String REST_URL = "rest/admin";

    @Autowired
    RestaurantService restaurantService;
    @Autowired
    DishService dishService;

    //rest/admin/restaurant/create
    //    curl -s -X POST -d '{"name":"Restaurant","description":"something"}' -H 'Content-Type: application/json;charset=UTF-8' http://localhost:8080/rest/admin/restaurant/create --user user@yandex.ru:password
    //    curl -s -X POST -d '{"name":"Restaurant5stars","description":"something"}' -H 'Content-Type: application/json;charset=UTF-8' http://localhost:8080/rest/admin/restaurant/create --user admin@gmail.com:password
    //    curl -X POST -H 'Content-Type: application/json' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -i http://localhost:8080/rest/admin/restaurant/create --data '{"name":"Ресторан Golden","description":"text текст"}'
    @PostMapping("/restaurant/create")
    public String restaurantCreate(@RequestBody Restaurant restaurant) {
        checkNew(restaurant);
        restaurantService.create(restaurant);

        return "Save Restaurant is OK";
    }

    //    curl -X POST -H 'Content-Type: application/json' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -i http://localhost:8080/rest/admin/dish/create/10094 --data '{"name":"Some Dish","price":100}'
    //    curl -X POST -H 'Content-Type: application/json' --user admin@gmail.com:password -i http://localhost:8080/rest/admin/dish/create/10094 --data '{"name":"Some Dish4","price":400}'
    @PostMapping("/dish/create/{id}")
    public String dishCreated(@RequestBody Dish dish, @PathVariable int id) {
        checkNew(dish);
        dishService.create(dish, id);
        return "Save Dish is OK";
    }
}
