package com.github.vendigo.musicfriends.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

import java.time.LocalDate;

@Node("BotChat")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private Long dayUsageCount;
    @Property
    private Long totalUsageCount;

    public BotChatNode(long id, String username) {
        this.id = id;
        this.username = username;
        this.dayUsageCount = 0L;
        this.totalUsageCount = 0L;
    }
}
