package com.github.vendigo.musicfriends;

import com.github.vendigo.musicfriends.config.ClockService;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
public class TestClockService implements ClockService {

    private final LocalDate date;

    public LocalDate nowDate() {
        return date;
    }
}
