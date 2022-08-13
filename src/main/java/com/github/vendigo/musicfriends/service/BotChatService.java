package com.github.vendigo.musicfriends.service;

import com.github.vendigo.musicfriends.command.SetArtistCommand;
import com.github.vendigo.musicfriends.model.BotChatNode;
import com.github.vendigo.musicfriends.repository.BotChatRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class BotChatService {

    private final BotChatRepository chatRepository;

    public void setArtist(long chatId, SetArtistCommand command) {
        BotChatNode chatNode = chatRepository.findById(chatId)
                .orElseGet(() -> new BotChatNode(chatId));
        command.commandType().getIdSetter().accept(chatNode, command.artistId());
        chatRepository.save(chatNode);
    }

    public Optional<BotChatNode> findChat(long chatId) {
        return chatRepository.findById(chatId);
    }
}
