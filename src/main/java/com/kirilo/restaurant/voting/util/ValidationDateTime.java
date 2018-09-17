package com.kirilo.restaurant.voting.util;

import com.kirilo.restaurant.voting.model.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

public class ValidationDateTime {

    public static boolean canVote(User user, LocalDate lastDate, LocalDate now) {
        return LocalTime.now().isBefore(LocalTime.of(11, 0)) || now.isAfter(lastDate);
    }

    public static boolean canVote(User user) {
        LocalDate lastDate = user.getLastVoting().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate now = LocalDate.now();
        return LocalTime.now().isBefore(LocalTime.of(11, 0)) || now.isAfter(lastDate);
    }
}
