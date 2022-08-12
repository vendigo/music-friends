package com.github.vendigo.musicfriends.bot;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;

@Component
@RequiredArgsConstructor
@Slf4j
public class HelloBot extends TelegramLongPollingBot {

    private final MessageHandler messageHandler;
    private final InlineQueryHandler inlineQueryHandler;

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
        if (update.hasMessage()) {
            SendMessage answer = messageHandler.handleMessage(update.getMessage());
            execute(answer);
            return;
        }

        if (update.hasInlineQuery()) {
            AnswerInlineQuery answer = inlineQueryHandler.handleQuery(update.getInlineQuery());
            execute(answer);
        }

    }
}
