package com.kirilo.restaurant.voting.service;

import com.kirilo.restaurant.voting.model.User;
import com.kirilo.restaurant.voting.model.Vote;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Transactional(readOnly = true)
public interface VotingService {

    Vote get(int id);

    @Transactional
    void update(Vote vote, User user);

    List<Vote> getWithRestaurantsByDate(Date date);
}
