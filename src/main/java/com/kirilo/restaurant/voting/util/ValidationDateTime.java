package com.kirilo.restaurant.voting.util;

import com.kirilo.restaurant.voting.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

//https://www.baeldung.com/java-date-to-localdate-and-localdatetime
public class ValidationDateTime {

    public LocalDate getLastDate(User user) {
        return new java.sql.Date(user.getLastVoting().getTime()).toLocalDate();
    }

    public Date convertToDate(LocalDateTime date) {
        return Date
                .from(date.atZone(ZoneId.systemDefault())
                        .toInstant());
    }

    public Date convertToDate(LocalDate date) {
        return java.sql.Date.valueOf(date);
    }

    public Date convertToDate(String dateInString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy", Locale.ENGLISH);

//        String dateInString = "7-01-2018";
        return formatter.parse(dateInString);
    }

    public LocalDateTime convertToLocalDateTime(LocalDate date, LocalTime time) {
        return LocalDateTime.of(date, time);
    }

    public Date getDateToday() {
        return convertToDate(LocalDate.now());
    }

    public LocalDate getLocalDate(String stringDate) {
        return LocalDate.parse(stringDate);
    }

    public LocalTime getLocalTime(String stringTime) {
        return LocalTime.parse(stringTime);
    }

    public LocalDateTime getLocalDateTime(String stringDate, String stringTime) {
        return convertToLocalDateTime(getLocalDate(stringDate), getLocalTime(stringTime));
    }

    public Date getDate(String stringDate, String stringTime) {
        return convertToDate(getLocalDateTime(stringDate, stringTime));
    }
}
