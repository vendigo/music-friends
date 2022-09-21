package com.github.vendigo.musicfriends.controller;

import com.github.vendigo.musicfriends.AbstractIntTest;
import com.github.vendigo.musicfriends.TestUtils;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import static io.restassured.RestAssured.given;

@DirtiesContext
public class WebHookControllerIntTest extends AbstractIntTest {

    private static final String UPDATE_JSON = TestUtils.readSystemResource("data/linksRequest.json");
    private static final String EXPECTED_RESPONSE = TestUtils.readSystemResource("data/linksResponse.json");

    @Test
    void linksCommand() {
        String actualResponse = given()
                .contentType(ContentType.JSON)
                .body(UPDATE_JSON)
                .when()
                .post("/callback/music-friends")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .body()
                .asString();
        TestUtils.assertJson(EXPECTED_RESPONSE, actualResponse);
    }

}
