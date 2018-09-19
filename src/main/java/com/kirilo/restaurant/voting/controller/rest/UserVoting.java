package com.kirilo.restaurant.voting.controller.rest;

import com.kirilo.restaurant.voting.controller.VotingController;
import com.kirilo.restaurant.voting.model.User;
import com.kirilo.restaurant.voting.model.Vote;
import com.kirilo.restaurant.voting.service.RestaurantService;
import com.kirilo.restaurant.voting.service.VotingService;
import com.kirilo.restaurant.voting.util.ValidationDateTime;
import com.kirilo.restaurant.voting.util.exception.NotFoundException;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

import static com.kirilo.restaurant.voting.security.SecurityUtil.getUser;
import static com.kirilo.restaurant.voting.util.ValidationDateTime.canVote;

@RestController
@RequestMapping(UserVoting.REST)
public class UserVoting {
    public final Logger logger = Logger.getLogger(VotingController.class);
    static final String REST = "/rest/user";

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    VotingService votingService;

    @RequestMapping("/voteFor/{id}")
    public String voteFor(@PathVariable int id) {

        User user = getUser();

        LocalDate lastDate = ValidationDateTime.getLastDate(user);
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

            return "Thank you for voting";
        }
        return "You have already voted";
    }
}
