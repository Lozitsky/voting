package com.kirilo.restaurant.voting.service;

import com.kirilo.restaurant.voting.model.Vote;
import com.kirilo.restaurant.voting.repository.VotingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;

import static com.kirilo.restaurant.voting.util.ValidationUtil.checkNotFoundWithName;

@Service
public class VotingServiceImpl implements VotingService{
    private final VotingRepository repository;

    @Autowired
    public VotingServiceImpl(VotingRepository repository) {
        this.repository = repository;
    }

    @Override
    public Vote get(int id) {
        return repository.getByRestaurantIdAndDate(id, java.sql.Date.valueOf(LocalDate.now()));
    }

    @Override
    public void update(Vote vote) {
        Assert.notNull(vote, "restaurant must not be null");
        repository.save(vote);
//        checkNotFoundWithName(repository.save(vote), vote.getName());
    }
}
