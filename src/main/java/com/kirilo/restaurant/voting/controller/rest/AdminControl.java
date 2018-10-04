package com.kirilo.restaurant.voting.controller.rest;

import com.kirilo.restaurant.voting.model.Dish;
import com.kirilo.restaurant.voting.model.Restaurant;
import com.kirilo.restaurant.voting.model.User;
import com.kirilo.restaurant.voting.service.DishService;
import com.kirilo.restaurant.voting.service.RestaurantService;
import com.kirilo.restaurant.voting.service.UserService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AdminControl.REST_URL)
public class AdminControl {
    static final String REST_URL = "rest/admin";
    private final RestaurantService restaurantService;
    private final DishService dishService;
    private final UserService userService;
    private final Logger logger = Logger.getLogger(AdminControl.class);

    @Autowired
    public AdminControl(RestaurantService restaurantService, DishService dishService, UserService userService) {
        this.restaurantService = restaurantService;
        this.dishService = dishService;
        this.userService = userService;
    }

    //    curl -X POST -H 'Content-Type: application/json' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -i http://localhost:8080/rest/admin/restaurant --data '{"name":"Ресторан Golden Star","description":"text текст"}'
    @PostMapping("/restaurant")
    public Restaurant restaurantCreate(@RequestBody Restaurant restaurant) {
        logger.info("Creating restaurant");
//        return valid.entityFromURI(restaurantService.create(restaurant));
        return restaurantService.create(restaurant);
    }

    //    curl -X POST -H 'Content-Type: application/json' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -i http://localhost:8080/rest/admin/restaurant/update/10005 --data '{"name":"Ресторан New Star","description":"text текст"}'
    @PutMapping("/restaurant/{id}")
    public void restaurantUpdate(@RequestBody Restaurant restaurant, @PathVariable int id) {
        logger.info("Updating restaurant " + id);
        restaurantService.update(restaurant, id);
    }

    //    curl -X DELETE -H 'Content-Type: application/json' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -i http://localhost:8080/rest/admin/restaurant/10008
    @DeleteMapping("/restaurant/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void restaurantDelete(@PathVariable int id) {
        logger.info("Deleting restaurant " + id);
        restaurantService.delete(id);
    }

    //    curl -X GET -H 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ=' -i http://localhost:8080/rest/admin/restaurant/10004
    @GetMapping("/restaurant/{id}")
    public Restaurant getRestaurant(@PathVariable int id) {
        Restaurant restaurant = restaurantService.get(id);
        logger.info("Returning restaurant: " + restaurant.toString());
        return restaurant;
    }

    //    curl -X POST -H 'Content-Type: application/json' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -i http://localhost:8080/rest/admin/dish/10008 --data '{"name":"Some Dish3 блюдо","price":350}'
    @PostMapping("/dish/{id}")
    public Dish dishCreated(@RequestBody Dish dish, @PathVariable int id) {

        logger.info("Creating dish");
//        return valid.entityFromURI(dishService.create(dish, id));
        return dishService.create(dish, id);
    }

    @GetMapping("/dish/{id}")
    public Dish dishUpdate(@PathVariable int id) {
        logger.info("Getting dish " + id);
        return dishService.get(id);
    }

    //    curl -X PUT -H 'Content-Type: application/json' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -i http://localhost:8080/rest/admin/dish/10022 --data '{"name":"New Dish блюдо","price":453}'
    @PutMapping("/dish/{id}")
    public void dishUpdated(@RequestBody Dish dish, @PathVariable int id) {
        logger.info("Updating dish " + id);
        dishService.update(dish, id);
    }

    //    curl -X DELETE -H 'Content-Type: application/json' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -i http://localhost:8080/rest/admin/dish/10022
    @DeleteMapping("/dish/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void dishDelete(@PathVariable int id) {
        logger.info("Deleting dish " + id);
        dishService.delete(id);
    }

    //    curl -X PUT -H 'Content-Type: application/json' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -i http://localhost:8080/rest/admin/user/off/10003
    @PutMapping("/user/off/{id}")
    public void switchOffUser(@PathVariable int id) {
        logger.info("Set user " + id + " status off");
        userService.setOnOff(id, false);
    }

    //    curl -X PUT -H 'Content-Type: application/json' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -i http://localhost:8080/rest/admin/user/on/10003
    @PutMapping("/user/on/{id}")
    public void switchOnUser(@PathVariable int id) {
        logger.info("Set user " + id + " status on");
        userService.setOnOff(id, true);
    }

    //    curl -X GET -i http://localhost:8080/rest/admin/users
    @GetMapping("/users")
    public List<User> getUsers() {
        logger.info("Get all users");
        return userService.getAll();
    }

//
    @GetMapping("/user/{id}")
    public User getUserWithRolesById(@PathVariable int id) {
        logger.info("Get user by id=" + id);
        return userService.getWithRoles(id);
    }
}
