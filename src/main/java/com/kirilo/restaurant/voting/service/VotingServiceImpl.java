package com.kirilo.restaurant.voting.service;

import com.kirilo.restaurant.voting.controller.VotingController;
import com.kirilo.restaurant.voting.model.User;
import com.kirilo.restaurant.voting.model.Vote;
import com.kirilo.restaurant.voting.repository.UserRepository;
import com.kirilo.restaurant.voting.repository.VotingRepository;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.sql.Date;
import java.util.List;

import static com.kirilo.restaurant.voting.util.ValidationDateTime.getDateToday;

@Service
public class VotingServiceImpl implements VotingService {
    public final Logger logger = Logger.getLogger(VotingController.class);

    private final VotingRepository repository;
    private final UserRepository userRepository;

    @Autowired
    public VotingServiceImpl(VotingRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    //    https://www.baeldung.com/java-date-to-localdate-and-localdatetime
    @Override
    public Vote get(int id) {
        return repository.getByRestaurantIdAndDate(id, getDateToday());
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
    public List<Vote> getWithRestaurantsToday() {
        Date dateToday = getDateToday();
        logger.info("Today is " + dateToday);
        return getWithRestaurantsByDate(dateToday);
    }
}
