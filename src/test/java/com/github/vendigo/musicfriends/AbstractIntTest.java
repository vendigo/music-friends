package com.github.vendigo.musicfriends;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.containers.Neo4jLabsPlugin;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class AbstractIntTest {

    @Container
    private static Neo4jContainer<?> neo4j = new Neo4jContainer<>(DockerImageName.parse("neo4j:4.4"))
            .withoutAuthentication()
            .withLabsPlugins(Neo4jLabsPlugin.APOC);

    @MockBean
    private Clock clock;

    @DynamicPropertySource
    static void registerNeo4jProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.neo4j.uri", neo4j::getBoltUrl);
    }

    @Value("${local.server.port}")
    void initRestAssured(int localPort) {
        RestAssured.port = localPort;
    }

    @BeforeEach
    void setUp() {
        when(clock.instant()).thenReturn(Instant.parse("2022-09-24T00:00:00.00Z"));
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());
    }
}
