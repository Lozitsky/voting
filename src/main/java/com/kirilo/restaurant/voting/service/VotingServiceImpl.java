package com.kirilo.restaurant.voting.service;

import com.kirilo.restaurant.voting.model.User;
import com.kirilo.restaurant.voting.model.Vote;
import com.kirilo.restaurant.voting.repository.UserRepository;
import com.kirilo.restaurant.voting.repository.VotingRepository;
import com.kirilo.restaurant.voting.util.ValidationDateTime;
import com.kirilo.restaurant.voting.util.ValidationUtil;
import com.kirilo.restaurant.voting.util.VotingUtil;
import com.kirilo.restaurant.voting.util.exception.NotFoundException;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
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
    private final VotingUtil valid;

    @Autowired
    public VotingServiceImpl(VotingRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        dateTime = new ValidationDateTime();
        util = new ValidationUtil();
        valid = new VotingUtil();
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
        valid.checkVoting(user, response);

        logger.info("User " + user.getName() + " can see result for today ");
        Date dateToday = dateTime.getDateToday();
        logger.info("Today is " + dateToday);
        return getWithRestaurantsByDate(dateToday);
    }

    @Override
    public boolean voteFor(int id) {
        User user = getUser();

        LocalDate lastDate = dateTime.getLastDate(user);
        LocalDate now = LocalDate.now();

        logger.info("Last voting: " + lastDate + "\nLocal Date Now: " + now +
                "Now is after last voting? " + now.isAfter(lastDate));

        if (valid.canVote(user, lastDate, now)) {
            if (now.equals(lastDate)) {
                logger.info("it is before 11:00 we assume that user changed his mind");
                Vote vote = get(user.getLastId());
                if (vote != null)
                    vote.setNumberOfVotes(vote.getNumberOfVotes() - 1);
            }

            logger.info("Voting for restaurant with id: " + id);
            Vote vote = get(id);
            if (vote == null) {
                throw new NotFoundException("Empty menu today");
            }
            vote.setNumberOfVotes(vote.getNumberOfVotes() + 1);

            user.setLastId(id);
//            user.setLastVoting(java.sql.Date.valueOf(now));
            user.setLastVoting(dateTime.convertToDate(now));

            update(vote, user);
            //"Thank you for voting"
            return true;
        }
        //  "You have already voted"
        return false;
    }
}
