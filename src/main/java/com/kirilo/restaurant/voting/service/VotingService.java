package com.kirilo.restaurant.voting.service;

import com.kirilo.restaurant.voting.model.Vote;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface VotingService {

    Vote get(int id);

    List<Vote> getWithRestaurantsByDate(LocalDateTime date);

    List<Vote> getWithRestaurantsToday(HttpServletResponse response);

    @Transactional
    Vote voteFor(int restaurantId, int userId);
}
