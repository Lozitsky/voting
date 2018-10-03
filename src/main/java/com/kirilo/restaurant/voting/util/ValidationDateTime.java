package com.kirilo.restaurant.voting.util;

import com.kirilo.restaurant.voting.model.User;
import com.kirilo.restaurant.voting.util.exception.NotFoundException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;

//https://www.baeldung.com/java-date-to-localdate-and-localdatetime
public class ValidationDateTime {

    public LocalDate getLastDate(User user) {
//        return user.getLastVoting();
        return new java.sql.Date(user.getLastVoting().getTime()).toLocalDate();
    }

    public Date convertToDate(LocalDateTime date) {
        return Date
                .from(date.atZone(ZoneId.systemDefault())
                        .toInstant());
    }

    public LocalDateTime convertToLocalDateTime(Date date) {
//        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return new java.sql.Timestamp(
                date.getTime()).toLocalDateTime();
    }

    public Date convertToDate(LocalDate date) {
        return java.sql.Date.valueOf(date);
    }

    public Date convertToDate(String dateInString){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//        String dateInString = "2018-07-21";
        try {
            return formatter.parse(dateInString);
        } catch (ParseException e) {
            throw new NotFoundException("Invalid date format");
        }
    }

    public LocalDateTime convertToLocalDateTime(LocalDate date, LocalTime time) {
        return LocalDateTime.of(date, time);
    }

/*    public Date getDateToday() {
        return convertToDate(LocalDate.now());
    }*/

    public LocalDate getDateToday() {
        return LocalDate.now();
//        return LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0,0));
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
