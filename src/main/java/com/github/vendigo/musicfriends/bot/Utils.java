package com.github.vendigo.musicfriends.bot;

import java.util.regex.Pattern;

public class Utils {
    public static final String FROM_PREFIX = "From: ";
    public static final String TO_PREFIX = "To: ";
    public static final int FIRST_GROUP = 1;
    public static final int SECOND_GROUP = 2;
    static final Pattern COMMAND_PATTERN = Pattern.compile("(From|To): (.*)");

    public static boolean isSetArtistCommand(String command) {
        return command.startsWith(FROM_PREFIX) || command.startsWith(TO_PREFIX);
    }
}
