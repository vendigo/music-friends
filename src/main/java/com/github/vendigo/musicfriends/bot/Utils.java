package com.github.vendigo.musicfriends.bot;

public class Utils {
    public static final String FROM_PREFIX = "From: ";
    public static final String TO_PREFIX = "To: ";
    public static String DEEZER_ARTIST_LINK = "https://www.deezer.com/artist/";

    public static boolean isSetArtistCommand(String command) {
        return command.startsWith(FROM_PREFIX) || command.startsWith(TO_PREFIX);
    }
}
