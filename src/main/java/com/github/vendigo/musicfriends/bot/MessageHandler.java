package com.github.vendigo.musicfriends.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;
import java.util.regex.Matcher;

import static com.github.vendigo.musicfriends.bot.InlineQueryHandler.DEEZER_ARTIST_LINK;
import static com.github.vendigo.musicfriends.bot.Utils.*;

@Component
@Slf4j
public class MessageHandler {

    private static final ReplyKeyboard REPLY_MARKUP = InlineKeyboardMarkup.builder()
            .keyboardRow(List.of(
                    InlineKeyboardButton.builder()
                            .text("Set artist from")
                            .switchInlineQueryCurrentChat(Utils.FROM_PREFIX)
                            .build(),
                    InlineKeyboardButton.builder()
                            .text("Set artist to")
                            .switchInlineQueryCurrentChat(Utils.TO_PREFIX)
                            .build()
            ))
            .build();

    public SendMessage handleMessage(Message message) {
        SendMessage answer = new SendMessage();
        answer.setChatId(message.getChatId().toString());
        String messageText = message.getText();

        if (messageText.equals("/start")) {
            answer.setText("Staring using bot");
            answer.setReplyMarkup(REPLY_MARKUP);
            return answer;
        }

        if (isSetArtistCommand(messageText) && messageText.contains(DEEZER_ARTIST_LINK)) {
            String[] lines = messageText.split("\n");
            String firstLine = lines[0];
            Matcher matcher = Utils.COMMAND_PATTERN.matcher(firstLine);
            if (matcher.find()) {
                String command = matcher.group(FIRST_GROUP);
                String artistName = matcher.group(SECOND_GROUP);
                answer.setText(command + ": " + artistName);
            } else {
                answer.setText("Wrong format");
            }

            answer.setReplyMarkup(REPLY_MARKUP);
            return answer;
        }

        answer.setText("Unknown command");
        return answer;
    }
}
