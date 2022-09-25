package com.github.vendigo.musicfriends;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.*;

@Configuration
public class TestConfig {
    @Bean
    public Clock clock() {
        Instant testDate = LocalDate.of(2022, Month.SEPTEMBER, 24).atStartOfDay().toInstant(ZoneOffset.UTC);
        return Clock.fixed(testDate, ZoneId.systemDefault());
    }
}
