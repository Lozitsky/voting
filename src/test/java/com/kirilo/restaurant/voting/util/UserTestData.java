package com.kirilo.restaurant.voting.util;

import com.kirilo.restaurant.voting.model.Role;
import com.kirilo.restaurant.voting.model.User;

import java.time.Month;

import static java.time.LocalDateTime.of;

import static com.kirilo.restaurant.voting.model.AbstractEntity.START_SEQ;

public class UserTestData {
    private ValidationDateTime dateTime;

    public final int USER_ID = START_SEQ;
    public final int ADMIN_ID = START_SEQ + 1;
    public final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "password", of(2018, Month.SEPTEMBER,14, 02,02,02), Role.ROLE_ADMIN);

    public UserTestData() {
        dateTime = new ValidationDateTime();
    }
}
