package com.kirilo.restaurant.voting.controller;

import com.kirilo.restaurant.voting.model.Restaurant;
import com.kirilo.restaurant.voting.model.User;
import com.kirilo.restaurant.voting.security.SecurityUtil;
import com.kirilo.restaurant.voting.service.RestaurantService;
import com.kirilo.restaurant.voting.util.ValidationDateTime;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class RootController {
    public final Logger logger = Logger.getLogger(RootController.class);
    private final RestaurantService restaurantService;
    private final ValidationDateTime dateTime;

    @Autowired
    public RootController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
        dateTime = new ValidationDateTime();
    }

    @GetMapping("/")
    public String goToVote() {
        logger.info("Returning vote.html file");
//        return "vote.html";
        return "redirect:/doLogin";
    }

    @RequestMapping("/doLogin")
//    public String doLogin(@RequestParam String name, Model model, HttpSession session) {
    public String doLogin(Model model, HttpSession session) {
        logger.info("getting user from database");

        User user = SecurityUtil.getUser();

        logger.info("putting user into session");

        session.setAttribute("user", user);

        if (dateTime.canVote(user)) {
            logger.info("putting restaurants into model");
            List<Restaurant> restaurants = restaurantService.getAll();
            model.addAttribute("restaurants", restaurants);

            return "/performVote.html";
        } else {
            return "/alreadyVoted.html";
        }
    }


}
