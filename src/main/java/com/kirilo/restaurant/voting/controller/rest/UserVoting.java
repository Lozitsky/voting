package com.kirilo.restaurant.voting.controller.rest;

import com.kirilo.restaurant.voting.controller.VotingController;
import com.kirilo.restaurant.voting.model.User;
import com.kirilo.restaurant.voting.model.Vote;
import com.kirilo.restaurant.voting.service.VotingService;
import com.kirilo.restaurant.voting.util.ValidationDateTime;
import com.kirilo.restaurant.voting.util.exception.NotFoundException;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;

import static com.kirilo.restaurant.voting.security.SecurityUtil.getUser;
import static com.kirilo.restaurant.voting.util.ValidationDateTime.getLastDate;

@RestController
@RequestMapping(UserVoting.REST)
public class UserVoting {
    public final Logger logger = Logger.getLogger(VotingController.class);
    static final String REST = "/rest/user";
    private final ValidationDateTime dateTime;
    private final VotingService votingService;

    //    https://dzone.com/articles/why-static-bad-and-how-avoid
    @Autowired
    public UserVoting(VotingService votingService) {
        this.votingService = votingService;
        dateTime = new ValidationDateTime();
    }

    //    curl -X GET -H 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ=' -i http://localhost:8080/rest/user/voteFor/10007
    @RequestMapping("/voteFor/{id}")
    public String voteFor(@PathVariable int id) {

        User user = getUser();

        LocalDate lastDate = getLastDate(user);
        LocalDate now = LocalDate.now();

        logger.info("Last voting: " + lastDate + "\nLocal Date Now: " + now +
                "Now is after last voting? " + now.isAfter(lastDate));

        if (dateTime.canVote(user, lastDate, now)) {
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

    //    curl -X GET -H 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ=' -i http://localhost:8080/rest/user/votes
    @GetMapping("/votes")
    public List<Vote> votesToday(HttpServletResponse response) {
        logger.info("Get result for voted today");

        List<Vote> votes = votingService.getWithRestaurantsToday(response);
        return votes;
    }
}
