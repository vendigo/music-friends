package com.github.vendigo.musicfriends.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NodeType {
    ARTIST("Artist"), TRACK("Track");

    final String label;
}
