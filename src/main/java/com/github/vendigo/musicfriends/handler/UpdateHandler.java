package com.github.vendigo.musicfriends.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
@Slf4j
public class UpdateHandler {

    private final MessageHandler messageHandler;
    private final ArtistSearchHandler artistSearchHandler;

    public BotApiMethod<?> handleUpdate(Update update) {
        if (update.hasMessage()) {
            return messageHandler.handleMessage(update.getMessage());
        }

        if (update.hasInlineQuery()) {
            return artistSearchHandler.handleQuery(update.getInlineQuery());
        }

        log.info("Unsupported update: {}", update);
        return null;
    }

}
