package com.github.vendigo.musicfriends.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Clock;

@Configuration
public class AppConfig {

    @Bean
    @Profile("!test")
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
