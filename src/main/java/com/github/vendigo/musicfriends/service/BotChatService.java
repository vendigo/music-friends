package com.github.vendigo.musicfriends.service;

import com.github.vendigo.musicfriends.model.BotChatNode;
import com.github.vendigo.musicfriends.repository.BotChatRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;

@Service
@AllArgsConstructor
@Slf4j
public class BotChatService {
    private final BotChatRepository chatRepository;
    private final Clock clock;

    public void setArtist(BotChatNode chat, Long artistId) {
        chat.setArtistId(artistId);
        chatRepository.save(chat);
    }

    public BotChatNode findChat(long chatId, String username) {
        return chatRepository.findById(chatId)
                .orElseGet(() -> new BotChatNode(chatId, username));
    }

    public void logPathSearch(BotChatNode chat) {
        LocalDate today = LocalDate.now(clock);
        if (!today.equals(chat.getLastUsageDate())) {
            chat.setLastUsageDate(today);
            chat.setDayUsageCount(0L);
        }

        chat.setDayUsageCount(chat.getDayUsageCount() + 1);
        chat.setTotalUsageCount(chat.getTotalUsageCount() + 1);
        chat.setArtistId(null);
        chatRepository.save(chat);
    }

    public long getTotalUsers() {
        return chatRepository.count();
    }

    public long getTotalUsages() {
        return chatRepository.getTotalUsages();
    }
}
