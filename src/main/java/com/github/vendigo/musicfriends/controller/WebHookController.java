package com.github.vendigo.musicfriends.controller;

import com.github.vendigo.musicfriends.handler.UpdateHandler;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
@AllArgsConstructor
public class WebHookController {

    private final UpdateHandler updateHandler;

    @PostMapping("/callback/music-friends")
    public BotApiMethod<?> handleUpdate(@RequestBody Update update) {
        return updateHandler.handleUpdate(update);
    }
}
