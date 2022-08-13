package com.github.vendigo.musicfriends.bot;

import com.github.vendigo.musicfriends.command.CommandParser;
import com.github.vendigo.musicfriends.command.SetArtistCommand;
import com.github.vendigo.musicfriends.service.BotChatService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

import java.util.List;
import java.util.Optional;

import static com.github.vendigo.musicfriends.bot.Utils.DEEZER_ARTIST_LINK;
import static com.github.vendigo.musicfriends.bot.Utils.isSetArtistCommand;

@Component
@Slf4j
@AllArgsConstructor
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
                            .build()))
            .keyboardRow(List.of(
                    InlineKeyboardButton.builder()
                            .text("Search path")
                            .callbackData("/search")
                            .build()
            ))
            .build();

    private final BotChatService chatService;

    public SendMessage handleMessage(Message message) {
        SendMessage answer = new SendMessage();
        Long chatId = message.getChatId();
        answer.setChatId(chatId.toString());
        String messageText = message.getText();

        if (messageText.equals("/start")) {
            return processStartCommand(answer);
        }

        if (isSetArtistCommand(messageText) && messageText.contains(DEEZER_ARTIST_LINK)) {
            return processSetArtistCommand(chatId, answer, messageText);
        }

        answer.setText("Unknown commandType");
        return answer;
    }

    private SendMessage processStartCommand(SendMessage answer) {
        answer.setText("Staring using bot");
        answer.setReplyMarkup(REPLY_MARKUP);
        return answer;
    }

    private SendMessage processSetArtistCommand(Long chatId, SendMessage answer, String messageText) {
        Optional<SetArtistCommand> commandOptional = CommandParser.parseSetArtistCommand(messageText);

        if (commandOptional.isEmpty()) {
            answer.setText("Wrong format");
            return answer;
        }

        SetArtistCommand command = commandOptional.get();
        chatService.setArtist(chatId, command);

        answer.setText(command.commandType().getValue() + ": " + command.artistName());
        answer.setReplyMarkup(REPLY_MARKUP);
        return answer;
    }
}
