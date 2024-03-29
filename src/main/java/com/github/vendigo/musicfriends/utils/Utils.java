package com.github.vendigo.musicfriends.utils;

public class Utils {
    public static String DEEZER_ARTIST_LINK = "https://www.deezer.com/artist/";
    public static final String LINE_BREAK = "\n";

    public static boolean isSetArtistCommand(String command) {
        String[] lines = command.split(LINE_BREAK);
        return lines.length == 2 && lines[1].startsWith(DEEZER_ARTIST_LINK);
    }
}
