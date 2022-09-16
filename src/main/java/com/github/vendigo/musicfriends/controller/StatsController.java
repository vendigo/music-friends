package com.github.vendigo.musicfriends.controller;

import com.github.vendigo.musicfriends.service.BotChatService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class StatsController {

    private final BotChatService botChatService;

    @GetMapping("/")
    public String stats(Model model) {
        model.addAttribute("totalUsers", botChatService.getTotalUsers());
        model.addAttribute("totalSearches", botChatService.getTotalUsages());
        return "stats";
    }
}
