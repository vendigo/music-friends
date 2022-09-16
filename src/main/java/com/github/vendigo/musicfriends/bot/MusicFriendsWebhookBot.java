package com.github.vendigo.musicfriends.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;

@Component
@Slf4j
@Profile("webhook")
public class MusicFriendsWebhookBot extends SpringWebhookBot {

    private final UpdateHandler updateHandler;

    @Value("${telegram.bot.username}")
    private String botUsername;
    @Value("${telegram.bot.token}")
    private String botToken;

    public MusicFriendsWebhookBot(SetWebhook setWebhook, UpdateHandler updateHandler) {
        super(setWebhook);
        this.updateHandler = updateHandler;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return updateHandler.handleUpdate(update);
    }

    @Override
    public String getBotPath() {
        return "music-friends";
    }
}
