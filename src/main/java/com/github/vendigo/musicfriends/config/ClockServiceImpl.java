package com.github.vendigo.musicfriends.config;

import java.time.LocalDate;

public class ClockServiceImpl implements ClockService {

    public LocalDate nowDate() {
        return LocalDate.now();
    }
}
