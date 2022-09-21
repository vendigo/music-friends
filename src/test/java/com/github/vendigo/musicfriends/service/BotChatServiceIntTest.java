package com.github.vendigo.musicfriends.service;

import com.github.vendigo.musicfriends.AbstractIntTest;
import com.github.vendigo.musicfriends.model.BotChatNode;
import com.github.vendigo.musicfriends.repository.BotChatRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext
public class BotChatServiceIntTest extends AbstractIntTest {

    @Autowired
    private BotChatService botChatService;
    @Autowired
    private BotChatRepository botChatRepository;

    @Test
    void getTotalUsers() {
        botChatRepository.saveAll(List.of(
                new BotChatNode(1, "Peter"),
                new BotChatNode(2, "Evan"),
                new BotChatNode(3, "Kate")
        ));
        long actualResults = botChatService.getTotalUsers();
        assertThat(actualResults).isEqualTo(3);
    }

    @Test
    void getTotalUsages() {
        BotChatNode peter = new BotChatNode(1, "Peter");
        peter.setTotalUsageCount(18L);
        BotChatNode kate = new BotChatNode(2, "Kate");
        kate.setTotalUsageCount(35L);
        botChatRepository.saveAll(List.of(peter, kate));
        long actualResults = botChatService.getTotalUsages();
        assertThat(actualResults).isEqualTo(53);
    }
}
