package com.kirilo.restaurant.voting.service;

import com.kirilo.restaurant.voting.model.Vote;

public interface VotingService {

    Vote get(int id);

    void update(Vote vote);
}
