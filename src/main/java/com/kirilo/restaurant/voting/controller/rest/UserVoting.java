package com.kirilo.restaurant.voting.controller.rest;

import org.springframework.web.bind.annotation.*;

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

    @RequestMapping("/voteFor/{id}")
    public String voteFor(@RequestParam int id) {
        return "voteFor" + id;
    }
}
