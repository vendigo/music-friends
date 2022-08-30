package com.github.vendigo.musicfriends.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

import java.time.LocalDate;

@Node("BotChat")
@Data
@NoArgsConstructor
public class BotChatNode {
    @Id
    private Long id;
    @Property
    private String username;
    @Property
    private Long artistId;
    @Property
    private LocalDate lastUsageDate;
    @Property
    private Long usageCount;

    public BotChatNode(long id, String username) {
        this.id = id;
        this.username = username;
    }
}
