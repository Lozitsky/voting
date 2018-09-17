package com.kirilo.restaurant.voting.service;

import com.kirilo.restaurant.voting.model.User;
import com.kirilo.restaurant.voting.model.Vote;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface VotingService {

    Vote get(int id);

    @Transactional
    void update(Vote vote, User user);
}
