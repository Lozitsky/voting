package com.kirilo.restaurant.voting.controller;

import com.kirilo.restaurant.voting.model.Restaurant;
import com.kirilo.restaurant.voting.model.User;
import com.kirilo.restaurant.voting.repository.RestaurantRepo;
import com.kirilo.restaurant.voting.repository.UserRepo;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class VotingController {
    public final Logger logger = Logger.getLogger(VotingController.class);

    @Autowired
    RestaurantRepo restaurantRepo;
    @Autowired
    UserRepo userRepo;

    @RequestMapping("/")
    public String goToVote() {
        logger.info("Returning vote.html file");
        return "vote.html";
    }

    @RequestMapping("/doLogin")
    public String doLogin(@RequestParam String name, Model model, HttpSession session) {
        logger.info("getting user from database");

        User user = userRepo.findByName(name);

        logger.info("putting user into session");

        session.setAttribute("user", user);

        if (!user.isVoted()) {
            logger.info("putting restaurants into model");
            List<Restaurant> restaurants = restaurantRepo.findAll();
            model.addAttribute("restaurants", restaurants);

            return "/performVote.html";
        } else {
            return "/alreadyVoted.html";
        }
    }

    @RequestMapping("/voteFor")
    public String voteFor(@RequestParam int id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (!user.isVoted()) {
            user.setVoted(true);
            userRepo.save(user);
            Restaurant restaurant= restaurantRepo.findById(id);
            logger.info("voting for restaurant " + restaurant.getName());
            restaurant.setNumberOfVotes(restaurant.getNumberOfVotes() + 1);
            restaurantRepo.save(restaurant);
            return "voted.html";
        }

        return "/alreadyVoted.html";
    }

    @RequestMapping("/result")
    public String result(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user.isVoted()) {
            List<Restaurant> restaurants = restaurantRepo.findAll();
            model.addAttribute("restaurants", restaurants);

            return "/result.html";
        }
        return "/performVote.html";
    }
}
