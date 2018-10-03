package com.kirilo.restaurant.voting.controller.rest;

import com.kirilo.restaurant.voting.model.Dish;
import com.kirilo.restaurant.voting.model.User;
import com.kirilo.restaurant.voting.model.Vote;
import com.kirilo.restaurant.voting.service.DishService;
import com.kirilo.restaurant.voting.service.VotingService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.kirilo.restaurant.voting.security.SecurityUtil.getUser;

@RestController
@RequestMapping(UserVoting.REST)
public class UserVoting {
    private final Logger logger = Logger.getLogger(UserVoting.class);
    static final String REST = "/rest/user";
//    private final ValidationDateTime dateTime;
    private final VotingService votingService;
    private final DishService dishService;

    //    https://dzone.com/articles/why-static-bad-and-how-avoid
    @Autowired
    public UserVoting(VotingService votingService, DishService dishService) {
        this.votingService = votingService;
//        dateTime = new ValidationDateTime();
        this.dishService = dishService;
    }

    //    curl -X GET -H 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ=' -i http://localhost:8080/rest/user/voteFor/10007
    @RequestMapping("/voteFor/{id}")
    public Vote voteFor(@PathVariable int id) {
        User user = getUser();
        Vote vote = votingService.voteFor(id, user);
        votingService.update(vote, user);

        return vote;
    }

    //    curl -X GET -H 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ=' -i http://localhost:8080/rest/user/votes
    @GetMapping("/votes")
    public List<Vote> votesToday(HttpServletResponse response) {
        logger.info("Get result for voted today");

        return votingService.getWithRestaurantsToday(response);

    }

    //    curl -X GET -H 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ=' -i http://localhost:8080/rest/user/dishes/forVoting
    @GetMapping("/dishes/forVoting")
    public List<Dish> dishesWithRestaurants() {
        List<Dish> dishes = dishService.getForVoting();
        logger.info("Returning all dishes with restaurants for voting: " + dishes.toString());
        return dishes;
    }
}
