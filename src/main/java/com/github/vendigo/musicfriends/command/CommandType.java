package com.github.vendigo.musicfriends.command;

import com.github.vendigo.musicfriends.model.BotChatNode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.BiConsumer;

@AllArgsConstructor
@Getter
public enum CommandType {
    FROM("From", BotChatNode::setArtistFromId),
    TO("To", BotChatNode::setArtistToId);

    final String value;
    final BiConsumer<BotChatNode, Long> idSetter;
}
