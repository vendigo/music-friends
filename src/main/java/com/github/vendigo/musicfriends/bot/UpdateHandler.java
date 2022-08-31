package com.github.vendigo.musicfriends.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
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

        throw new IllegalArgumentException("Other updates are not supported");
    }

}
