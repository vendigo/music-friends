package com.github.vendigo.musicfriends.controller;

import com.github.vendigo.musicfriends.AbstractIntTest;
import com.github.vendigo.musicfriends.TestUtils;
import com.github.vendigo.musicfriends.model.ArtistNode;
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
                Arguments.of("data/setArtistNoCollabs.json", "data/setArtistNoCollabsResponse.json")
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
        artistRepository.saveAll(List.of(billie, skofka, metallica, kalush));

        TrackNode dodomu = new TrackNode(1L, "Dodomu", 240, null, null, List.of(skofka, kalush));
        trackRepository.save(dodomu);
    }

}
