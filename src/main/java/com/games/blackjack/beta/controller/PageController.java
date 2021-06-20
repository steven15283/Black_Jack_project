package com.games.blackjack.beta.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PageController {
    @GetMapping("{roomID}/user/{username}")
    public String getIndex(@PathVariable("roomID") String roomID, @PathVariable("username") String username) {
        return "index";
    }

    @GetMapping("/")
    public String getWelcome() {
        return "welcome";
    }
}
