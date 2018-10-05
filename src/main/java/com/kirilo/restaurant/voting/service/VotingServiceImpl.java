package com.kirilo.restaurant.voting.service;

import com.kirilo.restaurant.voting.model.User;
import com.kirilo.restaurant.voting.model.Vote;
import com.kirilo.restaurant.voting.repository.DishRepository;
import com.kirilo.restaurant.voting.repository.UserRepository;
import com.kirilo.restaurant.voting.repository.VotingRepository;
import com.kirilo.restaurant.voting.util.ValidationDateTime;
import com.kirilo.restaurant.voting.util.ValidationUtil;
import com.kirilo.restaurant.voting.util.VotingUtil;
import com.kirilo.restaurant.voting.util.exception.NotAcceptableException;
import com.kirilo.restaurant.voting.util.exception.NotFoundException;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class VotingServiceImpl implements VotingService {
    public final Logger logger = Logger.getLogger(VotingServiceImpl.class);

    private final VotingRepository repository;

    private final ValidationUtil util;

    private final UserRepository userRepository;

    private final DishRepository dishRepository;

    private final ValidationDateTime dateTime;
    private final VotingUtil valid;

    @Autowired
    public VotingServiceImpl(VotingRepository repository, UserRepository userRepository, DishRepository dishRepository) {
        this.repository = repository;
        this.util = new ValidationUtil();
        this.userRepository = userRepository;
        this.dishRepository = dishRepository;
        dateTime = new ValidationDateTime();
        valid = new VotingUtil();
    }

    //    https://www.baeldung.com/java-date-to-localdate-and-localdatetime
    @Override
    public Vote getByRestaurantId(int id) {
        return repository.getByRestaurantIdAndDate(id, LocalDateTime.of(LocalDate.now(), LocalTime.MIN), LocalDateTime.of(LocalDate.now(), LocalTime.MAX))
                .orElseThrow(() -> new NotFoundException("Can't find the restaurant"));
    }

    @Override
    public Vote get(int id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Can't find the dish"));
    }

    @Override
    public List<Vote> getWithRestaurantsByDate(LocalDateTime date) {
        return util.checkNotFound(repository.getWithRestaurantsByDate(date), "list restaurants, must not be null");
    }

    @Override
    public List<Vote> getWithRestaurantsToday(HttpServletResponse response, int userId) {
        logger.info("Get authorized User ");
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("can't find user in database"));
        valid.checkVoting(user, response);

        logger.info("User " + user.getName() + " can see result for today ");

        LocalDate now = LocalDate.now();
        logger.info("Today is " + now);
        return getWithRestaurantsByDate(LocalDateTime.of(now, LocalTime.MIN));
    }

    @Override
    public Vote voteFor(int restaurantId, int userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("can't find user in database"));

        LocalDate lastDate = dateTime.getLastDate(user);
        LocalDate now = LocalDate.now();

        logger.info("Last voting: " + lastDate + "\nLocal Date Now: " + now +
                "Now is after last voting? " + now.isAfter(lastDate));

        if (valid.canVote(user, lastDate, now)) {
            if (now.equals(lastDate)) {
                logger.info("it is before 11:00 we assume that user changed his mind");
                Vote vote = getByRestaurantId(user.getLastId());
                vote.setNumberOfVotes(vote.getNumberOfVotes() - 1);
            }

            logger.info("Voting for restaurant with id: " + restaurantId);
            Vote vote = getByRestaurantId(restaurantId);
            vote.setNumberOfVotes(vote.getNumberOfVotes() + 1);

            user.setLastId(restaurantId);
            user.setLastVoting(java.sql.Date.valueOf(now));
            //"Thank you for voting"
            //**********************
            util.checkNotFound(dishRepository.getFirstByRestaurantIdAndDate(restaurantId, LocalDateTime.now()), "dish, must not be null");
//            Assert.notNull(dishRepository.getFirstByRestaurantIdAndDate(restaurantId, LocalDateTime.now()), "dish must not be null");
            repository.save(vote);
            userRepository.save(user);
            //**********************

            return vote;
        } else {
            throw new NotAcceptableException("can't vote for the restaurant");
        }
        //  "You have already voted"
    }

/*    @Override
    public Vote getVoteByRestaurantId(int id) {
        return repository.findByRestaurantIdAndDate(id, LocalDate.now())
                .orElseThrow(() -> new NotFoundException("Can't fined entity vote today"));
    }*/
}
