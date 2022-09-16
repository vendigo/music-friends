package com.github.vendigo.musicfriends.repository;

import com.github.vendigo.musicfriends.model.BotChatNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface BotChatRepository extends Neo4jRepository<BotChatNode, Long> {

    @Query("MATCH (b:BotChat) RETURN SUM(b.totalUsageCount)")
    long getTotalUsages();
}
