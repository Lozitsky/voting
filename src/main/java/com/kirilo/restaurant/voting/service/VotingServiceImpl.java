package com.kirilo.restaurant.voting.service;

import com.kirilo.restaurant.voting.controller.VotingController;
import com.kirilo.restaurant.voting.model.User;
import com.kirilo.restaurant.voting.model.Vote;
import com.kirilo.restaurant.voting.repository.UserRepository;
import com.kirilo.restaurant.voting.repository.VotingRepository;
import com.kirilo.restaurant.voting.util.ValidationDateTime;
import com.kirilo.restaurant.voting.util.exception.NotFoundException;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static com.kirilo.restaurant.voting.security.SecurityUtil.getUser;

@Service
public class VotingServiceImpl implements VotingService {
    public final Logger logger = Logger.getLogger(VotingController.class);

    private final VotingRepository repository;
    private final UserRepository userRepository;
    private final ValidationDateTime dateTime;

    @Autowired
    public VotingServiceImpl(VotingRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        dateTime = new ValidationDateTime();
    }

    //    https://www.baeldung.com/java-date-to-localdate-and-localdatetime
    @Override
    public Vote get(int id) {
        return repository.findByRestaurantIdAndDate(id, dateTime.getDateToday()).orElse(null);
    }

    @Override
    public void update(Vote vote, User user) {
        Assert.notNull(vote, "restaurant must not be null");
        repository.save(vote);
        userRepository.save(user);
//        checkNotFoundWithName(repository.save(vote), vote.getName());
    }

    @Override
    public List<Vote> getWithRestaurantsByDate(Date date) {
        return repository.getWithRestaurantsByDate(date);
    }

    @Override
    public List<Vote> getWithRestaurantsToday(HttpServletResponse response) {
        logger.info("Get authorized User ");
        User user = getUser();

        try {
            dateTime.checkVoting(user, response);
        } catch (IOException e) {
            throw new NotFoundException("Bad response from getWithRestaurantsToday()");
        }

        logger.info("User " + user.getName() + " can see result for today ");
        Date dateToday = dateTime.getDateToday();
        logger.info("Today is " + dateToday);
        return getWithRestaurantsByDate(dateToday);
    }
}
