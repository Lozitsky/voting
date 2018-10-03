package com.kirilo.restaurant.voting.util;

import com.kirilo.restaurant.voting.model.AbstractNamedEntity;
import com.kirilo.restaurant.voting.model.Restaurant;
import com.kirilo.restaurant.voting.model.User;
import com.kirilo.restaurant.voting.model.Vote;
import com.kirilo.restaurant.voting.repository.VotingRepository;
import com.kirilo.restaurant.voting.util.exception.NotFoundException;
import org.jboss.logging.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.kirilo.restaurant.voting.security.SecurityUtil.getUser;

public class VotingUtil {
    public final static Logger logger = Logger.getLogger(ValidationDateTime.class);
    private final ValidationDateTime dateTime;

    public VotingUtil() {
        dateTime = new ValidationDateTime();
    }

    public boolean canVote(User user, LocalDate lastDate, LocalDate now) {
        if (!user.isEnabled()) {
            return false;
        }
        return LocalTime.now().isBefore(LocalTime.of(11, 0)) || now.isAfter(lastDate);
    }

    public boolean canVote(User user) {
        if (user == null) {
            return false;
        }
        return LocalTime.now().isBefore(LocalTime.of(11, 0)) || !alreadyVoted(user);
    }

    public boolean alreadyVoted(User user) {
        if (user == null) {
            alreadyVoted(getUser());
        }
        LocalDate now = LocalDate.now();
        return now.equals(dateTime.getLastDate(user));
    }

    public ResponseEntity entityFromURI(AbstractNamedEntity entity) {
        URI uri = ServletUriComponentsBuilder
//                .fromCurrentContextPath()
//                .path(REST_URL + "/restaurant/{id}")
                .fromCurrentRequestUri()
                .path("{/id}")
                .buildAndExpand(entity.getId()).toUri();
        return ResponseEntity.created(uri).body(entity);
    }

    //https://stackoverflow.com/questions/29085295/spring-mvc-restcontroller-and-redirect
    public void checkVoting(User user, HttpServletResponse response){
        if (!alreadyVoted(user)) {
            logger.info("User " + user.getName() + " not voted yet");
            try {
                response.sendRedirect("/rest/user/dishes/forVoting");
            } catch (IOException e) {
                throw new NotFoundException("Bad response from " + e.getLocalizedMessage());
            }
        }
    }

    public void checkVotingEntity(VotingRepository repository, Restaurant restaurant) {
        if (repository.getByRestaurantIdAndDate(restaurant.getId(), LocalDateTime.of(LocalDate.now(), LocalTime.MIN), LocalDateTime.of(LocalDate.now(), LocalTime.MAX)).orElse(null) == null) {
            Vote vote = new Vote();
            vote.setRestaurant(restaurant);
            repository.save(vote);
        }
    }
}
