package com.github.vendigo.musicfriends.bot;

import com.github.vendigo.musicfriends.handler.UpdateHandler;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("pooling")
public class MusicFriendsPollingBot extends TelegramLongPollingBot {

    private final UpdateHandler updateHandler;

    @Value("${telegram.bot.username}")
    private String botUsername;
    @Value("${telegram.bot.token}")
    private String botToken;

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        BotApiMethod<?> answer = updateHandler.handleUpdate(update);
        execute(answer);
    }
}
