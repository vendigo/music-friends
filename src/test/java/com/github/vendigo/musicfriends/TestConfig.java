package com.github.vendigo.musicfriends;

import com.github.vendigo.musicfriends.config.ClockService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;

@Configuration
public class TestConfig {

    @Bean
    public ClockService clockService() {
        return new TestClockService(LocalDate.of(2022, Month.SEPTEMBER, 24));
    }
}
