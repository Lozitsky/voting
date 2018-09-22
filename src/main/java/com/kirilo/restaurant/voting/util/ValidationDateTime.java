package com.kirilo.restaurant.voting.util;

import com.kirilo.restaurant.voting.model.User;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Locale;

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
        }
        LocalDate now = LocalDate.now();
        return now.equals(getLastDate(user));
    }

    public static LocalDate getLastDate(User user) {
        return new java.sql.Date(user.getLastVoting().getTime()).toLocalDate();
    }

    public static java.util.Date convertToDate(LocalDateTime date){
        return java.util.Date
                .from(date.atZone(ZoneId.systemDefault())
                        .toInstant());
    }

    public static Date convertToDate(LocalDate date){
        return java.sql.Date.valueOf(date);
    }

    public static java.util.Date convertToDate(String dateInString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy", Locale.ENGLISH);

//        String dateInString = "7-01-2018";
        return formatter.parse(dateInString);
    }

    public static LocalDateTime convertToLocalDateTime(LocalDate date, LocalTime time) {
        return LocalDateTime.of(date, time);
    }

    public static Date getDateToday() {
        return convertToDate(LocalDate.now());
    }

    public static LocalDate getLocalDate(String stringDate) {
        return LocalDate.parse(stringDate);
    }

    public static LocalTime getLocalTime(String stringTime) {
        return LocalTime.parse(stringTime);
    }

    public static LocalDateTime getLocalDateTime(String stringDate, String stringTime) {
        return convertToLocalDateTime(getLocalDate(stringDate), getLocalTime(stringTime));
    }
}
