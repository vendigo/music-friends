package com.github.vendigo.musicfriends.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Node("BotChat")
@Data
@NoArgsConstructor
public class BotChatNode {
    @Id
    private Long id;
    @Property
    private Long artistId;

    public BotChatNode(Long id) {
        this.id = id;
    }
}
