package com.github.vendigo.musicfriends.bot;

import com.github.vendigo.musicfriends.command.CommandParser;
import com.github.vendigo.musicfriends.command.SetArtistCommand;
import com.github.vendigo.musicfriends.model.BotChatNode;
import com.github.vendigo.musicfriends.model.PathNode;
import com.github.vendigo.musicfriends.service.ArtistService;
import com.github.vendigo.musicfriends.service.BotChatService;
import com.github.vendigo.musicfriends.utils.Messages;
import com.github.vendigo.musicfriends.utils.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.vendigo.musicfriends.utils.Utils.isSetArtistCommand;

@Component
@Slf4j
@RequiredArgsConstructor
public class MessageHandler {
    private final BotChatService chatService;
    private final ArtistService artistService;
    private final Messages messages;
    private final ResponseBuilder responseBuilder;

    @Value("${usage.day.limit}")
    private int usageDayLimit;

    public SendMessage handleMessage(Message message) {
        SendMessage answer = new SendMessage();
        Long chatId = message.getChatId();
        answer.setChatId(chatId.toString());
        String messageText = message.getText();
        String username = buildUsername(message.getFrom());

        if (messageText.equals("/start")) {
            return processStartCommand(answer, username);
        }

        if (isSetArtistCommand(messageText)) {
            return processSetArtistCommand(chatId, username, answer, messageText);
        }

        answer.setText(messages.getUnknownCommand());
        return answer;
    }

    private SendMessage processStartCommand(SendMessage answer, String username) {
        String message = String.format(messages.getGreeting(), username);
        answer.setText(message);
        answer.setReplyMarkup(buildReplyMarkup(messages.getSetFirstArtist()));
        return answer;
    }

    private SendMessage processSetArtistCommand(Long chatId, String username, SendMessage answer, String messageText) {
        BotChatNode chat = chatService.findChat(chatId, username);
        SetArtistCommand command = CommandParser.parseSetArtistCommand(messageText);

        if (chat.getArtistId() == null) {
            return setArtist(answer, chat, command);
        }

        return searchPath(answer, chat, command);
    }

    private SendMessage searchPath(SendMessage answer, BotChatNode chat, SetArtistCommand command) {
        Long firstArtistId = chat.getArtistId();
        chatService.setArtist(chat, null);

        if (chat.getUsageCount() >= usageDayLimit) {
            log.info("Usage limit reached for user: {}", chat.getUsername());
            answer.setText(messages.getUsageLimitReached());
            return answer;
        }

        log.info("Searching path between: {} and {}", firstArtistId, command.artistId());
        answer.setReplyMarkup(buildReplyMarkup(messages.getTryAgain()));
        List<PathNode> path = artistService.findPath(firstArtistId, command.artistId());

        if (!path.isEmpty()) {
            String response = responseBuilder.buildPathResponse(path);
            answer.setText(response);
            answer.setParseMode("html");
            answer.disableWebPagePreview();
            return answer;
        }

        chatService.logPathSearch(chat);
        answer.setText(messages.getPathNotFound());
        return answer;
    }

    private SendMessage setArtist(SendMessage answer, BotChatNode chat, SetArtistCommand command) {
        chatService.setArtist(chat, command.artistId());
        answer.setText(messages.getChosenArtist() + command.artistName());
        answer.setReplyMarkup(buildReplyMarkup(messages.getSetSecondArtist()));
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

    private String buildUsername(User from) {
        return Optional.ofNullable(from.getUserName())
                .orElseGet(() ->
                        Stream.of(from.getFirstName(), from.getLastName())
                                .filter(str -> !str.isBlank())
                                .collect(Collectors.joining(" "))
                );
    }

}
