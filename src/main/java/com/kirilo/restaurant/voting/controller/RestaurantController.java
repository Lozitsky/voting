package com.kirilo.restaurant.voting.controller;

import com.kirilo.restaurant.voting.model.Dish;
import com.kirilo.restaurant.voting.model.Restaurant;
import com.kirilo.restaurant.voting.service.RestaurantService;
import com.kirilo.restaurant.voting.util.ValidationDateTime;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class RestaurantController {
    public final Logger logger = Logger.getLogger(VotingController.class);
    private final RestaurantService restaurantService;
    private final ValidationDateTime dateTime;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
        dateTime = new ValidationDateTime();
    }


    @RequestMapping("/menuFrom")
        public String menuFrom(@RequestParam int id, Model model) {
            Restaurant restaurant = restaurantService.getWithDishes(id, dateTime.getDateToday());
            List<Dish> dishes = restaurant.getDishes();
            model.addAttribute("dishes", dishes);
            return "/menu";
        }

        @GetMapping("/restaurants")
        public String goToRestaurants(Model model) {
            logger.info("Returning allRestaurants.html file");
            List<Restaurant> restaurants = restaurantService.getAll();
            model.addAttribute("restaurants", restaurants);
            return "/allRestaurants.html";
        }

        @RequestMapping("/restaurants/restaurantForm")
        public String addRestaurant(Model model) {
            //fake data
            model.addAttribute("restaurant", new Restaurant("New restaurant", "Something about New restaurant"));
            return "restaurantForm.html";
        }

        @RequestMapping("/restaurants/save")
        public String restaurantSave(@RequestParam Integer id, @RequestParam String name) {
            Restaurant restaurant = new Restaurant(name);

            if (id == null) {
                restaurantService.create(restaurant);
            } else {
                restaurantService.update(restaurant, id);
            }
            //do something
            return "redirect:/restaurants";
        }
}
