package com.github.vendigo.musicfriends.bot;

import com.github.vendigo.musicfriends.model.BotChatNode;
import com.github.vendigo.musicfriends.model.PathNode;
import com.github.vendigo.musicfriends.service.ArtistService;
import com.github.vendigo.musicfriends.service.BotChatService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CallbackQueryHandler {

    private final BotChatService botChatService;
    private final ArtistService artistService;

    public SendMessage handleCallbackQuery(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChat().getId();

        BotChatNode chat = botChatService.findChat(chatId)
                .orElse(null);
        SendMessage answer = new SendMessage();
        answer.setChatId(chatId);

        if (chat == null || chat.getArtistToId() == null || chat.getArtistFromId() == null) {
            answer.setText("Please set artistFrom and artistTo");
            return answer;
        }

        String message = artistService.findPath(chat.getArtistFromId(), chat.getArtistToId()).stream()
                .map(CallbackQueryHandler::pathNodeToString)
                .collect(Collectors.joining("\n"));

        return new SendMessage(chatId.toString(), message);
    }

    private static String pathNodeToString(PathNode node) {
        return node.type().getLabel() + ") " + node.name();
    }
}
