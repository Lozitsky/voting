package com.kirilo.restaurant.voting.util;

import com.kirilo.restaurant.voting.model.Restaurant;
import com.kirilo.restaurant.voting.model.User;
import com.kirilo.restaurant.voting.model.Vote;
import com.kirilo.restaurant.voting.repository.VotingRepository;
import com.kirilo.restaurant.voting.util.exception.NotFoundException;
import org.jboss.logging.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

import static com.kirilo.restaurant.voting.security.SecurityUtil.getUser;

//https://www.baeldung.com/java-date-to-localdate-and-localdatetime
public class ValidationDateTime {
    public final static Logger logger = Logger.getLogger(ValidationDateTime.class);

    public boolean canVote(User user, LocalDate lastDate, LocalDate now) {
        return LocalTime.now().isBefore(LocalTime.of(11, 0)) || now.isAfter(lastDate);
    }

    public boolean canVote(User user) {
        if (user == null) {
            return false;
        }
//        LocalDate lastDate = user.getLastVoting().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate now = LocalDate.now();
        return LocalTime.now().isBefore(LocalTime.of(11, 0)) || !alreadyVoted(user);
    }

    public boolean alreadyVoted(User user) {
        if (user == null) {
            alreadyVoted(getUser());
        }
        LocalDate now = LocalDate.now();
        return now.equals(getLastDate(user));
    }

    public static LocalDate getLastDate(User user) {
        return new java.sql.Date(user.getLastVoting().getTime()).toLocalDate();
    }

    public Date convertToDate(LocalDateTime date){
        return Date
                .from(date.atZone(ZoneId.systemDefault())
                        .toInstant());
    }

    public static Date convertToDate(LocalDate date){
        return java.sql.Date.valueOf(date);
    }

    public static Date convertToDate(String dateInString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy", Locale.ENGLISH);

//        String dateInString = "7-01-2018";
        return formatter.parse(dateInString);
    }

    public static LocalDateTime convertToLocalDateTime(LocalDate date, LocalTime time) {
        return LocalDateTime.of(date, time);
    }

    public Date getDateToday() {
        return convertToDate(LocalDate.now());
    }

    public static LocalDate getLocalDate(String stringDate) {
        return LocalDate.parse(stringDate);
    }

    public static LocalTime getLocalTime(String stringTime) {
        return LocalTime.parse(stringTime);
    }

    public LocalDateTime getLocalDateTime(String stringDate, String stringTime) {
        return convertToLocalDateTime(getLocalDate(stringDate), getLocalTime(stringTime));
    }

    public Date getDate(String stringDate, String stringTime) {
        return convertToDate(getLocalDateTime(stringDate, stringTime));
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
        if (repository.findByRestaurantIdAndDate(restaurant.getId(), getDateToday()).orElse(null) == null) {
            Vote vote = new Vote();
            vote.setRestaurant(restaurant);
            repository.save(vote);
        }
    }
}
