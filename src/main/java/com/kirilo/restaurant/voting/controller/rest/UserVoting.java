package com.kirilo.restaurant.voting.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;

@RestController
@RequestMapping(UserVoting.REST)
public class UserVoting {
    static final String REST = "/rest/user";

    @GetMapping(value = "/{id}")
    public String getSomething(@PathVariable int id) throws IOException, ParseException {
        return "Something information of json";
    }
}
