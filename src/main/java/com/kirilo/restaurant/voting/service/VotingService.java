package com.kirilo.restaurant.voting.service;

import com.kirilo.restaurant.voting.model.User;
import com.kirilo.restaurant.voting.model.Vote;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface VotingService {

    Vote get(int id);

//    @Transactional
    void update(Vote vote, User user);

    List<Vote> getWithRestaurantsByDate(LocalDateTime date);

    List<Vote> getWithRestaurantsToday(HttpServletResponse response);

    Vote voteFor(int id, User user);
//
//    Vote getVoteByRestaurantId(int restaurantId);
}
