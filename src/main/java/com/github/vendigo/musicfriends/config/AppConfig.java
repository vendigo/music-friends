package com.github.vendigo.musicfriends.config;

import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Configuration
@RegisterReflectionForBinding({SendMessage.class, AnswerInlineQuery.class, InlineKeyboardMarkup.class,
        InlineKeyboardButton.class, InlineQueryResultArticle.class, InputTextMessageContent.class})
public class AppConfig {

    @Bean
    @Profile("!test")
    public ClockService clock() {
        return new ClockServiceImpl();
    }
}
