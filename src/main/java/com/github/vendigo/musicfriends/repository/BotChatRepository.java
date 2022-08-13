package com.github.vendigo.musicfriends.repository;

import com.github.vendigo.musicfriends.model.BotChatNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface BotChatRepository extends Neo4jRepository<BotChatNode, Long> {
}
