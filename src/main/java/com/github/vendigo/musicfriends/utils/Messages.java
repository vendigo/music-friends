package com.github.vendigo.musicfriends.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "messages")
@Data
@Component
public class Messages {

    private String greeting;
    private String setFirstArtist;
    private String setSecondArtist;
    private String tryAgain;
    private String chosenArtist;
    private String unknownCommand;
    private String pathNotFound;
    private String usageLimitReached;
    private String likeMe;
    private String usefulLinks;
    private String noCollabs;
    private String sameArtist;
}
