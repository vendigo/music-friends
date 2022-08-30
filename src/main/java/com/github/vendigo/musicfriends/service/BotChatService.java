package com.github.vendigo.musicfriends.service;

import com.github.vendigo.musicfriends.model.BotChatNode;
import com.github.vendigo.musicfriends.repository.BotChatRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
@Slf4j
public class BotChatService {

    private final BotChatRepository chatRepository;

    public void setArtist(BotChatNode chat, long artistId) {
        chat.setArtistId(artistId);
        chatRepository.save(chat);
    }

    public BotChatNode findChat(long chatId, String username) {
        return chatRepository.findById(chatId)
                .orElseGet(() -> new BotChatNode(chatId, username));
    }

    public void logPathSearch(BotChatNode chat) {
        chat.setArtistId(null);
        LocalDate today = LocalDate.now();
        if (!today.equals(chat.getLastUsageDate())) {
            chat.setLastUsageDate(today);
            chat.setUsageCount(0L);
        }

        chat.setUsageCount(chat.getUsageCount() + 1);
        chatRepository.save(chat);
    }
}
