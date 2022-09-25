package com.github.vendigo.musicfriends.controller;

import com.github.vendigo.musicfriends.AbstractIntTest;
import com.github.vendigo.musicfriends.TestUtils;
import com.github.vendigo.musicfriends.model.ArtistNode;
import com.github.vendigo.musicfriends.model.BotChatNode;
import com.github.vendigo.musicfriends.model.TrackNode;
import com.github.vendigo.musicfriends.repository.ArtistRepository;
import com.github.vendigo.musicfriends.repository.BotChatRepository;
import com.github.vendigo.musicfriends.repository.TrackRepository;
import io.restassured.http.ContentType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.stream.Stream;

import static com.github.vendigo.musicfriends.TestUtils.readSystemResource;
import static io.restassured.RestAssured.given;

@DirtiesContext
public class WebHookControllerIntTest extends AbstractIntTest {

    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private TrackRepository trackRepository;
    @Autowired
    private BotChatRepository botChatRepository;

    static Stream<Arguments> handleUpdateArgs() {
        return Stream.of(
                Arguments.of("data/startRequest.json", "data/startExpectedResponse.json"),
                Arguments.of("data/linksRequest.json", "data/linksResponse.json"),
                Arguments.of("data/emptyQuery.json", "data/emptyQueryResults.json"),
                Arguments.of("data/searchQuery.json", "data/searchQueryResults.json"),
                Arguments.of("data/setArtist.json", "data/setArtistResponse.json"),
                Arguments.of("data/unknownCommandRequest.json", "data/unknownCommandResponse.json"),
                Arguments.of("data/setArtistNoCollabs.json", "data/setArtistNoCollabsResponse.json"),
                Arguments.of("data/searchPathLimitReached.json", "data/searchPathLimitReachedResponse.json"),
                Arguments.of("data/searchPathSuccessful.json", "data/searchPathSuccessfulResponse.json"),
                Arguments.of("data/searchPathNotify.json", "data/searchPathNotifyResponse.json"),
                Arguments.of("data/searchPathNoCollabs.json", "data/searchPathNoCollabsResponse.json"),
                Arguments.of("data/searchPathSuccessfulLastUsage.json", "data/searchPathSuccessfulLastUsageResponse.json"),
                Arguments.of("data/pathNotFound.json", "data/pathNotFoundResponse.json")
        );
    }

    @ParameterizedTest
    @MethodSource("handleUpdateArgs")
    void handleUpdate(String requestFileName, String expectedResponseFileName) {
        setUpArtists();

        String actualResponse = given()
                .contentType(ContentType.JSON)
                .body(readSystemResource(requestFileName))
                .when()
                .post("/callback/music-friends")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .body()
                .asString();
        TestUtils.assertJson(readSystemResource(expectedResponseFileName), actualResponse);
    }

    private void setUpArtists() {
        artistRepository.deleteAll();
        trackRepository.deleteAll();
        botChatRepository.deleteAll();

        ArtistNode billie = new ArtistNode(1L, "Billie Eilish", "billie eilish", "billieAva.png", 30000, 100);
        ArtistNode skofka = new ArtistNode(2L, "Skofka", "skofka", "skofka.png", 10000, 99);
        ArtistNode metallica = new ArtistNode(3L, "Metallica", "metallica", "metallica.png", 20000, 98);
        ArtistNode kalush = new ArtistNode(4L, "KALUSH", "kalush", "kalush.png", 20, 2);
        ArtistNode john = new ArtistNode(5L, "John Snow", "john snow", "john_snow.png", 5000, 5);
        artistRepository.saveAll(List.of(billie, skofka, metallica, kalush));

        TrackNode dodomu = new TrackNode(1L, "Dodomu", 240, null, null, List.of(skofka, kalush));
        TrackNode badguy = new TrackNode(2L, "Bad Guy feat john snow", 260, null, null, List.of(billie, john));
        trackRepository.saveAll(List.of(dodomu, badguy));

        LocalDate today = LocalDate.of(2022, Month.SEPTEMBER, 24);
        BotChatNode artem = new BotChatNode(1L, "artem", 2L, today, 100L, 100L);
        BotChatNode ivan = new BotChatNode(2L, "ivan", 2L, today, 2L, 2L);
        BotChatNode lesia = new BotChatNode(3L, "lesia", 2L, today.minusDays(1), 100L, 100L);
        BotChatNode ira = new BotChatNode(4L, "ira", 2L, today, 99L, 150L);
        botChatRepository.saveAll(List.of(artem, ivan, lesia, ira));
    }

}
