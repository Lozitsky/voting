package com.kirilo.restaurant.voting.controller;

import com.kirilo.restaurant.voting.model.Dish;
import com.kirilo.restaurant.voting.model.Restaurant;
import com.kirilo.restaurant.voting.model.User;
import com.kirilo.restaurant.voting.service.RestaurantService;
import com.kirilo.restaurant.voting.service.UserService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class VotingController {
    public final Logger logger = Logger.getLogger(VotingController.class);

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    UserService userService;



    @RequestMapping("/voteFor")
    public String voteFor(@RequestParam int id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (!user.isVoted()) {
            user.setVoted(true);
            userService.update(user);
            Restaurant restaurant = restaurantService.get(id);
            logger.info("voting for restaurant " + restaurant.getName());
            restaurant.setNumberOfVotes(restaurant.getNumberOfVotes() + 1);
            restaurantService.update(restaurant);
            return "voted.html";
        }

        return "/alreadyVoted.html";
    }

    @RequestMapping("/result")
    public String result(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user.isVoted()) {
            List<Restaurant> restaurants = restaurantService.getAll();
            model.addAttribute("restaurants", restaurants);

            return "/result.html";
        }
        return "/performVote.html";
    }

/*    @RequestMapping("/dishForm")
    public String addDish() {
        return "dish";
    }*/
}
