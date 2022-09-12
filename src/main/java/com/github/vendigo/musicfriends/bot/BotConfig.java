package com.github.vendigo.musicfriends.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultWebhook;

@Configuration
public class BotConfig {

    @Value("${telegram.bot.path}")
    private String botPath;

    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder()
                .url(botPath)
                .build();
    }

    @Bean
    public TelegramBotsApi botsApi() throws TelegramApiException {
        DefaultWebhook defaultWebhook = new DefaultWebhook();
        defaultWebhook.setInternalUrl("http://localhost:80");
        return new TelegramBotsApi(DefaultBotSession.class, defaultWebhook);
    }
}
