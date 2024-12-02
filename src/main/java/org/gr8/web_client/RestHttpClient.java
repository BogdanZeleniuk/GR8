package org.gr8.web_client;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class RestHttpClient {

    public static Response get(String url,
                               RequestSpecification requestSpecification,
                               ResponseSpecification responseSpecification,
                               Map<String, String> queryParams) {
        return given()
                .queryParams(queryParams)
                .spec(requestSpecification)
                .log().all()
                .when().get(url)
                .then()
                .spec(responseSpecification)
                .log().all()
                .extract().response();
    }

}
