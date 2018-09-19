package com.kirilo.restaurant.voting.util;

import com.kirilo.restaurant.voting.model.User;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;

import static com.kirilo.restaurant.voting.security.SecurityUtil.getUser;

//https://www.baeldung.com/java-date-to-localdate-and-localdatetime
public class ValidationDateTime {

    public static boolean canVote(User user, LocalDate lastDate, LocalDate now) {
        return LocalTime.now().isBefore(LocalTime.of(11, 0)) || now.isAfter(lastDate);
    }

    public static boolean canVote(User user) {
        if (user == null) {
            return false;
        }
//        LocalDate lastDate = user.getLastVoting().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate now = LocalDate.now();
        return LocalTime.now().isBefore(LocalTime.of(11, 0)) || !alreadyVoted(user);
    }

    public static boolean alreadyVoted(User user) {
        if (user == null) {
            alreadyVoted(getUser());
//            return false;
        }
        LocalDate now = LocalDate.now();
        return now.equals(getLastDate(user));
    }

    public static LocalDate getLastDate(User user) {
        return new java.sql.Date(user.getLastVoting().getTime()).toLocalDate();
    }

    public static Date getDateToday() {
        return java.sql.Date.valueOf(LocalDate.now());
    }
}
