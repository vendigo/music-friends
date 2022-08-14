package com.github.vendigo.musicfriends.command;

import java.util.Optional;

import static com.github.vendigo.musicfriends.utils.Utils.DEEZER_ARTIST_LINK;

public class CommandParser {

    public static Optional<SetArtistCommand> parseSetArtistCommand(String rawCommand) {
        String[] lines = rawCommand.split("\n");
        String firstLine = lines[0];
        String artistName = firstLine.trim();
        String secondLine = lines[1];
        if (!secondLine.startsWith(DEEZER_ARTIST_LINK)) {
            return Optional.empty();
        }
        String rawId = secondLine.substring(DEEZER_ARTIST_LINK.length());
        long artistId = Long.parseLong(rawId);
        return Optional.of(new SetArtistCommand(artistId, artistName));
    }
}
