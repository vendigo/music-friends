package com.github.vendigo.musicfriends.command;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.github.vendigo.musicfriends.bot.Utils.DEEZER_ARTIST_LINK;

public class CommandParser {

    public static final int FIRST_GROUP = 1;
    public static final int SECOND_GROUP = 2;
    public static final Pattern COMMAND_PATTERN = Pattern.compile("(From|To):(.*)");

    public static Optional<SetArtistCommand> parseSetArtistCommand(String rawCommand) {
        String[] lines = rawCommand.split("\n");
        String firstLine = lines[0];
        Matcher matcher = COMMAND_PATTERN.matcher(firstLine);
        if (!matcher.find()) {
            return Optional.empty();
        }

        String commandString = matcher.group(FIRST_GROUP);
        String artistName = matcher.group(SECOND_GROUP).trim();
        CommandType commandType = CommandType.valueOf(commandString.toUpperCase());
        String secondLine = lines[1];
        if (!secondLine.startsWith(DEEZER_ARTIST_LINK)) {
            return Optional.empty();
        }
        String rawId = secondLine.substring(DEEZER_ARTIST_LINK.length());
        long artistId = Long.parseLong(rawId);
        return Optional.of(new SetArtistCommand(commandType, artistId, artistName));
    }

    public static Optional<FindArtistCommand> parseFindArtistCommand(String rawCommand) {
        Matcher matcher = COMMAND_PATTERN.matcher(rawCommand);

        if (!matcher.find()) {
            return Optional.empty();
        }

        CommandType commandType = CommandType.valueOf(matcher.group(FIRST_GROUP).toUpperCase());
        String query = matcher.group(SECOND_GROUP).trim();
        return Optional.of(new FindArtistCommand(commandType, query));
    }
}
