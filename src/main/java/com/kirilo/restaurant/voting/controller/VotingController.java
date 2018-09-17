package com.kirilo.restaurant.voting.controller;

import com.kirilo.restaurant.voting.model.Restaurant;
import com.kirilo.restaurant.voting.model.User;
import com.kirilo.restaurant.voting.model.Vote;
import com.kirilo.restaurant.voting.service.RestaurantService;
import com.kirilo.restaurant.voting.service.VotingService;
import com.kirilo.restaurant.voting.util.exception.NotFoundException;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.kirilo.restaurant.voting.util.ValidationDateTime.canVote;

@Controller
public class VotingController {
    public final Logger logger = Logger.getLogger(VotingController.class);

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    VotingService votingService;

    @RequestMapping("/voteFor")
    public String voteFor(@RequestParam int id, HttpSession session) {
        User user = (User) session.getAttribute("user");

        LocalDate lastDate = user.getLastVoting().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate now = LocalDate.now();

        logger.info("Last voting: " + lastDate);
        logger.info("Local Date Now: " + now);
        logger.info("Now is after last voting? " + now.isAfter(lastDate));

        if (canVote(user, lastDate, now)) {

            if (now.equals(lastDate)) {
                logger.info("it is before 11:00 we assume that user changed his mind");
                Vote vote = votingService.get(user.getLastId());
                if (vote != null)
                    vote.setNumberOfVotes(vote.getNumberOfVotes() - 1);
            }

            logger.info("Voting for restaurant with id: " + id);
            Vote vote = votingService.get(id);
            if (vote == null) {
                throw new NotFoundException("Empty menu today");
            }
            vote.setNumberOfVotes(vote.getNumberOfVotes() + 1);

            user.setLastId(id);
            user.setLastVoting(java.sql.Date.valueOf(now));

            votingService.update(vote, user);

            return "voted.html";
        }

        return "/alreadyVoted.html";
    }

    @RequestMapping("/result")
    public String result(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (canVote(user)) {
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
