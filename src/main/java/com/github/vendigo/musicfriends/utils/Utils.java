package com.github.vendigo.musicfriends.utils;

public class Utils {
    public static String DEEZER_ARTIST_LINK = "https://www.deezer.com/artist/";

    public static boolean isSetArtistCommand(String command) {
        String[] lines = command.split("\n");
        return lines.length == 2 && lines[1].contains(DEEZER_ARTIST_LINK);
    }
}
