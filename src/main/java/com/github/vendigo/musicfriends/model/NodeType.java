package com.github.vendigo.musicfriends.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum NodeType {
    ARTIST("Artist"), TRACK("Track");

    @Getter
    final String label;
}
