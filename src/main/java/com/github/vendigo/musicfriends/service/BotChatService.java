package com.github.vendigo.musicfriends.service;

import com.github.vendigo.musicfriends.model.BotChatNode;
import com.github.vendigo.musicfriends.repository.BotChatRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class BotChatService {

    private final BotChatRepository chatRepository;

    public void setArtist(BotChatNode chat, Long artistId) {
        chat.setArtistId(artistId);
        chatRepository.save(chat);
    }

    public BotChatNode findChat(long chatId) {
        return chatRepository.findById(chatId)
                .orElseGet(() -> new BotChatNode(chatId));
    }
}
