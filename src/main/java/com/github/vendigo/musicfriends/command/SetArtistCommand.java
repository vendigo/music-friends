package com.github.vendigo.musicfriends.command;

public record SetArtistCommand(CommandType commandType, long artistId, String artistName) {
}
