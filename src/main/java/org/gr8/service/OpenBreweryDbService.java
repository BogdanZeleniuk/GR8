package org.gr8.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.gr8.model.response.SingleAutocompleteBreweryResponse;
import org.gr8.model.response.SingleBreweryResponse;
import org.gr8.web_client.RestHttpClient;

import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static org.apache.http.HttpStatus.SC_OK;
import static org.gr8.utils.Constants.OBJECT_MAPPER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

public class OpenBreweryDbService extends BaseService {

    private static final String SEARCH_FOR_BREWERIES_ENDPOINT = "search";
    private static final String AUTOCOMPLETE_ENDPOINT = "autocomplete";
    private static final String QUERY_PARAM = "query";
    private static final String PER_PAGE_PARAM = "per_page";

    public List<SingleBreweryResponse> getSearchBreweriesSuccessful(String city, int itemsPerPage) {
        Response searchBreweriesResponse = getSearchBreweriesResponse(city, itemsPerPage, HttpStatus.SC_OK);
        return deserializeSingleBreweryResponse(searchBreweriesResponse);
    }

    public Response getSearchBreweriesResponse(String city, int itemsPerPage, int statusCode) {
        Map<String, String> queryParams = Map.of(
                QUERY_PARAM, city.toLowerCase(),
                PER_PAGE_PARAM, String.valueOf(itemsPerPage)
        );
        return RestHttpClient.get(
                SEARCH_FOR_BREWERIES_ENDPOINT, getRequestSpecification(), getResponseSpecification(statusCode), queryParams);
    }

    public List<SingleAutocompleteBreweryResponse> getAutocompleteBreweriesSuccessful(String city) {
        Response autocompleteResponse = getAutocompleteResponse(city, HttpStatus.SC_OK);
        return deserializeSingleAutocompleteBreweryResponse(autocompleteResponse);
    }

    public Response getAutocompleteResponse(String city, int statusCode) {
        Map<String, String> queryParams = Map.of(
                QUERY_PARAM, city.toLowerCase()
        );
        return RestHttpClient.get(
                AUTOCOMPLETE_ENDPOINT, getRequestSpecification(), getResponseSpecification(statusCode), queryParams);
    }

    private List<SingleBreweryResponse> deserializeSingleBreweryResponse(Response response) {
        assertThat(response.getStatusCode(), greaterThanOrEqualTo(SC_OK));
        try {
            return OBJECT_MAPPER.readValue(response.getBody().prettyPrint(), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(format(DESERIALIZING_EXCEPTION_MESSAGE, response.getBody().prettyPrint(), e.getOriginalMessage()));
        }
    }

    private List<SingleAutocompleteBreweryResponse> deserializeSingleAutocompleteBreweryResponse(Response response) {
        assertThat(response.getStatusCode(), greaterThanOrEqualTo(SC_OK));
        try {
            return OBJECT_MAPPER.readValue(response.getBody().prettyPrint(), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(format(DESERIALIZING_EXCEPTION_MESSAGE, response.getBody().prettyPrint(), e.getOriginalMessage()));
        }
    }

}
