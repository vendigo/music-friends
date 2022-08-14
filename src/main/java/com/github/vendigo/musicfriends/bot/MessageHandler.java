package com.github.vendigo.musicfriends.bot;

import com.github.vendigo.musicfriends.command.CommandParser;
import com.github.vendigo.musicfriends.command.SetArtistCommand;
import com.github.vendigo.musicfriends.model.BotChatNode;
import com.github.vendigo.musicfriends.model.PathNode;
import com.github.vendigo.musicfriends.service.ArtistService;
import com.github.vendigo.musicfriends.service.BotChatService;
import com.github.vendigo.musicfriends.utils.Messages;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;
import java.util.Optional;

import static com.github.vendigo.musicfriends.utils.Utils.isSetArtistCommand;

@Component
@Slf4j
@AllArgsConstructor
public class MessageHandler {
    private final BotChatService chatService;
    private final ArtistService artistService;
    private final Messages messages;
    private final ResponseBuilder responseBuilder;

    public SendMessage handleMessage(Message message) {
        SendMessage answer = new SendMessage();
        Long chatId = message.getChatId();
        answer.setChatId(chatId.toString());
        String messageText = message.getText();

        if (messageText.equals("/start")) {
            return processStartCommand(answer);
        }

        if (isSetArtistCommand(messageText)) {
            return processSetArtistCommand(chatId, answer, messageText);
        }

        answer.setText(messages.getUnknownCommand());
        return answer;
    }

    private SendMessage processStartCommand(SendMessage answer) {
        answer.setText(messages.getGreeting());
        answer.setReplyMarkup(buildReplyMarkup(messages.getSetFirstArtist()));
        return answer;
    }

    private SendMessage processSetArtistCommand(Long chatId, SendMessage answer, String messageText) {
        BotChatNode chat = chatService.findChat(chatId);
        Optional<SetArtistCommand> commandOptional = CommandParser.parseSetArtistCommand(messageText);

        if (commandOptional.isEmpty()) {
            answer.setText(messages.getUnknownCommand());
            return answer;
        }

        SetArtistCommand command = commandOptional.get();
        if (chat.getArtistId() == null) {
            chatService.setArtist(chat, command.artistId());

            answer.setText(messages.getChosenArtist() + command.artistName());
            answer.setReplyMarkup(buildReplyMarkup(messages.getSetSecondArtist()));
            return answer;
        }

        List<PathNode> path = artistService.findPath(chat.getArtistId(), command.artistId());
        String response = responseBuilder.buildPathResponse(path);
        answer.setText(response);
        answer.setParseMode("html");
        answer.setReplyMarkup(buildReplyMarkup(messages.getSetFirstArtist()));
        answer.disableWebPagePreview();
        chatService.setArtist(chat, null);

        return answer;
    }

    private ReplyKeyboard buildReplyMarkup(String actionName) {
        return InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(
                        InlineKeyboardButton.builder()
                                .text(actionName)
                                .switchInlineQueryCurrentChat("")
                                .build()))
                .build();
    }


}
