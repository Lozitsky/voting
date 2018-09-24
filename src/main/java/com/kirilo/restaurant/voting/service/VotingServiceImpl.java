package com.kirilo.restaurant.voting.service;

import com.kirilo.restaurant.voting.model.User;
import com.kirilo.restaurant.voting.model.Vote;
import com.kirilo.restaurant.voting.repository.UserRepository;
import com.kirilo.restaurant.voting.repository.VotingRepository;
import com.kirilo.restaurant.voting.util.ValidationDateTime;
import com.kirilo.restaurant.voting.util.ValidationUtil;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

import static com.kirilo.restaurant.voting.security.SecurityUtil.getUser;

@Service
public class VotingServiceImpl implements VotingService {
    public final Logger logger = Logger.getLogger(VotingServiceImpl.class);

    private final VotingRepository repository;
    private final UserRepository userRepository;
    private final ValidationDateTime dateTime;
    private final ValidationUtil util;

    @Autowired
    public VotingServiceImpl(VotingRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        dateTime = new ValidationDateTime();
        util = new ValidationUtil();
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
//        userRepository.save(user);
        util.checkNotFoundWithName(userRepository.save(user), user.getName());
    }

    @Override
    public List<Vote> getWithRestaurantsByDate(Date date) {
        return repository.getWithRestaurantsByDate(date);
    }

    @Override
    public List<Vote> getWithRestaurantsToday(HttpServletResponse response) {
        logger.info("Get authorized User ");
        User user = getUser();
        dateTime.checkVoting(user, response);

        logger.info("User " + user.getName() + " can see result for today ");
        Date dateToday = dateTime.getDateToday();
        logger.info("Today is " + dateToday);
        return getWithRestaurantsByDate(dateToday);
    }
}
