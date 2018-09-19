package com.kirilo.restaurant.voting.service;

import com.kirilo.restaurant.voting.model.User;
import com.kirilo.restaurant.voting.model.Vote;
import com.kirilo.restaurant.voting.repository.UserRepository;
import com.kirilo.restaurant.voting.repository.VotingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class VotingServiceImpl implements VotingService {
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
        return repository.getByRestaurantIdAndDate(id, java.sql.Date.valueOf(LocalDate.now()));
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
}
