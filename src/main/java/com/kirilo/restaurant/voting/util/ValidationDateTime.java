package com.kirilo.restaurant.voting.util;

import com.kirilo.restaurant.voting.model.User;
import java.time.LocalDate;
import java.time.LocalTime;

public class ValidationDateTime {

    public static boolean canVote(User user, LocalDate lastDate, LocalDate now) {
        return LocalTime.now().isBefore(LocalTime.of(11, 0)) || now.isAfter(lastDate);
    }

    public static boolean canVote(User user) {
//        LocalDate lastDate = user.getLastVoting().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate now = LocalDate.now();
        return LocalTime.now().isBefore(LocalTime.of(11, 0)) || now.isAfter(getLastDate(user));
    }

    public static LocalDate getLastDate(User user) {
        return new java.sql.Date(user.getLastVoting().getTime()).toLocalDate();
    }
}
