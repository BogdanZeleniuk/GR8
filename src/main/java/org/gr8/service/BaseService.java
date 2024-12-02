package org.gr8.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static java.lang.String.format;
import static org.apache.http.HttpStatus.SC_OK;
import static org.gr8.config.ConfigProvider.getConfigProvider;
import static org.gr8.utils.Constants.ApplicationProperties.BASE_PATH;
import static org.gr8.utils.Constants.ApplicationProperties.BASE_URI;
import static org.gr8.utils.Constants.OBJECT_MAPPER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

public abstract class BaseService {

    private static final EncoderConfig ENCODER_CONFIG = encoderConfig()
            .appendDefaultContentCharsetToContentTypeIfUndefined(true);
    protected static final String DESERIALIZING_EXCEPTION_MESSAGE = "Can not deserialize %s, more details: %s";
    protected static final RestAssuredConfig REST_ASSURED_CONFIG = config.encoderConfig(ENCODER_CONFIG);

    public  <T>T deserializeAsObject(Response response, Class<?> clazz) {
        assertThat(response.getStatusCode(), greaterThanOrEqualTo(SC_OK));
        try {
            return (T) OBJECT_MAPPER.readValue(response.getBody().prettyPrint(), clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(format(DESERIALIZING_EXCEPTION_MESSAGE, response.getBody().prettyPrint(), e.getOriginalMessage()));
        }
    }

    protected RequestSpecification getRequestSpecification() {
        return given()
                .config(REST_ASSURED_CONFIG)
                .baseUri(getConfigProvider().getConfigParamValue(BASE_URI))
                .basePath(getConfigProvider().getConfigParamValue(BASE_PATH))
                .accept(ContentType.JSON)
                .noFilters();
    }
    protected ResponseSpecification getResponseSpecification(int statusCode) {
        return expect()
                .response()
                .statusCode(statusCode);
    }

}
