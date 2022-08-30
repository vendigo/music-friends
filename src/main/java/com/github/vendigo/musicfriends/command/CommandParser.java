package com.github.vendigo.musicfriends.command;

import static com.github.vendigo.musicfriends.utils.Utils.DEEZER_ARTIST_LINK;

public class CommandParser {

    public static SetArtistCommand parseSetArtistCommand(String rawCommand) {
        String[] lines = rawCommand.split("\n");
        String firstLine = lines[0];
        String artistName = firstLine.trim();
        String secondLine = lines[1];
        String rawId = secondLine.substring(DEEZER_ARTIST_LINK.length());
        long artistId = Long.parseLong(rawId);
        return new SetArtistCommand(artistId, artistName);
    }
}
