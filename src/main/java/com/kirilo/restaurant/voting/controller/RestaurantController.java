package com.kirilo.restaurant.voting.controller;

import com.kirilo.restaurant.voting.model.Dish;
import com.kirilo.restaurant.voting.model.Restaurant;
import com.kirilo.restaurant.voting.service.RestaurantService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.kirilo.restaurant.voting.util.ValidationDateTime.getDateToday;
import static com.kirilo.restaurant.voting.util.ValidationUtil.assureIdConsistent;

@Controller
public class RestaurantController {
    public final Logger logger = Logger.getLogger(VotingController.class);

    @Autowired
    RestaurantService restaurantService;

        @RequestMapping("/menuFrom")
        public String menuFrom(@RequestParam int id, Model model) {
            Restaurant restaurant = restaurantService.getWithDishes(id, getDateToday());
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
            model.addAttribute("restaurant", new Restaurant(null, "New restaurant"));
            return "restaurantForm.html";
        }

        @RequestMapping("/restaurants/save")
        public String restaurantSave(@RequestParam Integer id, @RequestParam String name) {
            Restaurant restaurant = new Restaurant(name);

            if (id == null) {
                restaurantService.create(restaurant);
            } else {
                assureIdConsistent(restaurant, id);
                restaurantService.update(restaurant);
            }
            //do something
            return "redirect:/restaurants";
        }
}
