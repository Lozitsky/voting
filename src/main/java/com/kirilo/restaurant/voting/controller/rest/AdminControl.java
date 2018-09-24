package com.kirilo.restaurant.voting.controller.rest;

import com.kirilo.restaurant.voting.model.AbstractNamedEntity;
import com.kirilo.restaurant.voting.model.Dish;
import com.kirilo.restaurant.voting.model.Restaurant;
import com.kirilo.restaurant.voting.service.DishService;
import com.kirilo.restaurant.voting.service.RestaurantService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(AdminControl.REST_URL)
public class AdminControl {
    static final String REST_URL = "rest/admin";
    private final RestaurantService restaurantService;
    private final DishService dishService;
    public final Logger logger = Logger.getLogger(AdminControl.class);

    @Autowired
    public AdminControl(RestaurantService restaurantService, DishService dishService) {
        this.restaurantService = restaurantService;
        this.dishService = dishService;
    }

    //    curl -X POST -H 'Content-Type: application/json' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -i http://localhost:8080/rest/admin/restaurant --data '{"name":"Ресторан Golden Star","description":"text текст"}'
    @PostMapping("/restaurant")
    public ResponseEntity<Restaurant> restaurantCreate(@RequestBody Restaurant restaurant) {
        logger.info("Creating restaurant");
        return entityFromURI(restaurantService.create(restaurant));
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

    //    curl -X POST -H 'Content-Type: application/json' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOnBhc3N3b3Jk' -i http://localhost:8080/rest/admin/dish/10008 --data '{"name":"Some Dish3 блюдо","price":350}'
    @PostMapping("/dish/{id}")
    public ResponseEntity<Dish> dishCreated(@RequestBody Dish dish, @PathVariable int id) {
        logger.info("Creating dish");
        return entityFromURI(dishService.create(dish, id));
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
        logger.info("Deleting restaurant " + id);
        dishService.delete(id);
    }

    private ResponseEntity entityFromURI(AbstractNamedEntity entity) {
        URI uri = ServletUriComponentsBuilder
//                .fromCurrentContextPath()
//                .path(REST_URL + "/restaurant/{id}")
                .fromCurrentRequestUri()
                .path("{/id}")
                .buildAndExpand(entity.getId()).toUri();
        return ResponseEntity.created(uri).body(entity);
    }
}
